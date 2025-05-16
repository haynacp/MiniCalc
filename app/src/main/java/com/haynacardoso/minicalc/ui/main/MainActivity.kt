package com.haynacardoso.minicalc.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.haynacardoso.minicalc.databinding.ActivityMainBinding
import com.haynacardoso.minicalc.utils.ExpressionEvaluator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val expression = StringBuilder()
    private val displayExpression = StringBuilder()
    private var justEvaluated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Mini calculadora - Prova Ifood"

        viewModel.observeHistory(this)

        viewModel.historyText.observe(this) { history ->
            binding.tvHistory.text = history
        }

        val numberButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                val digit = button.text.toString()
                expression.append(digit)
                displayExpression.append(digit)
                updateDisplay()
            }
        }

        binding.btnPlus.setOnClickListener {
            appendOperator("+", "+")
        }

        binding.btnMinus.setOnClickListener {
            appendOperator("-", "−")
        }

        binding.btnMultiply.setOnClickListener {
            appendOperator("*", "×")
        }

        binding.btnDivide.setOnClickListener {
            appendOperator("/", "÷")
        }

        binding.btnDot.setOnClickListener {
            expression.append(".")
            displayExpression.append(",")
            updateDisplay()
        }

        binding.btnClear.setOnClickListener {
            expression.clear()
            displayExpression.clear()
            updateDisplay("0")
        }

        binding.btnEquals.setOnClickListener {
            try {
                val result = ExpressionEvaluator.evaluate(expression.toString())

                val rawDisplay = displayExpression.toString()

                val formatted = formatResult(result).replace(".", ",")

                updateDisplay(formatted)

                viewModel.saveToHistory(this, "$rawDisplay = $formatted")

                expression.clear()
                displayExpression.clear()
                expression.append(result.toString())
                displayExpression.append(formatted)

                justEvaluated = true

            } catch (e: Exception) {
                updateDisplay("Erro")
                expression.clear()
                displayExpression.clear()
                justEvaluated = false
            }
        }


        binding.btnClearHistory.setOnClickListener {
            viewModel.clearHistory(this)
        }

    }

    private fun appendOperator(op: String, display: String) {
        if (expression.isNotEmpty() && !expression.last().isOperator()) {
            if (justEvaluated) {
                displayExpression.clear()
                displayExpression.append(
                    formatResult(expression.toString().toDouble()).replace(
                        ".",
                        ","
                    )
                )
                justEvaluated = false
            }

            expression.append(op)
            displayExpression.append(" $display ")
            updateDisplay()
        }
    }

    private fun updateDisplay(text: String? = null) {
        binding.tvResult.text = text ?: displayExpression.toString()
    }

    private fun Char.isOperator(): Boolean {
        return this == '+' || this == '-' || this == '*' || this == '/'
    }

    private fun formatResult(result: Double): String {
        return if (result % 1.0 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }
}