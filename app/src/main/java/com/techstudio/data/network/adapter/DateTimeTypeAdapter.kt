package com.techstudio.data.network.adapter
import com.google.gson.*
import timber.log.Timber
import java.lang.Exception
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

const val datePattern: String = "yyyy-MM-dd kk:mm:ss"
val dateFormatter = SimpleDateFormat(datePattern, Locale.getDefault())

object DateTimeTypeAdapter : JsonSerializer<Calendar>, JsonDeserializer<Calendar> {

    override fun serialize(
        src: Calendar?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? =
        src?.let { JsonPrimitive(dateFormatter.format(src.timeInMillis)) }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Calendar? {
        if (json == null) return null
        val calendar = Calendar.getInstance()
        try {
            calendar.time = dateFormatter.parse(json.asJsonPrimitive.asString)
        } catch (e: Exception) {
            Timber.i("Error parsing ${json.asJsonPrimitive.asString}")
            Timber.e(e)
        }
        return calendar
    }
}

fun Calendar.toStringPattern(): String? {
    return dateFormatter.format(timeInMillis)
}