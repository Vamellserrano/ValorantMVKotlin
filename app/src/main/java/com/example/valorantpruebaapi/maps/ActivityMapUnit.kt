package com.example.valorantpruebaapi.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.valorantpruebaapi.databinding.ActivityMapUnitBinding

class ActivityMapUnit : AppCompatActivity() {

    private lateinit var binding: ActivityMapUnitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapUnitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val map = intent.getParcelableExtra<ClasesApi.Map>("MAP")

        if (map != null) {

            supportActionBar?.title = "MAP" + map.displayName

            Glide.with(binding.imageViewMapPreview.context).load(map.listViewIcon)
                .into((binding.imageViewMapPreview))

            Glide.with(binding.imageViewMapDisposition.context).load(map.displayIcon)
                .into((binding.imageViewMapDisposition))

            binding.textViewMapName.text = map.displayName

        }
    }
}