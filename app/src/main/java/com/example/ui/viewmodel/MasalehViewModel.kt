package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class SortOption(val titleFa: String) {
    DEFAULT("پیش‌فرض"),
    CHEAPEST("ارزان‌ترین"),
    EXPENSIVE("گران‌ترین"),
    BEST_SELLER("پرفروش‌ترین")
}

data class MasalehUiState(
    val selectedTab: Int = 0,
    val searchQuery: String = "",
    val selectedCategoryId: String? = null,
    val selectedSort: SortOption = SortOption.DEFAULT,
    val cartItems: List<CartItem> = listOf(
        CartItem(SampleData.products[0], 20), // 20 کیسه سیمان ممتازان
        CartItem(SampleData.products[2], 500) // 500 قالب تیغه ۱۰
    ),
    val orders: List<Order> = SampleData.sampleOrders,
    val selectedProductForDetail: Product? = null,
    val selectedCity: String = "کرمان",
    val promoCodeInput: String = "",
    val appliedPromoDiscountPercent: Int = 0,
    val promoError: String? = null,
    val hasUnloaderWorkers: Boolean = false,
    val orderSuccess: Order? = null,
    val userAddress: String = "کرمان، بلوار جمهوری، کوچه ۱۸، پلاک ۱۲ - کارگاه ساختمانی آریا",
    
    // Calculator States
    val calcTypeIndex: Int = 0, // 0 = Wall, 1 = Concrete, 2 = Tile
    val wallLength: String = "12",
    val wallHeight: String = "3.2",
    val wallThicknessCm: Int = 10,
    val concreteArea: String = "100",
    val concreteThicknessCm: String = "15",
    val tileArea: String = "65"
) {
    val cartSubtotal: Long get() = cartItems.sumOf { it.totalPrice }
    val cartDiscount: Long get() = if (appliedPromoDiscountPercent > 0) (cartSubtotal * appliedPromoDiscountPercent) / 100 else 0L
    val unloaderCost: Long get() = if (hasUnloaderWorkers && cartItems.isNotEmpty()) 350_000L else 0L
    val estimatedShipping: Long get() = if (cartItems.isEmpty()) 0L else if (cartItems.any { it.product.hasFreeDeliveryInKerman }) 0L else 380_000L
    val cartTotal: Long get() = Math.max(0L, cartSubtotal - cartDiscount + unloaderCost + estimatedShipping)
    val totalCartItemCount: Int get() = cartItems.sumOf { it.quantity }
}

class MasalehViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MasalehUiState())
    val uiState: StateFlow<MasalehUiState> = _uiState.asStateFlow()

    fun selectTab(tabIndex: Int) {
        _uiState.update { it.copy(selectedTab = tabIndex) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectCategory(categoryId: String?) {
        _uiState.update { it.copy(selectedCategoryId = categoryId) }
    }

    fun selectSort(sortOption: SortOption) {
        _uiState.update { it.copy(selectedSort = sortOption) }
    }

    fun selectCity(city: String) {
        _uiState.update { it.copy(selectedCity = city) }
    }

    fun showProductDetail(product: Product?) {
        _uiState.update { it.copy(selectedProductForDetail = product) }
    }

    fun addToCart(product: Product, quantityToAdd: Int = 1) {
        _uiState.update { state ->
            val existingIndex = state.cartItems.indexOfFirst { it.product.id == product.id }
            val updatedList = state.cartItems.toMutableList()
            if (existingIndex >= 0) {
                val current = updatedList[existingIndex]
                updatedList[existingIndex] = current.copy(quantity = current.quantity + quantityToAdd)
            } else {
                updatedList.add(CartItem(product, quantityToAdd))
            }
            state.copy(cartItems = updatedList)
        }
    }

    fun updateCartItemQuantity(productId: String, delta: Int) {
        _uiState.update { state ->
            val updatedList = state.cartItems.mapNotNull { item ->
                if (item.product.id == productId) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else {
                    item
                }
            }
            state.copy(cartItems = updatedList)
        }
    }

    fun removeFromCart(productId: String) {
        _uiState.update { state ->
            state.copy(cartItems = state.cartItems.filterNot { it.product.id == productId })
        }
    }

    fun clearCart() {
        _uiState.update { it.copy(cartItems = emptyList()) }
    }

    fun toggleUnloaderWorkers(enabled: Boolean) {
        _uiState.update { it.copy(hasUnloaderWorkers = enabled) }
    }

    fun setPromoCodeInput(code: String) {
        _uiState.update { it.copy(promoCodeInput = code, promoError = null) }
    }

    fun applyPromoCode() {
        val code = _uiState.value.promoCodeInput.trim()
        if (code.equals("MASALEH", ignoreCase = true) || code.equals("KERMAN", ignoreCase = true) || code.equals("پروژه", ignoreCase = true)) {
            _uiState.update { it.copy(appliedPromoDiscountPercent = 7, promoError = null) }
        } else {
            _uiState.update { it.copy(promoError = "کد تخفیف نامعتبر است (کد تخفیف آزمایشی: MASALEH)") }
        }
    }

    fun setUserAddress(address: String) {
        _uiState.update { it.copy(userAddress = address) }
    }

    fun checkoutOrder() {
        val state = _uiState.value
        if (state.cartItems.isEmpty()) return

        val newOrder = Order(
            id = "ord-${System.currentTimeMillis() % 100000}",
            orderNumber = "MSL-${(10000..99999).random()}",
            date = "همین الان - امروز",
            status = OrderStatus.CONFIRMED,
            items = state.cartItems,
            totalPrice = state.cartSubtotal,
            shippingCost = state.estimatedShipping,
            discount = state.cartDiscount,
            finalPrice = state.cartTotal,
            address = state.userAddress,
            vehicle = if (state.totalCartItemCount > 500) "کمپرسی جفت یا تریلی" else "خاور ۴ تن",
            hasUnloader = state.hasUnloaderWorkers
        )

        _uiState.update {
            it.copy(
                orders = listOf(newOrder) + it.orders,
                cartItems = emptyList(),
                orderSuccess = newOrder
            )
        }
    }

    fun dismissOrderSuccess() {
        _uiState.update { it.copy(orderSuccess = null) }
    }

    // Calculator inputs
    fun setCalcTypeIndex(index: Int) {
        _uiState.update { it.copy(calcTypeIndex = index) }
    }

    fun setWallInputs(length: String, height: String, thickness: Int) {
        _uiState.update { it.copy(wallLength = length, wallHeight = height, wallThicknessCm = thickness) }
    }

    fun setConcreteInputs(area: String, thickness: String) {
        _uiState.update { it.copy(concreteArea = area, concreteThicknessCm = thickness) }
    }

    fun setTileInputs(area: String) {
        _uiState.update { it.copy(tileArea = area) }
    }

    fun addCalculationToCart(result: CalculationResult) {
        _uiState.update { state ->
            val updated = state.cartItems.toMutableList()
            for (mat in result.materials) {
                mat.matchedProduct?.let { product ->
                    val qty = mat.amount.toDoubleOrNull()?.toInt() ?: 1
                    val existingIdx = updated.indexOfFirst { it.product.id == product.id }
                    if (existingIdx >= 0) {
                        updated[existingIdx] = updated[existingIdx].copy(
                            quantity = updated[existingIdx].quantity + qty
                        )
                    } else {
                        updated.add(CartItem(product, qty))
                    }
                }
            }
            state.copy(cartItems = updated, selectedTab = 3) // Jump to cart
        }
    }

    fun getFilteredProducts(): List<Product> {
        val state = _uiState.value
        var list = SampleData.products

        if (!state.selectedCategoryId.isNullOrBlank()) {
            list = list.filter { it.categoryId == state.selectedCategoryId }
        }

        if (state.searchQuery.isNotBlank()) {
            val q = state.searchQuery.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                it.brand.lowercase().contains(q) ||
                it.description.lowercase().contains(q) ||
                it.categoryName.lowercase().contains(q)
            }
        }

        return when (state.selectedSort) {
            SortOption.DEFAULT -> list
            SortOption.CHEAPEST -> list.sortedBy { it.price }
            SortOption.EXPENSIVE -> list.sortedByDescending { it.price }
            SortOption.BEST_SELLER -> list.sortedByDescending { it.salesCount }
        }
    }
}
