package com.feifood.feifood.model


fun validateName(input: String): Boolean {
    val namePattern = "^[a-zA-Z0-9_]{1,50}$".toRegex()

    return namePattern.matches(input)
}

fun validatePrice(input: String): Boolean {
    val pricePattern = "^(\\d+)(\\.\\d{1,2})?$".toRegex()

    return pricePattern.matches(input)
}
