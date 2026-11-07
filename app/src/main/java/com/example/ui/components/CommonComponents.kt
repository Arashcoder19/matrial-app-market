package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Category
import com.example.data.Product
import com.example.ui.theme.*
import java.text.DecimalFormat

fun String.toPersianDigits(): String {
    val persianDigits = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
    val sb = StringBuilder()
    for (ch in this) {
        if (ch in '0'..'9') {
            sb.append(persianDigits[ch - '0'])
        } else {
            sb.append(ch)
        }
    }
    return sb.toString()
}

fun Long.toPersianToman(): String {
    val formatter = DecimalFormat("#,###")
    return "${formatter.format(this).toPersianDigits()} تومان"
}

fun Int.toPersianNumber(): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(this).toPersianDigits()
}

@Composable
fun ProductCard(
    product: Product,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onProductClick(product) }
            .testTag("product_card_${product.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Badges row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (product.hasFreeDeliveryInKerman) {
                    Surface(
                        color = Color(0xFFDCFCE7),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "حمل رایگان در کرمان",
                            color = Color(0xFF15803D),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                } else {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = product.categoryName,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }

                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = product.rating.toString().toPersianDigits(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = AmberPrimaryLight,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Visual Placeholder / Icon for Product
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                val icon = when (product.categoryId) {
                    "siman" -> Icons.Outlined.Layers
                    "ajr-o-sfal" -> Icons.Outlined.Widgets
                    "blok" -> Icons.Outlined.ViewInAr
                    "btn" -> Icons.Outlined.LocalShipping
                    "shn-o-mash" -> Icons.Outlined.Grain
                    "gch" -> Icons.Outlined.AutoAwesome
                    "chsb-kashi-o-sramik" -> Icons.Outlined.Dashboard
                    "aizogam" -> Icons.Outlined.Shield
                    else -> Icons.Outlined.Construction
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = AmberPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.brand,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp,
                modifier = Modifier.heightIn(min = 40.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Unit info
            Text(
                text = "واحد: ${product.unit}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price & Add Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
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
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AmberPrimary
                    )
                }

                IconButton(
                    onClick = { onAddToCart(product) },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AmberPrimary)
                        .testTag("add_to_cart_${product.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "افزودن به سبد",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .testTag("category_chip_${category.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AmberContainer else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(AmberPrimary)) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val icon = when (category.id) {
                "siman" -> Icons.Default.Layers
                "ajr-o-sfal" -> Icons.Default.Widgets
                "blok" -> Icons.Default.ViewInAr
                "btn" -> Icons.Default.LocalShipping
                "shn-o-mash" -> Icons.Default.Grain
                "gch" -> Icons.Default.AutoAwesome
                "chsb-kashi-o-sramik" -> Icons.Default.Dashboard
                "aizogam" -> Icons.Default.Shield
                else -> Icons.Default.Construction
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) OnAmberContainer else AmberPrimary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) OnAmberContainer else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (actionText != null && onActionClick != null) {
            TextButton(onClick = onActionClick) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelMedium,
                    color = AmberPrimary,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = null,
                    tint = AmberPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
