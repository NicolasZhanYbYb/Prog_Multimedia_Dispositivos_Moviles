package com.nzv.mynarrativecontent

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nzv.mynarrativecontent.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startElements()
    }
    //Inicializa todas las funcionalidades de la activity a sus correspondiente funciones
    @SuppressLint("SetTextI18n")
    private fun startElements() {
        val name = intent.getStringExtra(Utils.EXTRA_NAME)
        with (binding){
            etPass.text.clear()
            etEnterUser.text.clear()
            btSingUp.setOnClickListener { actionsBtSingUp(it) }
            btSingIn.setOnClickListener { actionsBtSingIn(it) }
        }
    }

    private fun actionsBtSingUp(it: View) {
        Utils.closeKeyboard(it, this)
        val myIntent = Intent(this, LogInActivity::class.java)
        startActivity(myIntent)
    }

    private fun actionsBtSingIn(it: View) {
        Utils.closeKeyboard(it, this)
        if (!checkCanAccept(it)) {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
    }

    //Comprueba que todos los campos estén informados para poder aceptar
    private fun checkCanAccept(view: View): Boolean {
        if (binding.etPass.text.isEmpty() || binding.etEnterUser.text.isEmpty()) {
            Utils.showSnackBar(getString(R.string.campos_sin_datos), view)
            return false
        }
        return true
    }
}