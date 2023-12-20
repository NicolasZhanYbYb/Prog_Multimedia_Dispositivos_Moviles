package com.nzv.exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import com.nzv.exam.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var appDBHelper: MyAppDBOpenHelper
    private var App = ""
    private var Tema = ""
    private var Horas = ""
    private var Modalidad = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        appDBHelper = MyAppDBOpenHelper(this)
        binding.enterButton.setOnClickListener {actionsBtEnter(it) }
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.second_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var modality = when {
            binding.rdUnity.isChecked -> "Unity"
            else -> "Android"
        }
        return when (item.itemId) {
            R.id.addItem -> {
                val app = binding.etAplicacion.text.toString()
                val horas = binding.etHoras.text.toString()
                val tema = binding.etTema.text.toString()
                appDBHelper.addApp(app, tema, horas, modality)

                true
            }
            R.id.historyItem -> {
                val myIntent = Intent(this, ThirdActivity::class.java).apply {
                    putExtra(Utils.EXTRA_MODALITY, modality)
                }
                startActivity(myIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun actionsBtEnter(it: View) {
        Utils.closeKeyboard(it, this)

        val app = binding.etAplicacion.text.toString()
        val horas = binding.etHoras.text.toString()
        val tema = binding.etTema.text.toString()

        var modality = when {
            binding.rdUnity.isChecked -> "Unity"
            else -> "Android"
        }
        if (app.isEmpty() || horas.isEmpty() || tema.isEmpty()) {
            Utils.showSnackBar("Tienes algún campo vacio", it)
            return
        }

        binding.etData.text = "Aplicacion: $app \n$tema\nHoras Implementación$horas"
        appDBHelper.addApp(app, tema, horas, modality)
    }

}