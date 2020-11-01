package com.example.childdevelopment

import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.GET

//https://proandroiddev.com/utilising-google-sheets-as-a-realtime-database-for-an-android-application-c56c1a56da2f
//https://docs.google.com/spreadsheets/d/18EKaZAeyj_b7nXH0n-OaPULJsdVp2kknicNh76Ma6KA/edit?usp=sharing
interface ApiInterface {
    @GET("v4/spreadsheets/" +
            "18EKaZAeyj_b7nXH0n-OaPULJsdVp2kknicNh76Ma6KA/" +
            "values/Sheet1!A1:A4" +
            "?key=AIzaSyD2oQfzSYDR73TNJCFtYGvHRCUf9LdqnzA")
    fun getMenu() : Call<GSheetsObject>
}

