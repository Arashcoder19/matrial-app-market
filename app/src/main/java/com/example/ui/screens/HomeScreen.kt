package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.Product
import com.example.data.SampleData
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.MasalehUiState
import com.example.ui.viewmodel.MasalehViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: MasalehUiState,
    viewModel: MasalehViewModel,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showCityDialog by remember { mutableStateOf(false) }
    val cities = listOf("کرمان", "رفسنجان", "سیرجان", "جیرفت", "بم", "زرند", "کهنوج")

    if (showCityDialog) {
        AlertDialog(
            onDismissRequest = { showCityDialog = false },
            title = {
                Text(
                    text = "انتخاب شهر محل پروژه",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "نرخ مصالح و کرایه حمل بر اساس شهر محل تخلیه محاسبه می‌شود:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    cities.forEach { city ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    viewModel.selectCity(city)
                                    showCityDialog = false
                                }
                                .padding(vertical = 10.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = city,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (city == uiState.selectedCity) FontWeight.Bold else FontWeight.Normal
                            )
                            if (city == uiState.selectedCity) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = AmberPrimary
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCityDialog = false }) {
                    Text("بستن")
                }
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Top Location and App Bar
        item {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Brand Title
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AmberPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Construction,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "مصالح مارکت",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberPrimary
                                )
                                Text(
                                    text = "سامانه فروش آنلاین مصالح ساختمانی",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // City Delivery Badge
                        Surface(
                            onClick = { showCityDialog = true },
                            shape = RoundedCornerShape(20.dp),
                            color = AmberContainer,
                            modifier = Modifier.testTag("city_selector_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = OnAmberContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = uiState.selectedCity,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnAmberContainer
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = OnAmberContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Search input
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = {
                            Text(
                                "جستجوی سیمان، آجر، بلوک، شن و ماسه، بتن...",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "جستجو",
                                tint = AmberPrimary
                            )
                        },
                        trailingIcon = {
                            if (uiState.searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                    Icon(Icons.Default.Close, contentDescription = "پاک کردن")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_search_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AmberPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        }

        // Hero Banner with Image
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("hero_banner"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hero_construction),
                        contentDescription = "کارگاه ساختمانی و تامین مصالح",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.25f),
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )
                    // Text over banner
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Surface(
                            color = AmberPrimary,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "تخفیف ویژه کارخانجات کرمان",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "تامین مستقیم و آنلاین مصالح ساختمانی",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "تحویل سریع با کمپرسی و خاور در محل کارگاه ساختمانی شما",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFF1F5F9)
                        )
                    }
                }
            }
        }

        // Quick Shortcuts: Calculator, Ready Concrete, Suppliers, Price List
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. Calculator
                QuickActionCard(
                    title = "محاسبه‌گر مصالح",
                    subtitle = "تخمین متر مربع",
                    icon = Icons.Default.Calculate,
                    color = Color(0xFF2563EB),
                    backgroundColor = Color(0xFFEFF6FF),
                    onClick = { viewModel.selectTab(2) },
                    modifier = Modifier.weight(1f)
                )
                // 2. Ready Concrete
                QuickActionCard(
                    title = "سفارش بتن آماده",
                    subtitle = "با پمپ و میکسر",
                    icon = Icons.Default.LocalShipping,
                    color = Color(0xFFD97706),
                    backgroundColor = Color(0xFFFFFBEB),
                    onClick = {
                        viewModel.selectCategory("btn")
                        viewModel.selectTab(1)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 3. Free Delivery Bricks
                QuickActionCard(
                    title = "آجر و سفال",
                    subtitle = "حمل رایگان کرمان",
                    icon = Icons.Default.Widgets,
                    color = Color(0xFF16A34A),
                    backgroundColor = Color(0xFFF0FDF4),
                    onClick = {
                        viewModel.selectCategory("ajr-o-sfal")
                        viewModel.selectTab(1)
                    },
                    modifier = Modifier.weight(1f)
                )
                // 4. Suppliers
                QuickActionCard(
                    title = "تامین‌کنندگان",
                    subtitle = "فروشگاه‌های منتخب",
                    icon = Icons.Default.Storefront,
                    color = Color(0xFF9333EA),
                    backgroundColor = Color(0xFFFAF5FF),
                    onClick = { viewModel.selectTab(4) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Categories section
        item {
            Spacer(modifier = Modifier.height(18.dp))
            SectionHeader(
                title = "دسته‌بندی مصالح ساختمانی",
                subtitle = "انتخاب از میان بیش از ۱۰ دسته‌بندی تخصصی",
                actionText = "مشاهده همه",
                onActionClick = {
                    viewModel.selectCategory(null)
                    viewModel.selectTab(1)
                }
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(SampleData.categories) { cat ->
                    CategoryCard(
                        category = cat,
                        isSelected = uiState.selectedCategoryId == cat.id,
                        onClick = {
                            viewModel.selectCategory(cat.id)
                            viewModel.selectTab(1)
                        }
                    )
                }
            }
        }

        // Best Sellers & Featured Products
        item {
            Spacer(modifier = Modifier.height(20.dp))
            SectionHeader(
                title = "پرفروش‌ترین مصالح امروز",
                subtitle = "ارسال از انبار مرکزی و کارخانجات کرمان",
                actionText = "همه کالاها",
                onActionClick = {
                    viewModel.selectCategory(null)
                    viewModel.selectTab(1)
                }
            )
        }

        // Products Grid (horizontal or staggered pairs)
        val featured = SampleData.products.take(6)
        items(featured.chunked(2)) { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (prod in pair) {
                    ProductCard(
                        product = prod,
                        onProductClick = onProductClick,
                        onAddToCart = { viewModel.addToCart(it, 1) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Direct Factory Order Announcement Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSecondary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(AmberPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneInTalk,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "سفارش مستقیم و استعلام پروژه‌ای",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "پشتیبانی و مشاوره تخصصی مهندسی: ۰۹۱۳۷۴۶۵۲۳۸",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:09137465238"))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("تماس", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .testTag("quick_action_${title}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}
