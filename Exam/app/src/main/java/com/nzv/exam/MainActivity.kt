package com.nzv.exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.nzv.exam.Utils.showSnackBar
import com.nzv.exam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.sartButton.setOnClickListener {actionsBtStart(it) }
        setContentView(binding.root)
    }

    private fun actionsBtStart(it: View) {
        Utils.closeKeyboard(it, this)
        if (!checkEntered(it)) {
            return
        }
        val myIntent = Intent(this, SecondActivity::class.java)
        startActivity(myIntent)
    }

    private  fun checkEntered(it: View): Boolean {
        val user = binding.etEnterUser.text.toString()
        val pass = binding.etPass.text.toString()
        if (user.isEmpty() || pass.isEmpty()) {
            showSnackBar("Tienes algún campo vacio", it)
            return false
        }
        if (user != "David" || pass != "1234") {
            showSnackBar("El usuario o la contraseña no coindciden", it)
            return false
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.main_menu, menu)
        return true
    }
}