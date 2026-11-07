package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CalculationResult
import com.example.data.SampleData
import com.example.ui.components.toPersianDigits
import com.example.ui.components.toPersianToman
import com.example.ui.theme.*
import com.example.ui.viewmodel.MasalehUiState
import com.example.ui.viewmodel.MasalehViewModel

@Composable
fun CalculatorScreen(
    uiState: MasalehUiState,
    viewModel: MasalehViewModel,
    modifier: Modifier = Modifier
) {
    val modes = listOf("دیوارچینی", "بتن‌ریزی سقف و پی", "چسب کاشی و اسلب")

    // Dynamic result calculation
    val currentResult: CalculationResult = when (uiState.calcTypeIndex) {
        0 -> {
            val length = uiState.wallLength.toDoubleOrNull() ?: 10.0
            val height = uiState.wallHeight.toDoubleOrNull() ?: 3.0
            SampleData.calculateWallMasonry(length, height, uiState.wallThicknessCm)
        }
        1 -> {
            val area = uiState.concreteArea.toDoubleOrNull() ?: 80.0
            val thickness = uiState.concreteThicknessCm.toDoubleOrNull() ?: 15.0
            SampleData.calculateConcreteSlab(area, thickness)
        }
        else -> {
            val area = uiState.tileArea.toDoubleOrNull() ?: 50.0
            SampleData.calculateTileWork(area)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("calculator_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AmberContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AmberPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "محاسبه‌گر آنلاین مصالح ساختمانی",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnAmberContainer
                        )
                        Text(
                            text = "تخمین دقیق تعداد مصالح و هزینه پروژه‌های ساختمانی در کرمان",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnAmberContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        // Segmented Mode Selector
        item {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                modes.forEachIndexed { index, title ->
                    SegmentedButton(
                        selected = uiState.calcTypeIndex == index,
                        onClick = { viewModel.setCalcTypeIndex(index) },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = modes.size),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = AmberPrimary,
                            activeContentColor = Color.White
                        )
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (uiState.calcTypeIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Input Fields Card
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "مشخصات و ابعاد پروژه",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    when (uiState.calcTypeIndex) {
                        0 -> { // Wall
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedTextField(
                                    value = uiState.wallLength,
                                    onValueChange = { viewModel.setWallInputs(it, uiState.wallHeight, uiState.wallThicknessCm) },
                                    label = { Text("طول دیوار (متر)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = uiState.wallHeight,
                                    onValueChange = { viewModel.setWallInputs(uiState.wallLength, it, uiState.wallThicknessCm) },
                                    label = { Text("ارتفاع دیوار (متر)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Text(
                                text = "نوع مصالح دیوارچینی:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val wallTypes = listOf(
                                    Triple(10, "تیغه ۱۰ سفال", "داخلی"),
                                    Triple(15, "بلوک ۱۵ لیکا", "خارجی"),
                                    Triple(20, "آجر فشاری", "باربر")
                                )
                                wallTypes.forEach { (thick, name, sub) ->
                                    val isSelected = uiState.wallThicknessCm == thick
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { viewModel.setWallInputs(uiState.wallLength, uiState.wallHeight, thick) },
                                        label = {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(name, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                                Text("($sub)", style = MaterialTheme.typography.labelSmall)
                                            }
                                        },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = AmberContainer,
                                            selectedLabelColor = OnAmberContainer
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                        1 -> { // Concrete
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedTextField(
                                    value = uiState.concreteArea,
                                    onValueChange = { viewModel.setConcreteInputs(it, uiState.concreteThicknessCm) },
                                    label = { Text("مساحت دال/پی (متر مربع)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = uiState.concreteThicknessCm,
                                    onValueChange = { viewModel.setConcreteInputs(uiState.concreteArea, it) },
                                    label = { Text("ضخامت بتن (سانتی‌متر)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        2 -> { // Tile
                            OutlinedTextField(
                                value = uiState.tileArea,
                                onValueChange = { viewModel.setTileInputs(it) },
                                label = { Text("مساحت کل سرامیک، اسلب یا کاشی‌کاری (متر مربع)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        // Calculation Results Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("calculation_result_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentResult.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AmberPrimary
                        )
                        Surface(
                            color = Color(0xFFDCFCE7),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "محاسبه استاندارد",
                                color = Color(0xFF15803D),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentResult.summary.toPersianDigits(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    // Materials list
                    Text(
                        text = "مصالح مورد نیاز تخمین زده شده:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    currentResult.materials.forEach { item ->
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = SuccessGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Text(
                                    text = "${item.amount.toPersianDigits()} ${item.unit}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = AmberPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Estimated Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "برآورد تقریبی هزینه مصالح:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = currentResult.estimatedCost.toPersianToman(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AmberPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Add all to cart button
                    Button(
                        onClick = { viewModel.addCalculationToCart(currentResult) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("add_calculated_to_cart_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "افزودن این مصالح به سبد خرید",
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
