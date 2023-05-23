package com.example.valorantpruebaapi

import ClasesApi.*
import ClasesApi.Map
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorantpruebaapi.databinding.ActivityMapsBinding
import com.example.valorantpruebaapi.mapadapter.MapAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityMaps : AppCompatActivity() {

    private var maps: List<Map> = emptyList()
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "MAPAS"

        val valorantService = ValorantService.create()

        valorantService.getMaps().enqueue(object : Callback<MapsResponse> {
            override fun onResponse(
                call: Call<MapsResponse>, response: Response <MapsResponse>
            ) {
                if (response.isSuccessful) {
                    val mapsResponse = response.body()
                    maps = mapsResponse?.data?.map {map ->
                        Map (
                            uuid = map.uuid,
                            displayName = map.displayName,
                            coordinates = map.coordinates,
                            displayIcon = map.displayIcon ?: "",
                            listViewIcon = map.listViewIcon,
                            splash = map.splash,
                            assetPath = map.assetPath,
                            mapUrl = map.mapUrl,
                            xMultiplier = map.xMultiplier,
                            yMultiplier = map.yMultiplier,
                            xScalarToAdd = map.xScalarToAdd,
                            yScalarToAdd = map.yScalarToAdd
                        )
                    } ?: emptyList()
                    initRecyclerView()
                } else {
                    Log.e("MANIII", response.message())
                }
            }
            override fun onFailure(call: Call<MapsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }

        })
    }

    fun initRecyclerView() {
        val manager = GridLayoutManager(this, 1)

        binding.recyclerViewMaps.layoutManager = manager
            binding.recyclerViewMaps.adapter =
                MapAdapter(maps) { map -> onItemSelected(map) }
    }

    fun onItemSelected(map: Map) {
        val intent = Intent(this, ActivityMapUnit::class.java)
        intent.putExtra("MAP", map)
        startActivity(intent)

    }
}