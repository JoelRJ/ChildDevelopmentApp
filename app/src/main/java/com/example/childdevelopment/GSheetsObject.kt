package com.example.childdevelopment

import com.google.gson.annotations.SerializedName

class GSheetsObject (
    @SerializedName("range")
    var range: String,
    @SerializedName("majorDimension")
    var majorDimension: String,
    @SerializedName("values")
    var values: ArrayList<String>
)
