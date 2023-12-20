package com.nzv.exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nzv.exam.databinding.ActivityDatosRecycleBinding
import com.nzv.exam.databinding.ActivitySecondBinding

class DatosRecycle : AppCompatActivity() {
    private lateinit var binding: ActivityDatosRecycleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatosRecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = intent.getStringExtra(Utils.EXTRA_APP)
        val tema = intent.getStringExtra(Utils.EXTRA_TEMA)
        val horas = intent.getStringExtra(Utils.EXTRA_HORAS)
        val modalidad = intent.getStringExtra(Utils.EXTRA_MODALITY)

        binding.tvApp2.text = app
        binding.tvTema2.text = tema
        binding.tvHoras2.text = "$horas horas"
        binding.tvModalidad.text = modalidad
    }
}