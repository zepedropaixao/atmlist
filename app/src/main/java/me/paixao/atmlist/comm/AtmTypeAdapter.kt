package me.paixao.atmlist.comm

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import me.paixao.atmlist.data.Atm
import java.io.IOException

class AtmTypeAdapter : TypeAdapter<Atm>() {
    val gson = Gson()

    val elementAdapter = gson.getAdapter(JsonElement::class.java)
    override fun write(out: JsonWriter?, value: Atm?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(`in`: JsonReader?): Atm {
        val jsonElement = elementAdapter.read(`in`)
        val output = delegate.fromJsonTree(jsonElement)
        if (jsonElement.isJsonObject) {
            val jsonObject = jsonElement.asJsonObject
            if (jsonObject.has("address") && jsonObject.get("address").isJsonObject) {
                val address = jsonObject.get("address").asJsonObject
                output.address = address.get("formatted").asString
            }
        }
        return output
    }

    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {



        return object : TypeAdapter<Atm>() {

            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Atm) {
                delegate.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): Atm {

            }
        }.nullSafe()
    }
}