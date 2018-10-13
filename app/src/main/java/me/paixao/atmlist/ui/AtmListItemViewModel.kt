package me.paixao.atmlist.ui

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import me.paixao.atmlist.data.Atm
import me.paixao.atmlist.utils.GlideApp
import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class AtmListItemViewModel(val context: Context,
                           val atm: Atm) {


    fun dateParse(s: String): Date? = DateHelper.DF_SIMPLE_FORMAT.get()?.parse(s, ParsePosition(0))

    object DateHelper {
        const val DF_SIMPLE_STRING = "yyyy-MM-dd"
        @JvmField
        val DF_SIMPLE_FORMAT = object : ThreadLocal<DateFormat>() {
            override fun initialValue(): DateFormat {
                return SimpleDateFormat(DF_SIMPLE_STRING, Locale.US)
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:typeface")
        fun setTypeface(v: TextView, style: String) {
            when (style) {
                "bold" -> v.setTypeface(null, Typeface.BOLD)
                else -> v.setTypeface(null, Typeface.NORMAL)
            }
        }

        @JvmStatic
        @BindingAdapter("app:imagesrc")
        fun setImageSource(v: View, s: String?) {
            if (s != null)
                GlideApp.with(v.context)
                        .load(s)
                        //.thumbnail(Glide.with(v.context).load(R.drawable.loader))
                        // TODO add image for error
                        .centerCrop()
                        .into(v as ImageView)
        }
    }

}