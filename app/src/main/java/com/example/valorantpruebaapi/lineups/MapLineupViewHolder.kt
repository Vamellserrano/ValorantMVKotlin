package com.example.valorantpruebaapi.lineups

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.databinding.ItemMapLineupBinding


class MapLineupViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemMapLineupBinding.bind(view)

        fun render(mapModel: ClasesApi.Map, onClickListener: (ClasesApi.Map) -> Unit) {
            Glide.with(binding.imageViewMapLineup.context).load(mapModel.listViewIcon)
                .into(binding.imageViewMapLineup)

            binding.textViewMapLineupName.text = mapModel.displayName

            itemView.setOnClickListener {
                onClickListener(mapModel)
            }
        }
    }