package com.example.valorantpruebaapi.mapadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantpruebaapi.R


class MapAdapter(private val map: List<ClasesApi.Map>, private val onClickListener: (ClasesApi.Map) -> Unit) :
    RecyclerView.Adapter<MapViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_map, parent, false)
        return MapViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        val item = map[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return map.size
    }
}