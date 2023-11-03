package com.nizhvi.practica2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nizhvi.practica2.MainActivity.Companion.toInt
import com.nizhvi.practica2.databinding.ActivitySecondBinding
//Nicolas Zhan
class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startElements()
    }
    //Inicializa todas las funcionalidades de la activity a sus correspondiente funciones
    @SuppressLint("SetTextI18n")
    private fun startElements() {
        binding.rbASIR.isChecked = true
        binding.rbPresencial.isChecked = true

        val name = intent.getStringExtra(MainActivity.EXTRA_NAME)

        binding.txtIntrDatosNombre.text = "Introduzca los datos del alumno:\n$name"

        binding.btAceptar.setOnClickListener { actionsBtAccept() }
        binding.btCancelar.setOnClickListener { actionsBtCancel() }
    }

    private fun actionsBtCancel() {
        setResult(Activity.RESULT_CANCELED)
        Log.d(MainActivity.TAG_APP, "Se devuelve cancelar")
        finish()
    }

    private fun actionsBtAccept() {
        if (!checkCanAccept())
            return

        val intentResult: Intent = Intent().apply {
            putAllExtras(this)
        }

        Log.d(MainActivity.TAG_APP, "Se devuelve Aceptar")
        setResult(Activity.RESULT_OK, intentResult)
        finish()

    }

    private fun putAllExtras(intent: Intent) {
        intent.putExtra(MainActivity.EXTRA_DAY, binding.etDia.text.toString().toInt())
        intent.putExtra(MainActivity.EXTRA_MONTH, binding.etMes.text.toString().toInt())
        intent.putExtra(MainActivity.EXTRA_YEAR, binding.etAnyo.text.toString().toInt())
        if (binding.rbPresencial.isChecked)
            intent.putExtra(MainActivity.EXTRA_MODALITY, Modality.PRESENCIAL.toInt())
        else
            intent.putExtra(MainActivity.EXTRA_MODALITY, Modality.SEMIPRESENCIAL.toInt())

        if (binding.rbASIR.isChecked)
            intent.putExtra(MainActivity.EXTRA_CYCLE, Cycle.ASIR.toInt())
        else if (binding.rbDAM.isChecked)
            intent.putExtra(MainActivity.EXTRA_CYCLE, Cycle.DAM.toInt())
        else
            intent.putExtra(MainActivity.EXTRA_CYCLE, Cycle.DAW.toInt())
    }
    //Comprueba que todos los campos estén informados para poder aceptar
    private fun checkCanAccept(): Boolean {
        if (!checkDataEntered()) {
            Toast.makeText(applicationContext, "Hay campos sin datos introducidos", Toast.LENGTH_LONG).show()
            return false
        }

        val day = binding.etDia.text.toString().toInt()
        val month = binding.etMes.text.toString().toInt()
        val year = binding.etAnyo.text.toString().toInt()

        val date = Date(day, month, year)

        if (!date.validateDate()) {
            Toast.makeText(applicationContext, "La fecha no es válida", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
    //Comprueba que la fecha introducida se valida
    private fun checkDataEntered(): Boolean {
        if (binding.etDia.text.toString().isEmpty() || binding.etMes.text.toString().isEmpty() || binding.etAnyo.text.toString().isEmpty())
            return false
        return true
    }
}