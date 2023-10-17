package com.nizhvi.ejemplo42

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nizhvi.ejemplo42.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Estas dos líneas sustituyen a
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { actionsButtons("button") }
        binding.imageButton.setOnClickListener {
            actionsButtons("imageButton") }
        binding.fabButton.setOnClickListener { fabActionButton() }

    }
    private fun actionsButtons(name: String) {
        when (name) {
            "button" -> {
                binding.checkBox.isChecked = true
                binding.radioButton1.isChecked = true
                binding.radioButton2.isChecked = false
                binding.toggleButton.isChecked = true
                binding.switch1.isChecked = true
            }
            else -> {
                binding.checkBox.isChecked = false
                binding.radioButton1.isChecked = false
                binding.radioButton2.isChecked = true
                binding.toggleButton.isChecked = false
                binding.switch1.isChecked = false
            }
        }
    }
    private fun fabActionButton() {
        var texto: String
        texto = "Pulsados: \n";
        if(binding.checkBox.isChecked)
            texto += "CheckBox "

        if(binding.radioButton1.isChecked)
            texto += "Radio 1 "
        else
            texto += "Radio 2 "

        if(binding.toggleButton.isChecked)
            texto += "Toggle Button "
        if(binding.switch1.isChecked)
            texto += "Switch"
        Toast.makeText(applicationContext, texto, Toast.LENGTH_LONG).show()
    }
}