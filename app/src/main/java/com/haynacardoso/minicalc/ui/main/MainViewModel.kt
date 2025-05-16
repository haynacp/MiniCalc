package com.haynacardoso.minicalc.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haynacardoso.minicalc.domain.HistoryDataSource
import com.haynacardoso.minicalc.utils.ExpressionEvaluator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val historyRepository: HistoryDataSource
) : ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    private val _historyText = MutableLiveData<String>()
    val historyText: LiveData<String> = _historyText

    fun calculate(expression: String) {
        try {
            val evaluation = ExpressionEvaluator.evaluate(expression)
            _result.value = formatResult(evaluation)
        } catch (e: Exception) {
            _result.value = "Erro"
        }
    }

    private fun formatResult(result: Double): String {
        return if (result % 1.0 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }

    fun observeHistory(context: Context) {
        viewModelScope.launch {
            historyRepository.get(context).collect { history ->
                _historyText.postValue(history)
            }
        }
    }

    fun saveToHistory(context: Context, expression: String) {
        viewModelScope.launch {
            historyRepository.save(context, expression)
        }
    }

    fun clearHistory(context: Context) {
        viewModelScope.launch {
            historyRepository.clear(context)
        }
    }
}