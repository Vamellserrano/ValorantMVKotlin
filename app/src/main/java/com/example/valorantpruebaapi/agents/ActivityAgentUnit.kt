package com.example.valorantpruebaapi.agents

import ClasesApi.Agent
import android.content.Intent
import android.graphics.text.LineBreaker
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.LoginActivity
import com.example.valorantpruebaapi.MainScreen
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.databinding.ActivityAgentUnitBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.maps.ActivityMaps
import com.example.valorantpruebaapi.weapons.ActivityWeapons
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ActivityAgentUnit : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

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
            binding.ImageViewAbility1.setBackgroundResource(R.drawable.box_recyclerunit_selected)

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
                binding.ImageViewAbility1.setBackgroundResource(R.drawable.box_recyclerunit_selected)
                binding.ImageViewAbility2.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility3.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility4.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.imageViewPasive.setBackgroundResource(R.drawable.box_recyclerunit)
            }
            binding.ImageViewAbility2.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[1].description
                binding.ImageViewAbility1.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility2.setBackgroundResource(R.drawable.box_recyclerunit_selected)
                binding.ImageViewAbility3.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility4.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.imageViewPasive.setBackgroundResource(R.drawable.box_recyclerunit)
            }
            binding.ImageViewAbility3.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[2].description
                binding.ImageViewAbility1.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility2.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility3.setBackgroundResource(R.drawable.box_recyclerunit_selected)
                binding.ImageViewAbility4.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.imageViewPasive.setBackgroundResource(R.drawable.box_recyclerunit)
            }
            binding.ImageViewAbility4.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[3].description
                binding.ImageViewAbility1.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility2.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility3.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility4.setBackgroundResource(R.drawable.box_recyclerunit_selected)
                binding.imageViewPasive.setBackgroundResource(R.drawable.box_recyclerunit)
            }
            binding.imageViewPasive.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[4].description
                binding.textViewAbilitiesDescription.text = agent.abilities[3].description
                binding.ImageViewAbility1.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility2.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility3.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.ImageViewAbility4.setBackgroundResource(R.drawable.box_recyclerunit)
                binding.imageViewPasive.setBackgroundResource(R.drawable.box_recyclerunit_selected)
            }

        }

        // -----------------------------------------------------------
        // --------------------- NAVIGATION MENU ---------------------
        // -----------------------------------------------------------

        //Asignar la navigationView
        navigationView = findViewById(R.id.nav_view_agent)
        //Asignar el drawer
        drawerLayout = findViewById(R.id.draweragent)
        //Asignar el user
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userEmail = user.email
            binding.userWelcome.text = "Bienvenido/a \n$userEmail"
        } else {
            binding.userWelcome.isVisible = false
        }

        // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbaragent, R.string.opendrawer, R.string.closedrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nav -> {
                    val home =
                        Intent(this@ActivityAgentUnit, MainScreen::class.java)
                    startActivity(home)
                    true
                }
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

        if (user != null) {
            binding.btnLogoutMs.setOnClickListener {
                mAuth = FirebaseAuth.getInstance()
                mAuth.signOut()
                Toast.makeText(this, "You've logged out.", Toast.LENGTH_SHORT).show()
                val logout =
                    Intent(this@ActivityAgentUnit, LoginActivity::class.java)
                startActivity(logout)
            }
        } else {
            binding.btnLogoutMs.isVisible = false
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
