package com.example.data

data class Category(
    val id: String,
    val name: String,
    val iconName: String,
    val description: String,
    val itemCount: Int
)

data class Product(
    val id: String,
    val title: String,
    val categoryId: String,
    val categoryName: String,
    val price: Long,
    val oldPrice: Long? = null,
    val unit: String,
    val brand: String,
    val rating: Float = 4.8f,
    val salesCount: Int = 120,
    val minOrder: Int = 1,
    val stockAvailable: Boolean = true,
    val description: String,
    val specs: Map<String, String> = emptyMap(),
    val hasFreeDeliveryInKerman: Boolean = false,
    val recommendedVehicle: String = "خاور"
)

data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val totalPrice: Long get() = product.price * quantity
}

data class StoreSupplier(
    val id: String,
    val name: String,
    val rating: Float,
    val reviewsCount: Int,
    val phone: String,
    val address: String,
    val city: String,
    val isVerified: Boolean = true,
    val specialties: List<String>
)

enum class OrderStatus(val titleFa: String, val stepIndex: Int) {
    REGISTERED("ثبت سفارش", 0),
    CONFIRMED("تایید تامین‌کننده", 1),
    LOADING("بارگیری در کارخانه/انبار", 2),
    IN_TRANSIT("در مسیر پروژه", 3),
    DELIVERED("تخلیه و تحویل", 4)
}

data class Order(
    val id: String,
    val orderNumber: String,
    val date: String,
    val status: OrderStatus,
    val items: List<CartItem>,
    val totalPrice: Long,
    val shippingCost: Long,
    val discount: Long,
    val finalPrice: Long,
    val address: String,
    val vehicle: String,
    val hasUnloader: Boolean
)

data class CalculationResult(
    val title: String,
    val summary: String,
    val materials: List<CalculatedMaterialItem>,
    val estimatedCost: Long
)

data class CalculatedMaterialItem(
    val name: String,
    val amount: String,
    val unit: String,
    val matchedProduct: Product? = null
)
