package com.feifood.feifood.model

import java.sql.Date

data class Sale(
    val product: Product,
    val quantity: Int,
    val date: Date
)
