package com.example.valorantpruebaapi

import ClasesApi.AgentsResponse
import ClasesApi.ValorantService
import ClasesApi.WeaponsResponse
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


        val valorantService = ValorantService.create()

        //WEAPONS
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

        //AGENTS
        valorantService.getAgents().enqueue(object : Callback<AgentsResponse> {
            override fun onResponse(
                call: Call<AgentsResponse>,
                response: Response<AgentsResponse>
            ) {
                if (response.isSuccessful) {
                    val AgentsResponse = response.body()
                    AgentsResponse?.data?.forEach { agent ->
                        Log.d("MANIII", "Agent: ${agent.displayName}")
                        Log.d("MANIII", "Role: ${agent.role.displayName}")
                        for (x in agent.abilities){
                            Log.d("MANIII", "${x.slot} \n${x.displayName} - ${x.description}")}
                    }
                    // y así sucesivamente
                    Log.d("MANIII", AgentsResponse.toString())
                } else {
                    Log.e("MANIII", response.message())
                }
            }

            override fun onFailure(call: Call<AgentsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }
        })

        //MAPS
    }
}