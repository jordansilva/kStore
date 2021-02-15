package com.jordansilva.kstore.util

import org.json.JSONArray
import org.json.JSONObject

inline fun <R> JSONArray.map(transform: (JSONObject) -> R): List<R> {
    val destination = arrayListOf<R>()
    repeat(this.length()) { index -> destination.add(transform(this.getJSONObject(index))) }
    return destination
}

fun JSONObject.getStringOrNull(name: String): String? = if (has(name)) getString(name) else null