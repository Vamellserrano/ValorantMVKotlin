package com.example.valorantpruebaapi.adapter

import ClasesApi.Weapon
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.databinding.ItemWeaponBinding

class WeaponViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemWeaponBinding.bind(view)

    fun render(weaponModel: Weapon, onClickListener: (Weapon) -> Unit) {
        //Esta funcion sirve para establecer los datos que queremos en la vista de los items
        //Utilizamos libreria externa para bindear un http a imageView
        Glide.with(binding.imageViewWeapon.context).load(weaponModel.displayIcon)
            .into(binding.imageViewWeapon)
        var categoriaArmas = weaponModel.category.split("::")
        binding.textViewName.text = weaponModel.displayName + "      " +
                categoriaArmas[1] + " " + weaponModel.shopData?.cost.toString()

        itemView.setOnClickListener {
            onClickListener(weaponModel)
        }
    }
}