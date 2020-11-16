package com.andrempuerto.meli.model

data class Product (
    var name: String,
    var price: String,
    var currencyId: String,
    var availableQuantity: Int,
    var soldQuantity: Int,
    var condition: String,
    var permalink: String,
    var thumbnail: String,
    var acceptsMp: Boolean,
    var installments: Installment,
    var address: Address,
    var shipping: Shipping,
    var attributes: List<Attribute>
)

data class Installment(
    var quantity: Int,
    var amount: Float,
    var rate: Float
)

data class Address(
    var stateName: String,
    var cityName: String
)

data class Shipping(
    var freeShipping: Boolean
)

data class Attribute(
    var name: String,
    var valueName: String? = "-"
)
