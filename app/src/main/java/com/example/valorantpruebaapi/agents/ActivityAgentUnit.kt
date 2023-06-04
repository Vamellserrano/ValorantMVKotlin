package com.example.valorantpruebaapi.agents

import ClasesApi.Agent
import android.content.Intent
import android.graphics.text.LineBreaker
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isInvisible
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.databinding.ActivityAgentUnitBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.maps.ActivityMaps
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.material.navigation.NavigationView

class ActivityAgentUnit : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var binding: ActivityAgentUnitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgentUnitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val agent = intent.getParcelableExtra<Agent>("AGENT")

        if (agent != null) {
            // Asignar la Toolbar
            setSupportActionBar(binding.toolbaragent)
            supportActionBar?.title = "AGENT " + agent.displayName

            //Establecemos todos los atributos de la clase agente necesarios
            Glide.with(binding.imageViewAgentPortrait.context).load(agent.fullPortrait)
                .into(binding.imageViewAgentPortrait)
            Glide.with(binding.imageViewAgentPortrait2.context).load(agent.fullPortrait)
                .into(binding.imageViewAgentPortrait2)

            //Para justificar los textos y que se pongan guiones en el momento adecuado
            binding.textViewAbilitiesDescription.justificationMode =
                LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            binding.textViewAbilitiesDescription.breakStrategy =
                LineBreaker.BREAK_STRATEGY_HIGH_QUALITY
            binding.textViewAbilitiesDescription.hyphenationFrequency =
                Layout.HYPHENATION_FREQUENCY_FULL

            binding.textViewDescriptionAgent.justificationMode =
                LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            binding.textViewDescriptionAgent.breakStrategy = LineBreaker.BREAK_STRATEGY_HIGH_QUALITY
            binding.textViewDescriptionAgent.hyphenationFrequency =
                Layout.HYPHENATION_FREQUENCY_FULL

            //Establecemos los primeros textos e imagenes
            binding.textViewNameAgent.text = agent.displayName
            binding.textViewRolAgent.text = agent.role.displayName
            binding.textViewAbilitiesDescription.text = agent.abilities.get(0)?.description
            binding.textViewDescriptionAgent.text = agent.description

            Glide.with(binding.ImageViewAbility1.context)
                .load(agent.abilities[0].displayIcon)
                .into(binding.ImageViewAbility1)
            Glide.with(binding.ImageViewAbility2.context)
                .load(agent.abilities[1].displayIcon)
                .into(binding.ImageViewAbility2)
            Glide.with(binding.ImageViewAbility3.context)
                .load(agent.abilities[2].displayIcon)
                .into(binding.ImageViewAbility3)
            Glide.with(binding.ImageViewAbility4.context)
                .load(agent.abilities[3].displayIcon)
                .into(binding.ImageViewAbility4)
            if (agent.abilities.size <= 4) {
                binding.imageViewPasive.isInvisible = true
                binding.textViewPasive.isInvisible = true
            } else {
                if (agent.abilities.size == 5 && agent.abilities[4].displayIcon == "noPasive") {
                    binding.imageViewPasive.setImageResource(R.drawable.pasive_white)
                } else {
                    Glide.with(binding.imageViewPasive.context)
                        .load(agent.abilities[4].displayIcon)
                        .into(binding.imageViewPasive)
                }
            }

            binding.ImageViewAbility1.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[0].description
            }
            binding.ImageViewAbility2.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[1].description
            }
            binding.ImageViewAbility3.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[2].description
            }
            binding.ImageViewAbility4.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[3].description
            }
            binding.imageViewPasive.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[4].description
            }

        }

        // -----------------------------------------------------------
        // --------------------- NAVIGATION MENU ---------------------
        // -----------------------------------------------------------

        //Asignar la navigationView
        navigationView = findViewById(R.id.nav_view_agent)
        //Asignar el drawer
        drawerLayout = findViewById(R.id.draweragent)

        // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbaragent, R.string.opendrawer, R.string.closedrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.agents_nav -> {
                    val agentsIntent =
                        Intent(this@ActivityAgentUnit, ActivityAgents::class.java)
                    startActivity(agentsIntent)
                    true
                }
                R.id.maps_nav -> {
                    val mapsIntent =
                        Intent(this@ActivityAgentUnit, ActivityMaps::class.java)
                    startActivity(mapsIntent)
                    true
                }
                R.id.weapons_nav -> {
                    val weaponsIntent =
                        Intent(this@ActivityAgentUnit, ActivityWeapons::class.java)
                    startActivity(weaponsIntent)
                    true
                }
                R.id.lineups_nav -> {
                    val lineupsIntent2 =
                        Intent(this@ActivityAgentUnit, ActivityLineups::class.java)
                    startActivity(lineupsIntent2)
                    true
                }
                else -> false
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
