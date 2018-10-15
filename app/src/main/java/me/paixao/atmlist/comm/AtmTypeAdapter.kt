package me.paixao.atmlist.comm

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import me.paixao.atmlist.data.Atm

class AtmTypeAdapter : TypeAdapter<Atm>() {

    override fun write(out: JsonWriter?, value: Atm?) {
        TODO("not implemented")
    }

    override fun read(reader: JsonReader): Atm? {
        try {
            val atmOutput = Atm()
            reader.beginObject()
            while(reader.hasNext()) {
                val name = reader.nextName()
                when(name) {
                    "address" -> {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            val nameAddr = reader.nextName()
                            when(nameAddr) {
                                "formatted" -> atmOutput.address = reader.nextString()
                                else -> reader.skipValue()
                            }
                        }
                        reader.endObject()
                    }
                    "sonectId" -> atmOutput.sonectId = reader.nextString()
                    "name" -> atmOutput.name = reader.nextString()
                    "latitude" -> atmOutput.latitude = reader.nextString()
                    "longitude" -> atmOutput.longitude = reader.nextString()
                    "imagePath" -> atmOutput.imagePath = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()
            return atmOutput
        } catch(e: Exception) {
            return null
        }
    }

}