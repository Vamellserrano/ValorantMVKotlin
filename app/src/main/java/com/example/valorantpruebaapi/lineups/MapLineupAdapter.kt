package com.example.valorantpruebaapi.lineups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantpruebaapi.R

class MapLineupAdapter(private val map: List<ClasesApi.Map>, private val onClickListener: (ClasesApi.Map) -> Unit) :
    RecyclerView.Adapter<MapLineupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapLineupViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_map_lineup, parent, false)
        return MapLineupViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MapLineupViewHolder, position: Int) {
        val item = map[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return map.size
    }
}