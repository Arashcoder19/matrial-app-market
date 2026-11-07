package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.toPersianDigits
import com.example.ui.components.toPersianNumber
import com.example.ui.screens.*
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.MasalehMarketTheme
import com.example.ui.theme.OnAmberContainer
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.MasalehViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasalehMarketTheme {
                // Ensure natural RTL layout for Persian (Farsi)
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    MasalehApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasalehApp(viewModel: MasalehViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Back handling
    BackHandler(enabled = uiState.selectedProductForDetail != null || uiState.selectedTab != 0) {
        if (uiState.selectedProductForDetail != null) {
            viewModel.showProductDetail(null)
        } else if (uiState.selectedTab != 0) {
            viewModel.selectTab(0)
        }
    }

    // Success Dialog on Order Completion
    if (uiState.orderSuccess != null) {
        val order = uiState.orderSuccess!!
        AlertDialog(
            onDismissRequest = { viewModel.dismissOrderSuccess() },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDCFCE7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "سفارش با موفقیت ثبت شد!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "شماره پیگیری سفارش شما:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = AmberContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = order.orderNumber,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnAmberContainer,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "کارشناسان انبار و ترابری مصالح مارکت هم‌اکنون در حال بررسی و آماده‌سازی بارگیری سفارش شما هستند و جهت هماهنگی ورود خودرو با شما تماس خواهند گرفت.",
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 20.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.dismissOrderSuccess()
                        viewModel.selectTab(4) // Navigate to Orders screen
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("مشاهده در بخش سفارش‌های من")
                }
            }
        )
    }

    // Product Detail Bottom Sheet
    if (uiState.selectedProductForDetail != null) {
        ProductDetailSheet(
            product = uiState.selectedProductForDetail!!,
            onDismiss = { viewModel.showProductDetail(null) },
            onAddToCart = { prod, qty ->
                viewModel.addToCart(prod, qty)
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier.testTag("main_bottom_nav")
            ) {
                // 1. Home
                NavigationBarItem(
                    selected = uiState.selectedTab == 0,
                    onClick = { viewModel.selectTab(0) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "خانه"
                        )
                    },
                    label = { Text("خانه", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_item_home")
                )

                // 2. Catalog
                NavigationBarItem(
                    selected = uiState.selectedTab == 1,
                    onClick = { viewModel.selectTab(1) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.selectedTab == 1) Icons.Filled.Widgets else Icons.Outlined.Widgets,
                            contentDescription = "کاتالوگ"
                        )
                    },
                    label = { Text("کاتالوگ", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_item_catalog")
                )

                // 3. Calculator
                NavigationBarItem(
                    selected = uiState.selectedTab == 2,
                    onClick = { viewModel.selectTab(2) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.selectedTab == 2) Icons.Filled.Calculate else Icons.Outlined.Calculate,
                            contentDescription = "محاسبه‌گر"
                        )
                    },
                    label = { Text("محاسبه‌گر", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_item_calculator")
                )

                // 4. Cart
                NavigationBarItem(
                    selected = uiState.selectedTab == 3,
                    onClick = { viewModel.selectTab(3) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (uiState.cartItems.isNotEmpty()) {
                                    Badge(
                                        containerColor = AmberPrimary,
                                        contentColor = Color.White
                                    ) {
                                        Text(uiState.cartItems.size.toString().toPersianDigits())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (uiState.selectedTab == 3) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                                contentDescription = "سبد خرید"
                            )
                        }
                    },
                    label = { Text("سبد خرید", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_item_cart")
                )

                // 5. Suppliers / Orders
                NavigationBarItem(
                    selected = uiState.selectedTab == 4,
                    onClick = { viewModel.selectTab(4) },
                    icon = {
                        Icon(
                            imageVector = if (uiState.selectedTab == 4) Icons.Filled.Storefront else Icons.Outlined.Storefront,
                            contentDescription = "تامین‌کنندگان"
                        )
                    },
                    label = { Text("تامین‌کنندگان", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AmberPrimary,
                        selectedTextColor = AmberPrimary,
                        indicatorColor = AmberContainer
                    ),
                    modifier = Modifier.testTag("nav_item_suppliers")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.selectedTab) {
                0 -> HomeScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onProductClick = { viewModel.showProductDetail(it) }
                )
                1 -> CatalogScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onProductClick = { viewModel.showProductDetail(it) }
                )
                2 -> CalculatorScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                3 -> CartScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                4 -> SuppliersAndOrdersScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
            }
        }
    }
}
