package com.nizhvi.practica3

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.nizhvi.practica3.Utils.toInt
import com.nizhvi.practica3.Utils.closeKeyboard
import com.nizhvi.practica3.databinding.ActivitySecondBinding
import java.util.Calendar
//Nicolas Zhan
class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private var selectDate: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startElements()
    }
    //Inicializa todas las funcionalidades de la activity a sus correspondiente funciones
    @SuppressLint("SetTextI18n")
    private fun startElements() {
        val name = intent.getStringExtra(Utils.EXTRA_NAME)
        with (binding){
            rbASIR.isChecked = true
            rbPresencial.isChecked = true
            txtIntrDatosNombre.text = "Introduzca los datos del alumno:\n$name"
            btAceptar.setOnClickListener { actionsBtAccept(it) }
            btCancelar.setOnClickListener { actionsBtCancel(it) }
            tvDateBirth.setOnClickListener { myDatePicker(it) }
        }
    }

    private fun actionsBtCancel(view: View) {
        setResult(Activity.RESULT_CANCELED)
        Log.d(Utils.TAG_APP, "Se devuelve cancelar")
        finish()
    }

    private fun actionsBtAccept(view: View) {
        if (!checkCanAccept(view))
            return

        val intentResult: Intent = Intent().apply {
            putAllExtras(this)
        }

        Log.d(Utils.TAG_APP, "Se devuelve Aceptar")
        setResult(Activity.RESULT_OK, intentResult)
        finish()

    }

    private fun myDatePicker(view: View) {
        val cal = selectDate ?: Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->

            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            binding.tvDateBirth.text = "${cal.get(Calendar.DAY_OF_MONTH)} " +
                    "/ ${cal.get(Calendar.MONTH) + 1} / ${cal.get(Calendar.YEAR)}"
            selectDate = cal
        }

        val datePickerDialog = DatePickerDialog(
            this,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun putAllExtras(intent: Intent) {
        intent.putExtra(Utils.EXTRA_DAY, selectDate?.get(Calendar.DAY_OF_MONTH))
        intent.putExtra(Utils.EXTRA_MONTH, selectDate?.get(Calendar.MONTH))
        intent.putExtra(Utils.EXTRA_YEAR, selectDate?.get(Calendar.YEAR))
        if (binding.rbPresencial.isChecked)
            intent.putExtra(Utils.EXTRA_MODALITY, Modality.PRESENCIAL.toInt())
        else
            intent.putExtra(Utils.EXTRA_MODALITY, Modality.SEMIPRESENCIAL.toInt())

        if (binding.rbASIR.isChecked)
            intent.putExtra(Utils.EXTRA_CYCLE, Cycle.ASIR.toInt())
        else if (binding.rbDAM.isChecked)
            intent.putExtra(Utils.EXTRA_CYCLE, Cycle.DAM.toInt())
        else
            intent.putExtra(Utils.EXTRA_CYCLE, Cycle.DAW.toInt())
    }
    //Comprueba que todos los campos estén informados para poder aceptar
    private fun checkCanAccept(view: View): Boolean {
        if (selectDate == null) {
            Utils.showSnackBar(getString(R.string.campos_sin_datos), view)
            return false
        }

        return true
    }
}