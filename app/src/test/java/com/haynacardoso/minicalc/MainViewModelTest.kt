package com.haynacardoso.minicalc

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.haynacardoso.minicalc.ui.main.MainViewModel
import com.haynacardoso.minicalc.data.repository.FakeHistoryRepository
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeRepo = FakeHistoryRepository()
    private val viewModel = MainViewModel(fakeRepo)

    @Test
    fun testSimpleAddition() {
        viewModel.calculate("2 + 2")
        assertEquals("4", viewModel.result.value)
    }

    @Test
    fun testIntegerFormatting() {
        viewModel.calculate("10 / 5")
        assertEquals("2", viewModel.result.value)
    }

    @Test
    fun testDecimalWithComma() {
        viewModel.calculate("5 / 2")
        assertEquals("2,5", viewModel.result.value?.replace(".", ","))
    }

    @Test
    fun testInvalidExpression() {
        viewModel.calculate("2 +")
        assertEquals("Erro", viewModel.result.value)
    }

    @Test
    fun testSaveToHistory() = runTest {
        viewModel.saveToHistory(mockContext(), "6 × 2 = 12")
        advanceUntilIdle()
        val history = fakeRepo.get(mockContext()).first()
        assertTrue(history.contains("6 × 2 = 12"))
    }

    @Test
    fun testClearHistory() = runTest {
        viewModel.saveToHistory(mockContext(), "8 + 8 = 16")
        advanceUntilIdle()
        viewModel.clearHistory(mockContext())
        advanceUntilIdle()
        val history = fakeRepo.get(mockContext()).first()
        assertEquals("", history)
    }

    private fun mockContext(): Context = mockk(relaxed = true)
}