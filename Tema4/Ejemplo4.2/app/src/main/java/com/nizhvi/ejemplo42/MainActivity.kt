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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}