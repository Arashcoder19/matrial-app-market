package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CartItem
import com.example.ui.components.toPersianDigits
import com.example.ui.components.toPersianNumber
import com.example.ui.components.toPersianToman
import com.example.ui.theme.*
import com.example.ui.viewmodel.MasalehUiState
import com.example.ui.viewmodel.MasalehViewModel

@Composable
fun CartScreen(
    uiState: MasalehUiState,
    viewModel: MasalehViewModel,
    modifier: Modifier = Modifier
) {
    var showAddressDialog by remember { mutableStateOf(false) }
    var addressInput by remember { mutableStateOf(uiState.userAddress) }

    if (showAddressDialog) {
        AlertDialog(
            onDismissRequest = { showAddressDialog = false },
            title = {
                Text(
                    text = "آدرس محل تخلیه مصالح",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "آدرس دقیق پروژه و شماره تماس جهت هماهنگی ورود کامیون:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = addressInput,
                        onValueChange = { addressInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.setUserAddress(addressInput)
                        showAddressDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary)
                ) {
                    Text("ذخیره آدرس")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddressDialog = false }) {
                    Text("انصراف")
                }
            }
        )
    }

    if (uiState.cartItems.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp)
                .testTag("empty_cart_view"),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(AmberContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(46.dp),
                        tint = AmberPrimary
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "سبد خرید شما خالی است",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "برای ثبت سفارش مصالح مورد نیاز پروژه خود، به کاتالوگ یا صفحه اصلی مراجعه کنید.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { viewModel.selectTab(1) },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Storefront, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("مشاهده کاتالوگ مصالح")
                }
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .testTag("cart_screen"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 95.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "سبد خرید پروژه (${uiState.cartItems.size.toString().toPersianDigits()} قلم کالا)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { viewModel.clearCart() }) {
                        Text("حذف همه", color = DangerRed, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

            // Cart Items List
            items(uiState.cartItems, key = { it.product.id }) { item ->
                CartItemRow(
                    item = item,
                    onIncrease = { viewModel.updateCartItemQuantity(item.product.id, if (item.product.minOrder >= 100) 100 else 1) },
                    onDecrease = { viewModel.updateCartItemQuantity(item.product.id, if (item.product.minOrder >= 100) -100 else -1) },
                    onRemove = { viewModel.removeFromCart(item.product.id) }
                )
            }

            // Transport vehicle recommendation
            item {
                val vehicleName = if (uiState.totalCartItemCount > 500) "کمپرسی جفت یا تریلی" else "خاور ۴ تن"
                Surface(
                    color = SlateContainer,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = null,
                            tint = SlateSecondary,
                            modifier = Modifier.size(26.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "خودروی باربری پیشنهادی: $vehicleName",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = OnSlateContainer
                            )
                            Text(
                                text = "حجم کل بار مطابق با استانداردهای حمل و نقل جاده‌ای محاسبه شده است.",
                                style = MaterialTheme.typography.bodySmall,
                                color = SlateSecondaryLight
                            )
                        }
                    }
                }
            }

            // Unloader labor switch
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Groups,
                                contentDescription = null,
                                tint = AmberPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(
                                    text = "کارگر تخلیه بار در محل پروژه",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "تخلیه استاندارد پای کار (+۳۵۰٬۰۰۰ تومان)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Switch(
                            checked = uiState.hasUnloaderWorkers,
                            onCheckedChange = { viewModel.toggleUnloaderWorkers(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = AmberPrimary)
                        )
                    }
                }
            }

            // Project Delivery Address
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showAddressDialog = true },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = AmberPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "محل تخلیه (${uiState.selectedCity}):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = uiState.userAddress,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2
                            )
                        }
                        Text(
                            text = "تغییر",
                            style = MaterialTheme.typography.labelSmall,
                            color = AmberPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Discount Code
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = uiState.promoCodeInput,
                                onValueChange = { viewModel.setPromoCodeInput(it) },
                                placeholder = { Text("کد تخفیف (مثال: MASALEH)") },
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            )
                            Button(
                                onClick = { viewModel.applyPromoCode() },
                                colors = ButtonDefaults.buttonColors(containerColor = SlateSecondary),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("اعمال")
                            }
                        }
                        if (uiState.appliedPromoDiscountPercent > 0) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "کد تخفیف ۷٪ ویژه با موفقیت اعمال شد!",
                                color = SuccessGreen,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (uiState.promoError != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = uiState.promoError,
                                color = DangerRed,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            // Price Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "فاکتور و خلاصه مالی",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Divider(modifier = Modifier.padding(vertical = 4.dp))

                        // Subtotal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "مجموع بهای مصالح:", style = MaterialTheme.typography.bodyMedium)
                            Text(text = uiState.cartSubtotal.toPersianToman(), style = MaterialTheme.typography.bodyMedium)
                        }

                        // Shipping
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "کرایه حمل در ${uiState.selectedCity}:", style = MaterialTheme.typography.bodyMedium)
                            if (uiState.estimatedShipping == 0L) {
                                Text(text = "رایگان", color = SuccessGreen, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            } else {
                                Text(text = uiState.estimatedShipping.toPersianToman(), style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        // Unloader
                        if (uiState.hasUnloaderWorkers) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "خدمات کارگر تخلیه بار:", style = MaterialTheme.typography.bodyMedium)
                                Text(text = uiState.unloaderCost.toPersianToman(), style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        // Discount
                        if (uiState.cartDiscount > 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "تخفیف ویژه:", style = MaterialTheme.typography.bodyMedium, color = SuccessGreen)
                                Text(text = "- ${uiState.cartDiscount.toPersianToman()}", style = MaterialTheme.typography.bodyMedium, color = SuccessGreen, fontWeight = FontWeight.Bold)
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 6.dp))

                        // Total
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "مبلغ نهایی قابل پرداخت:",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = uiState.cartTotal.toPersianToman(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = AmberPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Final Checkout Button
                        Button(
                            onClick = { viewModel.checkoutOrder() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("checkout_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ثبت نهایی سفارش و ارسال به پروژه",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.product.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "برند: ${item.product.brand} | واحد: ${item.product.unit}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "حذف",
                        tint = DangerRed,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Stepper
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        IconButton(
                            onClick = onDecrease,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "کاهش", modifier = Modifier.size(16.dp))
                        }
                        Text(
                            text = item.quantity.toPersianNumber(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.widthIn(min = 30.dp),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = onIncrease,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "افزایش", modifier = Modifier.size(16.dp))
                        }
                    }
                }

                // Total price
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "قیمت کل:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = item.totalPrice.toPersianToman(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AmberPrimary
                    )
                }
            }
        }
    }
}
