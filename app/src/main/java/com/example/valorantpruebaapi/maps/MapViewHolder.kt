package com.example.valorantpruebaapi.maps

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.databinding.ItemMapBinding


class MapViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemMapBinding.bind(view)

        fun render(mapModel: ClasesApi.Map, onClickListener: (ClasesApi.Map) -> Unit) {
            Glide.with(binding.imageViewMap.context).load(mapModel.listViewIcon)
                .into(binding.imageViewMap)

            binding.textViewMapName.text = mapModel.displayName

            itemView.setOnClickListener {
                onClickListener(mapModel)
            }
        }
    }