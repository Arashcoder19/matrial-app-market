package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Order
import com.example.data.OrderStatus
import com.example.data.SampleData
import com.example.data.StoreSupplier
import com.example.ui.components.toPersianDigits
import com.example.ui.components.toPersianNumber
import com.example.ui.components.toPersianToman
import com.example.ui.theme.*
import com.example.ui.viewmodel.MasalehUiState
import com.example.ui.viewmodel.MasalehViewModel

@Composable
fun SuppliersAndOrdersScreen(
    uiState: MasalehUiState,
    viewModel: MasalehViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var subTab by remember { mutableStateOf(0) } // 0 = Orders, 1 = Suppliers, 2 = About & Support
    val tabs = listOf("سفارش‌های من", "تامین‌کنندگان منتخب", "اطلاعات و پشتیبانی")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("suppliers_and_orders_screen")
    ) {
        // Tab Selector
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "پیگیری سفارش‌ها و تامین‌کنندگان",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    tabs.forEachIndexed { index, title ->
                        SegmentedButton(
                            selected = subTab == index,
                            onClick = { subTab = index },
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = tabs.size),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = AmberPrimary,
                                activeContentColor = Color.White
                            )
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (subTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        when (subTab) {
            0 -> OrdersSubList(orders = uiState.orders)
            1 -> SuppliersSubList(suppliers = SampleData.suppliers, onCall = { phone ->
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                context.startActivity(intent)
            })
            2 -> AboutAndSupportSubTab(onCallSupport = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:09137465238"))
                context.startActivity(intent)
            })
        }
    }
}

@Composable
fun OrdersSubList(orders: List<Order>) {
    if (orders.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Inventory2,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "هنوز سفارشی ثبت نکرده‌اید",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(orders) { order ->
                OrderCard(order = order)
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        tint = AmberPrimary
                    )
                    Text(
                        text = "سفارش ${order.orderNumber}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    color = when (order.status) {
                        OrderStatus.DELIVERED -> Color(0xFFDCFCE7)
                        OrderStatus.IN_TRANSIT -> Color(0xFFDBEAFE)
                        else -> AmberContainer
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = order.status.titleFa,
                        color = when (order.status) {
                            OrderStatus.DELIVERED -> Color(0xFF15803D)
                            OrderStatus.IN_TRANSIT -> Color(0xFF1D4ED8)
                            else -> OnAmberContainer
                        },
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "تاریخ ثبت: ${order.date}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Divider(modifier = Modifier.padding(vertical = 10.dp))

            // Order Status Tracker Steps
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OrderStatus.values().forEach { step ->
                    val isPastOrCurrent = step.stepIndex <= order.status.stepIndex
                    val isCurrent = step == order.status
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isCurrent) AmberPrimary else if (isPastOrCurrent) SuccessGreen else MaterialTheme.colorScheme.surfaceVariant
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isPastOrCurrent) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = step.titleFa,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 9.sp,
                            color = if (isPastOrCurrent) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 10.dp))

            // Items list summary
            Text(
                text = "اقلام سفارش داده شده:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            order.items.forEach { itm ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "• ${itm.product.title} (${itm.quantity.toPersianNumber()} ${itm.product.unit})",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = itm.totalPrice.toPersianToman(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Transport & Address
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "ناوگان حمل: ${order.vehicle}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "آدرس تحویل: ${order.address}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Total Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "مبلغ کل پرداختی:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = order.finalPrice.toPersianToman(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AmberPrimary
                )
            }
        }
    }
}

@Composable
fun SuppliersSubList(
    suppliers: List<StoreSupplier>,
    onCall: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(suppliers) { sup ->
            SupplierCard(supplier = sup, onCall = { onCall(sup.phone) })
        }
    }
}

@Composable
fun SupplierCard(supplier: StoreSupplier, onCall: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = supplier.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (supplier.isVerified) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "تایید شده",
                                tint = SuccessGreen,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Text(
                        text = "شهر: ${supplier.city}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = supplier.rating.toString().toPersianDigits(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AmberPrimaryLight,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Address
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = AmberPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = supplier.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Specialties chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                supplier.specialties.forEach { spec ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = spec,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Call Button
            Button(
                onClick = onCall,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "تماس با فروشگاه (${supplier.phone})",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AboutAndSupportSubTab(onCallSupport: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // About Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "درباره سامانه مصالح مارکت",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AmberPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "مصالح مارکت با ارائه متنوع‌ترین مصالح ساختمانی و همکاری با تامین‌کنندگان معتبر، تجربه آسان، مطمئن و سریع از خرید آنلاین را برای سازندگان، پیمانکاران و فعالان حوزه ساختمان فراهم کرده است.",
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Contact & Support
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "اطلاعات تماس و دفتر مرکزی",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = AmberPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "شهر کرمان، بلوار امام حسن مجتبی، نرسیده به کوچه پانزده",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Phone, contentDescription = null, tint = AmberPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "شماره پشتیبانی: ۰۹۱۳۷۴۶۵۲۳۸",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onCallSupport,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.HeadsetMic, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("تماس فوری با کارشناس فروش")
                    }
                }
            }
        }

        // FAQ
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "پرسش‌های متداول",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "۱. زمان تحویل سفارش‌ها چقدر است؟",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "سفارش‌های داخل شهر کرمان در کمتر از ۳ تا ۵ ساعت و مصالح کامیونی مانند بتن و آجر طبق نوبت اعلامی در همان روز کاری تخلیه می‌شوند.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "۲. آیا بارنامه و باسکول معتبر ارائه می‌شود؟",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "بله، کلیه بارهای تنی مانند ماسه، شن و آجر همراه با فاکتور رسمی و برگ باسکول دیجیتال معدن تحویل مهندس کارگاه می‌گردد.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
