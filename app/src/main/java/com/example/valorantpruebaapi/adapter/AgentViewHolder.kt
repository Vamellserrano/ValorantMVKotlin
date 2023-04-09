package com.example.valorantpruebaapi.adapter

import ClasesApi.Agent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.databinding.ItemAgentBinding

class AgentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemAgentBinding.bind(view)

    fun render(agentModel: Agent, onClickListener: (Agent) -> Unit) {
        //Esta funcion sirve para establecer los datos que queremos en la vista de los items
        //Utilizamos libreria externa para bindear un http a imageView
        Glide.with(binding.imageViewAgent.context).load(agentModel.displayIcon)
            .into(binding.imageViewAgent)

        itemView.setOnClickListener {
            onClickListener(agentModel)
        }
    }
}