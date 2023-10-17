package com.nizhvi.practica1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.NotificationCompat.getGroup
import com.nizhvi.practica1.databinding.ActivityMainBinding
//Nicolas Zhan
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rbASIR.isChecked = true
        binding.rbPresencial.isChecked = true

        binding.buttonObtDatos.setOnClickListener { actionsButtons("button", it) }
    }

    private fun actionsButtons(name: String, view: View) {

        if (!checkDataEntered()) {
            Toast.makeText(applicationContext, "Hay campos sin datos introducidos", Toast.LENGTH_LONG).show()
            return
        }

        var day = binding.etDia.text.toString().toInt()
        var month = binding.etMes.text.toString().toInt()
        var year = binding.etAnyo.text.toString().toInt()

        var date = Date(day, month, year);

        if (!checkCorrectDate(date)) {
            Toast.makeText(applicationContext, "La fecha no es válida", Toast.LENGTH_LONG).show()
            return
        }

        var age = date.getAge();
        var group: Group = getGroupStudent()
        var classRoom: String = getClassRoom(group)

        when (name) {
            "button" -> {
                binding.txtName.text = binding.etIntroducirNom.text
                binding.txtDatos.text = "Edad: " + age + "\nGrupo " + group.name + "\nAula " + classRoom
            }
        }

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    //Devuelve el grupo al que pertenece segun los radioButtons marcados
    private fun getGroupStudent(): Group {
        if (binding.rbASIR.isChecked) {
            if (binding.rbPresencial.isChecked)
                return Group.A
            else if (binding.rbSemipresencial.isChecked)
                return Group.B
        } else if (binding.rbDAM.isChecked) {
            if (binding.rbPresencial.isChecked)
                return Group.C
            else if (binding.rbSemipresencial.isChecked)
                return Group.D
        } else if (binding.rbDAW.isChecked)
            if (binding.rbPresencial.isChecked)
                return Group.E
            else if (binding.rbSemipresencial.isChecked)
                return Group.F

        return Group.Default
    }
    //Devuelve el string que te dice el aula según al grupo que pertence
    private fun getClassRoom(group: Group): String {
        when (group) {
            Group.A -> return "201"
            Group.B -> return "202"
            Group.C -> return "203"
            Group.D -> return "204"
            Group.E -> return "205"
            Group.F -> return "206"
            else -> return "Sin asignar"
        }
    }
    //Comprueba que estén todos los campos con contenido
    private fun checkDataEntered(): Boolean {
        if (binding.etIntroducirNom.text.toString().isEmpty())
            return false
        if (binding.etDia.text.toString().isEmpty() || binding.etMes.text.toString().isEmpty() || binding.etAnyo.text.toString().isEmpty())
            return false
        return true
    }
    //Comprueba que la fecha introducida sea correcta
    private fun checkCorrectDate(date: Date): Boolean {
        if (!date.validateDay(date.day))
            return false
        if (!date.validateMonth(date.month))
            return false
        if (!date.validateYear(date.year))
            return false

        return true
    }
}