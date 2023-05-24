package com.example.valorantpruebaapi.agents

import ClasesApi.Agent
import android.graphics.text.LineBreaker
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import androidx.core.view.isInvisible
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.R
import com.example.valorantpruebaapi.databinding.ActivityAgentUnitBinding

class ActivityAgentUnit : AppCompatActivity() {
    private lateinit var binding: ActivityAgentUnitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAgentUnitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val agent = intent.getParcelableExtra<Agent>("AGENT")

        if (agent != null) {
            //Establecemos todos los atributos de la clase agente necesarios
            supportActionBar?.title = "AGENT " + agent.displayName

            Glide.with(binding.imageViewAgentPortrait.context).load(agent.fullPortrait)
                .into(binding.imageViewAgentPortrait)

            //Para justificar los textos y que se pongan guiones en el momento adecuado
            binding.textViewAbilitiesDescription.justificationMode =
                LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            binding.textViewAbilitiesDescription.breakStrategy =
                LineBreaker.BREAK_STRATEGY_HIGH_QUALITY
            binding.textViewAbilitiesDescription.hyphenationFrequency =
                Layout.HYPHENATION_FREQUENCY_FULL

            binding.textViewDescriptionAgent.justificationMode =
                LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            binding.textViewDescriptionAgent.breakStrategy = LineBreaker.BREAK_STRATEGY_HIGH_QUALITY
            binding.textViewDescriptionAgent.hyphenationFrequency =
                Layout.HYPHENATION_FREQUENCY_FULL

            //Establecemos los primeros textos e imagenes
            binding.textViewNameAgent.text = agent.displayName
            binding.textViewRolAgent.text = agent.role.displayName
            binding.textViewAbilitiesDescription.text = agent.abilities.get(0)?.description
            binding.textViewDescriptionAgent.text = agent.description

            Glide.with(binding.ImageViewAbility1.context)
                .load(agent.abilities[0].displayIcon)
                .into(binding.ImageViewAbility1)
            Glide.with(binding.ImageViewAbility2.context)
                .load(agent.abilities[1].displayIcon)
                .into(binding.ImageViewAbility2)
            Glide.with(binding.ImageViewAbility3.context)
                .load(agent.abilities[2].displayIcon)
                .into(binding.ImageViewAbility3)
            Glide.with(binding.ImageViewAbility4.context)
                .load(agent.abilities[3].displayIcon)
                .into(binding.ImageViewAbility4)
            if (agent.abilities.size <= 4) {
                binding.imageViewPasive.isInvisible = true
                binding.textViewPasive.isInvisible = true
            } else {
                if (agent.abilities.size == 5 && agent.abilities[4].displayIcon == "noPasive") {
                    binding.imageViewPasive.setImageResource(R.drawable.pasive_white)
                } else {
                    Glide.with(binding.imageViewPasive.context)
                        .load(agent.abilities[4].displayIcon)
                        .into(binding.imageViewPasive)
                }
            }

            binding.ImageViewAbility1.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[0].description
            }
            binding.ImageViewAbility2.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[1].description
            }
            binding.ImageViewAbility3.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[2].description
            }
            binding.ImageViewAbility4.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[3].description
            }
            binding.imageViewPasive.setOnClickListener {
                binding.textViewAbilitiesDescription.text = agent.abilities[4].description
            }

        }
    }
}