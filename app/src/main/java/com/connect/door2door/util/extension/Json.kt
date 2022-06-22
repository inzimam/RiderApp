package com.connect.door2door.util.extension

import com.connect.door2door.data.remote.dto.RiderDataDto
import com.connect.door2door.domain.model.Data
import com.connect.door2door.domain.model.IntermediateList
import com.connect.door2door.domain.model.RiderData
import com.connect.door2door.util.Constants.EMPTY
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

/**
 * This extension method is used to parse json to data class
 *
 * @param json This is the string input which need to be parsed
 * @return RiderData This returns RiderData object after parsing the string
 */
fun parseData(json: String): RiderData {
    val format = Json { ignoreUnknownKeys = true }
    return if (format.parseToJsonElement(json).jsonObject["data"] is JsonObject) {
        val riderDataDto = format.decodeFromString<RiderDataDto>(json)
        riderDataDto.toRiderData()
    } else if (format.parseToJsonElement(json).jsonObject["data"] is JsonArray) {
        val interMediateDataDto = format.decodeFromString<IntermediateList>(json)
        interMediateDataDto.toRiderData()
    } else {
        val jsonObj = format.parseToJsonElement(json).jsonObject
        val riderDataDto =
            RiderDataDto(
                Data(status = jsonObj["data"].toString().replace("\"", EMPTY)),
                jsonObj["event"].toString().replace("\"", EMPTY)
            )
        riderDataDto.toRiderData()
    }
}