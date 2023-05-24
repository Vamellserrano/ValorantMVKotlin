package com.example.valorantpruebaapi.adapter

import ClasesApi.Weapon
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantpruebaapi.R

class WeaponAdapter(private val weapon: List<Weapon>, private val onClickListener: (Weapon) -> Unit) :
    RecyclerView.Adapter<WeaponViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponViewHolder {
        //Le indicamos que formato debe coger para el item y que le de esa vista.
        val layoutInflater = LayoutInflater.from(parent.context)
        return WeaponViewHolder(layoutInflater.inflate((R.layout.item_weapon), parent, false))
    }

    override fun getItemCount(): Int {
        //Contamos el número de items que vamos a tener en el recycler view
        return weapon.size
    }

    override fun onBindViewHolder(holder: WeaponViewHolder, position: Int) {
        //Basicamente nos indica en que posicion va cada item y el contenido del mismo
        val item = weapon[position]
        holder.render(item, onClickListener)
    }
}