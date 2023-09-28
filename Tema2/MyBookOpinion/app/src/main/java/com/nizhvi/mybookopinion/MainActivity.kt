package com.nizhvi.mybookopinion

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.nizhvi.mybookopinion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btHecho.setOnClickListener() {
            mostrarOpinion(it)
        }

        binding.txtOpinion.setOnClickListener {
            volverEditOpinion(it)
        }
    }

    private fun mostrarOpinion(view: View) {
        binding.txtOpinion.text = binding.etOpinion.text
        binding.txtOpinion.visibility = View.VISIBLE

        binding.etOpinion.visibility = View.GONE
        view.visibility = View.GONE

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun volverEditOpinion(view: View) {
        binding.etOpinion.visibility = View.VISIBLE
        binding.btHecho.visibility = View.VISIBLE

        view.visibility = View.GONE

        binding.etOpinion.requestFocus()
    }
}