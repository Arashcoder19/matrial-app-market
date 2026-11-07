package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Category
import com.example.data.Product
import com.example.data.SampleData
import com.example.ui.components.CategoryCard
import com.example.ui.components.ProductCard
import com.example.ui.components.toPersianDigits
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.OnAmberContainer
import com.example.ui.viewmodel.MasalehUiState
import com.example.ui.viewmodel.MasalehViewModel
import com.example.ui.viewmodel.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    uiState: MasalehUiState,
    viewModel: MasalehViewModel,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredProducts = viewModel.getFilteredProducts()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("catalog_screen")
    ) {
        // Top Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "کاتالوگ و لیست قیمت مصالح",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Search Bar
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = { Text("جستجو در بین مصالح...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = AmberPrimary)
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
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AmberPrimary
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Sort Options Scrollable Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "مرتب‌سازی:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    SortOption.values().forEach { option ->
                        val isSelected = uiState.selectedSort == option
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.selectSort(option) },
                            label = {
                                Text(
                                    text = option.titleFa,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AmberContainer,
                                selectedLabelColor = OnAmberContainer
                            )
                        )
                    }
                }
            }
        }

        // Horizontal Category Tabs
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // "همه دسته‌ها" (All)
            item {
                val isAllSelected = uiState.selectedCategoryId == null
                ElevatedFilterChip(
                    selected = isAllSelected,
                    onClick = { viewModel.selectCategory(null) },
                    label = {
                        Text(
                            text = "همه دسته‌ها (${SampleData.products.size.toString().toPersianDigits()})",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        selectedContainerColor = AmberPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }

            items(SampleData.categories) { cat ->
                val isSelected = uiState.selectedCategoryId == cat.id
                ElevatedFilterChip(
                    selected = isSelected,
                    onClick = { viewModel.selectCategory(cat.id) },
                    label = {
                        Text(
                            text = cat.name,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        selectedContainerColor = AmberPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Active Category Header or Filter Info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val categoryTitle = SampleData.categories.find { it.id == uiState.selectedCategoryId }?.name ?: "همه مصالح ساختمانی"
            Text(
                text = "$categoryTitle (${filteredProducts.size.toString().toPersianDigits()} کالا)",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )

            if (uiState.selectedCategoryId != null || uiState.searchQuery.isNotEmpty()) {
                TextButton(
                    onClick = {
                        viewModel.selectCategory(null)
                        viewModel.setSearchQuery("")
                    },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "حذف فیلترها",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmberPrimary
                    )
                }
            }
        }

        // Product list
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "موردی با این مشخصات یافت نشد",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "عبارت دیگری را جستجو کنید یا فیلتر دسته‌بندی را تغییر دهید",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProducts.chunked(2)) { pair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
            }
        }
    }
}
