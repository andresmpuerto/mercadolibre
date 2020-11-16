package com.andrempuerto.meli.data.source.network.dto

import com.andrempuerto.meli.model.*
import com.google.gson.annotations.SerializedName

data class ProductDto(
    var id: String,
    @SerializedName("site_id") var siteId: String,
    var title: String,
    var price: Float,
    @SerializedName("currency_id") var currencyId: String,
    @SerializedName("available_quantity") var availableQuantity: Int,
    @SerializedName("sold_quantity") var soldQuantity: Int,
    var condition: String,
    var permalink: String,
    var thumbnail: String,
    @SerializedName("accepts_mercadopago") var acceptsMp: Boolean,
    var installments: InstallmentDto,
    var address: AddressDto,
    var shipping: ShippingDto,
    var attributes: List<AttributeDto>
)

fun List<ProductDto>.asProductModel(): List<Product> =
    map {
        Product(
            name = it.title,
            price = it.price.toString(),
            currencyId = it.currencyId,
            availableQuantity = it.availableQuantity,
            soldQuantity = it.soldQuantity,
            condition = it.condition,
            permalink = it.permalink,
            thumbnail = it.thumbnail,
            acceptsMp = it.acceptsMp,
            installments = it.installments.asInstallmentModel(),
            address = it.address.asAddressModel(),
            shipping = it.shipping.asShippingModel(),
            attributes = it.attributes.asListAttributeModel(),
        )
    }

data class InstallmentDto(
    var quantity: Int,
    var amount: Float,
    var rate: Float
)

fun InstallmentDto.asInstallmentModel(): Installment = Installment(quantity, amount, rate)

data class AddressDto(
    @SerializedName("state_name") var stateName: String,
    @SerializedName("city_name") var cityName: String
)

fun AddressDto.asAddressModel(): Address = Address(stateName, cityName)

data class ShippingDto(
    @SerializedName("free_shipping") var freeShipping: Boolean
)

fun ShippingDto.asShippingModel(): Shipping = Shipping(freeShipping)

data class AttributeDto(
    var name: String,
    @SerializedName("value_name") var valueName: String?
)

fun List<AttributeDto>.asListAttributeModel(): List<Attribute> =
    map { Attribute(it.name, it.valueName) }
