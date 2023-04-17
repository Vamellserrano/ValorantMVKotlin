package com.example.valorantpruebaapi

import ClasesApi.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.valorantpruebaapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

//        //AGENTS
//        //PROBLEM TO SOLVE: When we reach Sova it calls the object as null
//        valorantService.getAgents().enqueue(object : Callback<AgentsResponse> {
//            override fun onResponse(
//                call: Call<AgentsResponse>,
//                response: Response<AgentsResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val AgentsResponse = response.body()
//                    AgentsResponse?.data?.forEach { agent ->
//                        Log.d("MANIII", "Agent: ${agent.displayName}")
//                        Log.d("MANIII", "Role: ${agent.role.displayName}")
//                        for (x in agent.abilities){
//                            Log.d("MANIII", "${x.slot} \n${x.displayName} - ${x.description}")}
//                    }
//                    // y así sucesivamente
//                    Log.d("MANIII", AgentsResponse.toString())
//                } else {
//                    Log.e("MANIII", response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<AgentsResponse>, t: Throwable) {
//                Log.e("MainActivity", t.message, t)
//            }
//        })

        //CONTRACTS
        valorantService.getContracts().enqueue(object : Callback<ContractsResponse> {
            override fun onResponse(
                call: Call<ContractsResponse>,
                response: Response<ContractsResponse>
            ) {
                if (response.isSuccessful) {
                    val ContractsResponse = response.body()
                    ContractsResponse?.data?.forEach { contract ->
                        //PROBLEM TO SOLVE: It must break from Yoru until the end
                        Log.d("MANIII", "Agent: ${contract.displayName}")
                    }
                    // y así sucesivamente
                    Log.d("MANIII", ContractsResponse.toString())
                } else {
                    Log.e("MANIII", response.message())
                }
            }

            override fun onFailure(call: Call<ContractsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }
        })
        supportActionBar?.title = "Pantalla Inicial"
//
//        val valorantService = ValorantService.create()
//
//        //WEAPONS
//        valorantService.getWeapons().enqueue(object : Callback<WeaponsResponse> {
//            override fun onResponse(
//                call: Call<WeaponsResponse>,
//                response: Response<WeaponsResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val weaponsResponse = response.body()
//                    weaponsResponse?.data?.forEach { weapon ->
//                        Log.d("MANIII", "Weapon: ${weapon.displayName}")
//                        Log.d("MANIII", "Category: ${weapon.category}")
//                        Log.d("MANIII", "Default Skin UUID: ${weapon.defaultSkinUuid}")
//                    }
//                    // y así sucesivamente
//                    Log.d("MANIII", weaponsResponse.toString())
//                } else {
//                    Log.e("MANIII", response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<WeaponsResponse>, t: Throwable) {
//                Log.e("MainActivity", t.message, t)
//            }
//        })
//
//        //AGENTS
//        valorantService.getAgents().enqueue(object : Callback<AgentsResponse> {
//            override fun onResponse(
//                call: Call<AgentsResponse>,
//                response: Response<AgentsResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val AgentsResponse = response.body()
//                    AgentsResponse?.data?.forEach { agent ->
//                        if (agent.role != null) {
//                            Log.d("MANIII", "Agent: ${agent.displayName}")
//                            Log.d("MANIII", "Role: ${agent.role.displayName}")
//                            for (x in agent.abilities) {
//                                Log.d("MANIII", "${x.slot} \n${x.displayName} - ${x.description}")
//                            }
//                        }
//                    }
//                    // y así sucesivamente
//                    Log.d("MANIII", AgentsResponse.toString())
//                } else {
//                    Log.e("MANIII", response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<AgentsResponse>, t: Throwable) {
//                Log.e("MainActivity", t.message, t)
//            }
//        })

        //MAPS
        valorantService.getMaps().enqueue(object : Callback<MapsResponse> {
            override fun onResponse(
                call: Call<MapsResponse>,
                response: Response<MapsResponse>
            ) {
                if (response.isSuccessful) {
                    val MapsResponse = response.body()
                    MapsResponse?.data?.forEach { map ->
                        Log.d("MANIII", "Map: ${map.displayName}")
                    }
                    Log.d("MANIII", MapsResponse.toString())
                } else {
                    Log.e("MANIII", response.message())
                }
            }

            override fun onFailure(call: Call<MapsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }
        })
    }
}