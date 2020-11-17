package com.andrempuerto.meli.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrempuerto.meli.R
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import java.util.*


@BindingAdapter("setAdapter")
fun bindAdapter(recyclerView: RecyclerView, adapter: PagingDataAdapter<*, *>) {
    recyclerView.apply {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(recyclerView.context)
        this.adapter = adapter
    }
}

@BindingAdapter("isVisible")
fun bindToggle(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("isShippingFree", "isMP")
fun bindMercadoPagoShippingFree(view: MaterialTextView, isFree: Boolean, isMP: Boolean) {
    val builder = SpannableStringBuilder()
    val str1 = SpannableString(view.context.getString(R.string.text_shipping_free))
    str1.setSpan(ForegroundColorSpan(view.context.getColor(R.color.teal_700)), 0, str1.length, 0)

    val mp = String.format(
        "Mercado Pago: %s ",
        view.context.getString(if (isMP) R.string.text_yes_mp else R.string.text_no_mp),
    )
    view.text = if (isFree) builder.append(SpannableString(mp)).append(" - ").append(str1) else mp
}

@BindingAdapter("condition")
fun bindConditionProduct(view: MaterialTextView, condition: String) {
    view.text = view.context.getString(
        when (condition) {
            ConditionProduct.NEW.value -> ConditionProduct.NEW.text
            else -> ConditionProduct.USED.text
        }
    )
}

@BindingAdapter("city")
fun bindCityProduct(view: MaterialTextView, cityName: String) {
    view.text = cityName
}

@BindingAdapter("available")
fun bindAvailable(view: MaterialTextView, availableQuantity: Int) {
    view.text = availableQuantity.toString()
}

@BindingAdapter("currency", "amount")
fun bindAmountProduct(view: MaterialTextView, currency: String, price: String) {
    when (currency) {
        CurrencyProduct.COP.value -> {
            view.text = String.format(
                Locale.US,
                "%s $ %,.0f",
                currency,
                price.toDouble()
            )
        }

        CurrencyProduct.ARS.value -> {
            view.text = String.format(
                Locale.GERMANY,
                "%s $ %,.2f",
                currency,
                price.toDouble()
            )
        }
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    if (url.isEmpty()) {
        imageView.setImageDrawable(
            ContextCompat.getDrawable(imageView.context, R.mipmap.ic_launcher)
        )
    } else {
        imageView.context.also {
            Glide
                .with(it)
                .load(url.replace("http://", "https://"))
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }
}

enum class ConditionProduct(val value: String, val text: Int) {
    NEW("new", R.string.text_condition_new),
    USED("used", R.string.text_condition_used)
}

enum class CurrencyProduct(val value: String) {
    COP("COP"),
    ARS("ARS")
}