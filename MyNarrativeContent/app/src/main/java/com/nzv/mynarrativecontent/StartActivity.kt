package com.nzv.mynarrativecontent

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nzv.mynarrativecontent.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //db = FirebaseFirestore.getInstance()
        startElements()
    }
    //Inicializa todas las funcionalidades de la activity a sus correspondiente funciones
    @SuppressLint("SetTextI18n")
    private fun startElements() {
        with (binding){
            btSingUp.setOnClickListener { actionsBtSingUp(it) }
            btSingIn.setOnClickListener { actionsBtSingIn(it) }
        }
    }

    private fun actionsBtSingUp(it: View) {
        Utils.closeKeyboard(it, this)
        val myIntent = Intent(this, SingUpActivity::class.java)
        startActivity(myIntent)
    }

    private fun actionsBtSingIn(it: View) {
        Utils.closeKeyboard(it, this)

        val user = binding.etEnterUser.text.toString()
        val pass = binding.etPass.text.toString()
        val myIntent: Intent
        if (user == "nicolas" && pass == "12345678") {
            myIntent = Intent(this, MainActivity::class.java).apply {
                putExtra(Utils.EXTRA_USER, user)
            }
            startActivity(myIntent)
            binding.etPass.text.clear()
            binding.etEnterUser.text.clear()
        }
        Utils.showSnackBar("El usuario y la contraseña no coinciden", it)

    }

    //Comprueba que todos los campos estén informados para poder aceptar
    private fun checkCanAccept(view: View): Boolean {
        if (binding.etPass.text.isEmpty() || binding.etEnterUser.text.isEmpty()) {
            Utils.showSnackBar(getString(R.string.campos_sin_datos), view)
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