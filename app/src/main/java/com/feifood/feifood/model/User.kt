package com.feifood.feifood.model

data class User(
    var userId: String? = null,
    var name: String = "",
    var paternalSurname: String = "",
    var maternalSurname: String = "",
    var identifier: String = "",
    var email: String = "",
    var phoneNumber: String = "",
     // Puedes usar String? si puede ser null
    var password:String
) {
    // Constructor sin argumentos
    constructor() : this("", "" , "", "", "", "", "", "")
}