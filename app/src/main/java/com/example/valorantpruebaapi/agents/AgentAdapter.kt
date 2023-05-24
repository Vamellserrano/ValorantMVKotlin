package com.example.valorantpruebaapi.agents

import ClasesApi.Agent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantpruebaapi.R

class AgentAdapter(private val agent: List<Agent>, private val onClickListener: (Agent) -> Unit) :
    RecyclerView.Adapter<AgentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        //Le indicamos que formato debe coger para el item y que le de esa vista.
        val layoutInflater = LayoutInflater.from(parent.context)
        return AgentViewHolder(layoutInflater.inflate((R.layout.item_agent), parent, false))
    }

    override fun getItemCount(): Int {
        //Contamos el número de items que vamos a tener en el recycler view
        return agent.size
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        //Basicamente nos indica en que posicion va cada item y el contenido del mismo
        val item = agent[position]
        holder.render(item, onClickListener)
    }
}