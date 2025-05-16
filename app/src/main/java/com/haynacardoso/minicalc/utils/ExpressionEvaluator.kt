package com.haynacardoso.minicalc.utils

object ExpressionEvaluator {
    fun evaluate(expression: String): Double {
        val tokens = expression.replace(" ", "")
            .split("(?<=[-+*/])|(?=[-+*/])".toRegex())
            .toMutableList()

        val stack = mutableListOf<Double>()
        var operator: String? = null

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> {
                    val number = token.toDouble()
                    if (operator == null) {
                        stack.add(number)
                    } else {
                        val prev = stack.removeAt(stack.lastIndex)
                        val result = when (operator) {
                            "+" -> prev + number
                            "-" -> prev - number
                            "*" -> prev * number
                            "/" -> prev / number
                            else -> throw IllegalArgumentException("Operador inválido")
                        }
                        stack.add(result)
                        operator = null
                    }
                }

                token in listOf("+", "-", "*", "/") -> operator = token
                else -> throw IllegalArgumentException("Token inválido")
            }
        }

        return stack.last()
    }
}