package com.feifood.feifood.model

class Product(
    var name: String = "", // Constructor sin argumentos con valor predeterminado
    var description: String = "",
    var price: Double = 0.0,
    var userId: String? = null, // Proporciona un valor predeterminado
    var picturePath: String = "",
    var category: String? = null, // Proporciona un valor predeterminado
    var cuantity: Int = 0
) {
    // Constructor vacío necesario para Firestore
    constructor() : this("", "", 0.0, null, "", null, 0)

    companion object {
        fun create(name: String, description: String, price: String, picturePath: String, category: String?, userId: String?): Product? {
            if (!validateName(name)) {
                throw IllegalArgumentException("El nombre solo debe contener letras y números")
            }
            if (!validatePrice(price)) {
                throw IllegalArgumentException("El precio ingresado no es válido")
            }
            return Product(name, description, price.toDouble(), userId = userId, picturePath, category = category)
        }
    }
}
