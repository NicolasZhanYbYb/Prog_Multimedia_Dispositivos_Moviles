package com.nizhvi.mycolorsmyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nizhvi.mycolorsmyviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private fun colorearElemento(view: View) {
        when (view.id) {
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)
            R.id.box_three_text -> view.setBackgroundColor(Color.BLUE)
            R.id.box_four_text -> view.setBackgroundColor(Color.MAGENTA)
            R.id.box_five_text -> view.setBackgroundColor(Color.BLUE)

            R.id.red_button ->
                binding.boxThreeText.setBackgroundColor(getColor(R.color.my_red))
            R.id.yellow_button ->
                binding.boxFourText.setBackgroundColor(getColor(R.color.my_yellow))
            R.id.green_button ->
                binding.boxFiveText.setBackgroundColor(getColor(R.color.my_green))

            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun setListeners() {
        binding.boxOneText.setOnClickListener { colorearElemento(it) }
        binding.boxTwoText.setOnClickListener { colorearElemento(it) }
        binding.boxThreeText.setOnClickListener { colorearElemento(it) }
        binding.boxFourText.setOnClickListener { colorearElemento(it) }
        binding.boxFiveText.setOnClickListener { colorearElemento(it) }

        binding.root.setOnClickListener { colorearElemento(it) }

        binding.redButton.setOnClickListener { colorearElemento(it) }
        binding.yellowButton.setOnClickListener { colorearElemento(it) }
        binding.greenButton.setOnClickListener { colorearElemento(it) }
    }

}