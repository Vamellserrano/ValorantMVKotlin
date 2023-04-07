package com.example.valorantpruebaapi

import ClasesApi.Agent
import ClasesApi.AgentsResponse
import ClasesApi.Role
import ClasesApi.ValorantService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantpruebaapi.adapter.AgentAdapter
import com.example.valorantpruebaapi.databinding.ActivityAgentsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityAgents : AppCompatActivity() {
    private var agents: List<Agent> = emptyList()
    private lateinit var binding: ActivityAgentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgentsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title = "AGENTES"

        val valorantService = ValorantService.create()

        valorantService.getAgents().enqueue(object : Callback<AgentsResponse> {
            override fun onResponse(
                call: Call<AgentsResponse>, response: Response<AgentsResponse>
            ) {
                if (response.isSuccessful) {
                    val agentsResponse = response.body()
                    agents = agentsResponse?.data?.map { agent ->
                        Agent(
                            uuid = agent.uuid,
                            displayName = agent.displayName,
                            description = agent.description,
                            developerName = agent.developerName,
                            characterTags = agent.characterTags ?: emptyList(),
                            displayIcon = agent.displayIcon,
                            displayIconSmall = agent.displayIconSmall,
                            bustPortrait = agent.bustPortrait ?: "",
                            fullPortrait = agent.fullPortrait ?: "",
                            fullPortraitV2 = agent.fullPortraitV2 ?: "",
                            killfeedPortrait = agent.killfeedPortrait,
                            background = agent.background ?: "",
                            backgroundGradientColors = agent.backgroundGradientColors,
                            assetPath = agent.assetPath,
                            isFullPortraitRightFacing = agent.isFullPortraitRightFacing,
                            isPlayableCharacter = agent.isPlayableCharacter,
                            isAvailableForTest = agent.isAvailableForTest,
                            isBaseContent = agent.isBaseContent,
                            role = agent.role ?: Role("", "", "", "", ""),
                            abilities = agent.abilities
                        )
                    } ?: emptyList()
                    for (agent in agents) {
                        Log.d("MANIII", agent.toString())
                    }
                    initRecyclerView()
                } else {
                    Log.e("MANIII", response.message())
                }
            }

            override fun onFailure(call: Call<AgentsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }
        })

    }

    fun initRecyclerView() {
        //Funcion para establecer el recycle view, tanto el layout como los items
        val manager = GridLayoutManager(this, 3)
        val decoration = DividerItemDecoration(this, manager.orientation)

        binding.recyclerViewAgents.layoutManager = manager
        binding.recyclerViewAgents.adapter =
            AgentAdapter(agents, { agent -> onItemSelected(agent) })

        binding.recyclerViewAgents.addItemDecoration(decoration)
    }

    fun onItemSelected(agent: Agent) {
        Toast.makeText(this, agent.displayName, Toast.LENGTH_SHORT).show()
    }
}