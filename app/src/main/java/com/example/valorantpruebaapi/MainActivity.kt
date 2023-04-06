package com.example.valorantpruebaapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MANIII","PEPEEEEEE")

        val valorantService = ValorantService.create()
        valorantService.getWeapons().enqueue(object : Callback<WeaponsResponse> {
            override fun onResponse(
                call: Call<WeaponsResponse>,
                response: Response<WeaponsResponse>
            ) {
                if (response.isSuccessful) {
                    val weaponsResponse = response.body()
                    weaponsResponse?.data?.forEach { weapon ->
                        Log.d("MANIII", "Weapon: ${weapon.displayName}")
                        Log.d("MANIII", "Category: ${weapon.category}")
                        Log.d("MANIII", "Default Skin UUID: ${weapon.defaultSkinUuid}")}
                        // y así sucesivamente
                    Log.d("MANIII", weaponsResponse.toString())
                } else {
                    Log.e("MANIII", response.message())
                }
            }

            override fun onFailure(call: Call<WeaponsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }
        })
    }
}