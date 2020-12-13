package com.example.childdevelopment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import retrofit2.*
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), RecyclerAdapter.CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //actionBar?.title = "Child Development"
       // supportActionBar?.title = "Child Development"
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.actionbar_layout)

        // RecyclerView for Main Menu
        main_menu_RV.layoutManager = LinearLayoutManager(this)
        main_menu_RV.adapter = RecyclerAdapter(arrayListOf(), this, this)

        // Populate menu with options from cloud
        populateMenu()
    }

    fun populateMenu() = CoroutineScope(Dispatchers.Main).launch {
        val menuData = makeNetworkCall()
        var count = 0
        menuData.forEach{
            Log.d("$count", it)
            count += 1
        }

        main_menu_RV.layoutManager = LinearLayoutManager(this@MainActivity)
        main_menu_RV.adapter = RecyclerAdapter(menuData, this@MainActivity, this@MainActivity)

    }

    // Get data from .csv
    suspend fun makeNetworkCall(): List<String> = withContext(Dispatchers.IO) IO@{
//      stackoverflow.com/questions/29802323/android-with-kotlin-how-to-use-httpurlconnection
//      stackoverflow.com/questions/45940861/android-8-cleartext-http-traffic-not-permitted
//      stackoverflow.com/questions/59472320/kotlin-read-and-display-data-from-google-sheets
        val url = URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vSuBhdNE5_" +
                "vEvM0xtpvD9WpPfJM3T4WIVLKjZhWEu19-Sn3giVFo2u4dgDHyzbw8dATbnV1pARaiKY3/pub?output=csv")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

        val data: String
        try {
            data = urlConnection.inputStream.bufferedReader().use { it.readText() }
        } finally {
            urlConnection.disconnect()
        }
        var result = data.lines().map { it.trim() }
        return@IO result
    }

    // Old method: API call to Google Sheets
    private fun getMenu() {
        var apiInterface = APIClient.client.create(ApiInterface::class.java)

        val call = apiInterface.getMenu()
        call.enqueue(object : Callback<GSheetsObject> {
            override fun onResponse(call: Call<GSheetsObject>, response: Response<GSheetsObject>) {
                //Log.d("Success!", response.toString())
                //var text = response.body()
                //var Gvalues = text?.values

//                if (Gvalues != null) {
//                    for (x in Gvalues) {
//                        Log.d("Value: ", x.toString())
//                    }
//                }

                //Log.d("Success!", Gvalues.toString())
            }

            override fun onFailure(call: Call<GSheetsObject>, t: Throwable)                  {
                Log.e("Failed Query :(", t.toString())

            }
        })
    }

    override fun onCellClickListener(stringIn: String) {
        Log.d("This is: ", stringIn)
    }

    override fun onLongClick(stringIn: String) {

    }
}
