package com.example.valorantpruebaapi.weapons

import ClasesApi.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.agents.ActivityAgents
import com.example.valorantpruebaapi.databinding.ActivityWeaponsBinding
import com.example.valorantpruebaapi.lineups.ActivityLineups
import com.example.valorantpruebaapi.maps.ActivityMaps
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityWeapons : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private var weapons: List<Weapon> = emptyList()
    private lateinit var binding: ActivityWeaponsBinding
    private lateinit var popupWindow: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeaponsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignar la Toolbar
        setSupportActionBar(binding.toolbarweapons)
        supportActionBar?.title = "WEAPONS"
        popupWindow = PopupWindow(layoutInflater.inflate(R.layout.popup_weapons, null), 800,  1000)

        val valorantService = ValorantService.create()

        valorantService.getWeapons().enqueue(object : Callback<WeaponsResponse> {
            override fun onResponse(
                call: Call<WeaponsResponse>, response: Response<WeaponsResponse>
            ) {
                if (response.isSuccessful) {
                    val weaponsResponse = response.body()
                    //Con la funcion MAP lo transformo a una lista. Para evitar errores,
                    //añado el ?: por si devuelve un null que no sea null ya que crashea la app
                    weapons = weaponsResponse?.data?.map { weapon ->
                        Weapon(
                            uuid = weapon.uuid,
                            displayName = weapon.displayName,
                            category = weapon.category,
                            defaultSkinUuid = weapon.defaultSkinUuid,
                            displayIcon = weapon.displayIcon,
                            killStreamIcon = weapon.killStreamIcon,
                            assetPath = weapon.assetPath, //?: ""
                            weaponStats = weapon.weaponStats,
                            shopData = weapon.shopData
                        )
                    } ?: emptyList()
                    //Activamos la vista de nuestro recyclerview
                    initRecyclerView()
                } else {
                    Log.e("MANIII", response.message())
                }
            }

            override fun onFailure(call: Call<WeaponsResponse>, t: Throwable) {
                Log.e("MainActivity", t.message, t)
            }
        })

        //Evento para hacer desaparecer el popupwindow, pero bloquea el deslizar... buscar solucion.
        /*val backgroundView = findViewById<View>(R.id.recyclerViewWeapons)
        backgroundView.setOnTouchListener { _, event ->
            // Cerrar el popup cuando se detecta un toque en la vista detrás de él
            popupWindow.dismiss()
            true
        }*/

        // -----------------------------------------------------------
        // --------------------- NAVIGATION MENU ---------------------
        // -----------------------------------------------------------

        //Asignar la navigationView
        navigationView = findViewById(R.id.nav_view_weapons)
        //Asignar el drawer
        drawerLayout = findViewById(R.id.drawerWeapons)

        // Inicializar ActionBarDrawerToggle y asociarlo al DrawerLayout y la Toolbar
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbarweapons, R.string.opendrawer, R.string.closedrawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.agents_nav -> {
                    val agentsIntent =
                        Intent(this@ActivityWeapons, ActivityAgents::class.java)
                    startActivity(agentsIntent)
                    true
                }
                R.id.maps_nav -> {
                    val mapsIntent =
                        Intent(this@ActivityWeapons, ActivityMaps::class.java)
                    startActivity(mapsIntent)
                    true
                }
                R.id.weapons_nav -> {
                    val weaponsIntent =
                        Intent(this@ActivityWeapons, ActivityWeapons::class.java)
                    startActivity(weaponsIntent)
                    true
                }
                R.id.lineups_nav -> {
                    val lineupsIntent2 =
                        Intent(this@ActivityWeapons, ActivityLineups::class.java)
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

    fun initRecyclerView() {
        //Funcion para establecer el recycle view, tanto el layout como los items
        val manager = GridLayoutManager(this, 2)

        val order = listOf("EEquippableCategory::Sidearm", "EEquippableCategory::SMG",
            "EEquippableCategory::Shotgun", "EEquippableCategory::Rifle", "EEquippableCategory::Sniper",
            "EEquippableCategory::Heavy", "EEquippableCategory::Melee")
        weapons = weapons.sortedWith(compareBy({ order.indexOf(it.category) }, { it.shopData?.cost }))

        //Eliminamos el cuchillo de la ecuacion <3
        weapons = weapons.subList(0,weapons.size-1)

        binding.recyclerViewWeapons.layoutManager = manager
        binding.recyclerViewWeapons.adapter =
            WeaponAdapter(weapons) { weapon -> onItemSelected(weapon) }

        //Decoracion - Separacion de rayas.
        //val decoration = DividerItemDecoration(this, manager.orientation)
        //binding.recyclerViewAgents.addItemDecoration(decoration)
    }

    fun onItemSelected(weapon: Weapon) {

        if(popupWindow.isShowing){
            popupWindow.dismiss()
        }else{
            val popupView = layoutInflater.inflate(R.layout.popup_weapons, null)
            popupView.findViewById<TextView>(R.id.TextViewWeaponName).text = weapon.displayName
            var weaponCategory = weapon.category.split("::")
            popupView.findViewById<TextView>(R.id.textViewCategory).text = weaponCategory[1]
            popupView.findViewById<TextView>(R.id.textViewCost).append(weapon.shopData?.cost.toString())
            var weaponPenetration = weapon.weaponStats?.wallPenetration?.split("::")
            popupView.findViewById<TextView>(R.id.textViewPenetration).append(weaponPenetration?.get(1))
            val damageRange = weapon.weaponStats?.damageRanges
            var ranges = ""
            var head = ""
            var body = ""
            var legs = ""
            for (range in damageRange!!){
                ranges += range.rangeStartMeters.toInt().toString() + " - " + range.rangeEndMeters.toInt().toString() +"m".padEnd(8, ' ')
                head += range.headDamage.toInt().toString().padEnd(18, ' ')
                body += range.bodyDamage.toInt().toString().padEnd(18, ' ')
                legs += range.legDamage.toInt().toString().padEnd(18, ' ')
            }
            popupView.findViewById<TextView>(R.id.textViewRanges).text = ranges
            popupView.findViewById<TextView>(R.id.textViewDamageHead).text = head
            popupView.findViewById<TextView>(R.id.textViewDamageBody).text = body
            popupView.findViewById<TextView>(R.id.textViewDamageLegs).text = legs


            popupWindow = PopupWindow(popupView, 800,  600)

            //INTENTAR REDONDEAR EL POPUPWINDOW
            //popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(popupView.context, R.drawable.box_50_alpha))

            //val popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        }
    }
}