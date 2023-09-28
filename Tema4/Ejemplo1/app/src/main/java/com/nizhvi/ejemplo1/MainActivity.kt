package com.nizhvi.ejemplo1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.nizhvi.ejemplo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            var message: String = ""

            if (binding.editText.text.isEmpty() || binding.editTextNumber.text.isEmpty()) {
                message = "Hay algún texto vacío..."
                binding.textView.text = ""
            } else {
                message = "Texto: " + binding.editText.text.toString() + "\nNúmero: " + binding.editTextNumber.text.toString()

                binding.textView.text = message
            }

            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()

            ocultarTeclado(it)
        }
    }
    fun ocultarTeclado(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}