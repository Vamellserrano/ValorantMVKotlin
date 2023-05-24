package com.example.valorantpruebaapi

import ClasesApi.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.valorantpruebaapi.adapter.WeaponAdapter
import com.example.valorantpruebaapi.databinding.ActivityWeaponsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityWeapons : AppCompatActivity() {

    private var weapons: List<Weapon> = emptyList()
    private lateinit var binding: ActivityWeaponsBinding
    private lateinit var popupWindow: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeaponsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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