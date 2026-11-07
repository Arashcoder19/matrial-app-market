package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Product
import com.example.ui.components.toPersianDigits
import com.example.ui.components.toPersianNumber
import com.example.ui.components.toPersianToman
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailSheet(
    product: Product,
    onDismiss: () -> Unit,
    onAddToCart: (Product, Int) -> Unit
) {
    var quantity by remember(product) { mutableStateOf(if (product.minOrder > 1) product.minOrder else 1) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.testTag("product_detail_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Category & Rating Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = AmberContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = product.categoryName,
                        color = OnAmberContainer,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${product.rating.toString().toPersianDigits()} (${product.salesCount.toPersianNumber()} سفارش)",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AmberPrimaryLight,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "تولیدکننده / برند: ${product.brand}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Free Delivery Badge in Kerman if applicable
            if (product.hasFreeDeliveryInKerman) {
                Surface(
                    color = Color(0xFFDCFCE7),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = null,
                            tint = Color(0xFF15803D),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "این کالا شامل طرح حمل رایگان در سراسر شهر کرمان است.",
                            color = Color(0xFF15803D),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Price Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "قیمت به ازای هر ${product.unit}:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Column(horizontalAlignment = Alignment.End) {
                    if (product.oldPrice != null) {
                        Text(
                            text = product.oldPrice.toPersianToman(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    Text(
                        text = product.price.toPersianToman(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = AmberPrimary
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 14.dp))

            // Description
            Text(
                text = "توضیحات و کاربرد:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Technical Specs
            if (product.specs.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "مشخصات فنی و استانداردها:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        product.specs.entries.forEachIndexed { idx, entry ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = entry.key,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = entry.value.toPersianDigits(),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            if (idx < product.specs.size - 1) {
                                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Recommended Vehicle
            Surface(
                color = SlateContainer,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = SlateSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "نوع وسیله نقلیه پیشنهادی جهت تحویل: ${product.recommendedVehicle}",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSlateContainer,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quantity Stepper and Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تعداد / مقدار (${product.unit}):",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                val step = if (product.minOrder >= 100) 100 else if (product.minOrder >= 10) 5 else 1
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (quantity - step >= product.minOrder) {
                                    quantity -= step
                                }
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "کاهش")
                        }
                        Text(
                            text = quantity.toPersianNumber(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.widthIn(min = 40.dp),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = { quantity += step },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "افزایش")
                        }
                    }
                }
            }

            if (product.minOrder > 1) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "* حداقل سفارش این محصول ${product.minOrder.toPersianNumber()} ${product.unit} می‌باشد.",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmberPrimary
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Add To Cart Button
            Button(
                onClick = {
                    onAddToCart(product, quantity)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("detail_add_to_cart_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary)
            ) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                val total = product.price * quantity
                Text(
                    text = "افزودن به سبد خرید (${total.toPersianToman()})",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}
