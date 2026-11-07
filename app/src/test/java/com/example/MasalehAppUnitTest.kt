package com.example

import com.example.data.SampleData
import com.example.ui.components.toPersianDigits
import org.junit.Assert.*
import org.junit.Test

class MasalehAppUnitTest {

    @Test
    fun testPersianDigitsConversion() {
        val english = "12345"
        val persian = english.toPersianDigits()
        assertEquals("۱۲۳۴۵", persian)
    }

    @Test
    fun testWallCalculation() {
        val result = SampleData.calculateWallMasonry(10.0, 3.0, 10)
        assertNotNull(result)
        assertTrue(result.materials.isNotEmpty())
        assertTrue(result.estimatedCost > 0)
    }

    @Test
    fun testConcreteCalculation() {
        val result = SampleData.calculateConcreteSlab(100.0, 15.0)
        assertNotNull(result)
        assertTrue(result.materials.isNotEmpty())
        assertTrue(result.estimatedCost > 0)
    }

    @Test
    fun testCategoriesAndProductsNotEmpty() {
        assertTrue(SampleData.categories.isNotEmpty())
        assertTrue(SampleData.products.isNotEmpty())
        assertTrue(SampleData.suppliers.isNotEmpty())
    }
}
