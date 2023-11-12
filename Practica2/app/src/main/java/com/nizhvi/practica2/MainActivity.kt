package com.nizhvi.practica2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.nizhvi.practica2.databinding.ActivityMainBinding
import java.io.IOException
import java.io.OutputStreamWriter

//Nicolas Zhan
@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var day = 0
    private var month = 0
    private var year = 0
    private lateinit var modality: Modality
    private lateinit var cycle: Cycle

    companion object {
        const val TAG_APP = "myExplicitIntent"
        const val EXTRA_NAME = "myName"
        const val EXTRA_MODALITY = "myModality"
        const val EXTRA_DAY = "myDay"
        const val EXTRA_MONTH = "myMonth"
        const val EXTRA_YEAR = "myYear"
        const val EXTRA_CYCLE = "myCycle"

        //Devuelve el grupo al que pertenece segun los radioButtons marcados
        fun getGroupStudent(cycle: Cycle, modality: Modality): Group {
            return when (cycle) {
                Cycle.ASIR ->
                    return if (modality == Modality.PRESENCIAL)
                        Group.A
                    else
                        Group.B
                Cycle.DAM ->
                    return if (modality == Modality.PRESENCIAL)
                        Group.C
                    else
                        Group.D
                Cycle.DAW ->
                    return if (modality == Modality.PRESENCIAL)
                        Group.E
                    else
                        Group.F
            }
        }

        //Devuelve el string que te dice el aula según al grupo que pertence
        fun getClassRoom(group: Group): String {
            return when (group) {
                Group.A -> "201"
                Group.B -> "202"
                Group.C -> "203"
                Group.D -> "204"
                Group.E -> "205"
                Group.F -> "206"
            }
        }
        //Int to Enum
        inline fun <reified T : Enum<T>> Int.toEnum(): T? {
            return enumValues<T>().firstOrNull { it.ordinal == this }
        }
        //Enum to Int
        inline fun <reified T : Enum<T>> T.toInt(): Int {
            return this.ordinal
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startElements()
    }

    //Inicializa todas las funcionalidades de la activity a sus correspondiente funciones
    @SuppressLint("SetTextI18n")
    private fun startElements() {
        binding.txtDatos.text = "Date of birth: ?\nModality: ? Cycle: ?"
        binding.btLeerDatos.setOnClickListener {actionsBtShowData(it) }
        binding.btObtDatos.setOnClickListener {actionsBtGetData(it) }
        binding.btGuardarHistorico.setOnClickListener {actionsBtSaveHistory(it) }
        binding.btVerHistorico.setOnClickListener {actionsBtShowHistory(it) }
    }

    private fun actionsBtShowHistory(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
 
        val myIntent = Intent(this, StudentsListActivity::class.java).apply { }

        getResult.launch(myIntent)
    }

    @SuppressLint("SetTextI18n")
    private fun actionsBtSaveHistory(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        enterDataInFile()

        binding.btObtDatos.isEnabled = false
        binding.btGuardarHistorico.isEnabled = false
        binding.etIntroducirNom.setText("")
        binding.txtDatosCalculados.text = " "
        binding.txtDatos.text = "Date of birth: ?\n" + "Modality: ? Cycle: ?"
    }

    @SuppressLint("SetTextI18n")
    private fun actionsBtGetData(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        val date = Date(day, month, year)

        val age = date.getAge()
        val group: Group = getGroupStudent(cycle, modality)
        val classRoom: String = getClassRoom(group)

        binding.txtDatosCalculados.text = "Age: " + age + "\nGroup " + group.name + "\nClassroom " + classRoom
        binding.btObtDatos.isEnabled = false
        binding.btGuardarHistorico.isEnabled = true
    }

    @SuppressLint("SetTextI18n")
    private fun actionsBtShowData(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        if (!checkDataEntered()) {
            Toast.makeText(applicationContext, "Hay campos sin datos introducidos", Toast.LENGTH_LONG).show()
            return
        }

        val myIntent = Intent(this, SecondActivity::class.java).apply {
                putExtra(EXTRA_NAME, binding.etIntroducirNom.text.toString())
        }

        getResult.launch(myIntent)

        binding.txtDatosCalculados.text = " "
        binding.btGuardarHistorico.isEnabled = false
        binding.txtDatos.text = "Date of birth: ?\n" + "Modality: ? Cycle: ?"
    }

    @SuppressLint("SetTextI18n")
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK) {
            val day = it.data?.getIntExtra(EXTRA_DAY, -1)
            val month = it.data?.getIntExtra(EXTRA_MONTH, -1)
            val year = it.data?.getIntExtra(EXTRA_YEAR, -1)
            val modality = it.data?.getIntExtra(EXTRA_MODALITY, -1)
            val cycle = it.data?.getIntExtra(EXTRA_CYCLE, -1)

            this.day = day ?: -1
            this.month = month ?: -1
            this.year = year ?: -1
            this.modality = modality?.toEnum<Modality>()!!
            this.cycle = cycle?.toEnum<Cycle>()!!

            binding.btObtDatos.isEnabled = true
            binding.txtDatos.text = "Date of birth: $day / $month / $year\nModality: " + this.modality + " Cycle: " + this.cycle
        }
        if (it.resultCode == Activity.RESULT_CANCELED) {
            binding.btObtDatos.isEnabled = false
        }
    }
    //Recoje los datos que se van a introducir en un fichero
    private fun enterDataInFile(){
        if(day == 0 || month == 0 || year == 0 || binding.etIntroducirNom.text.isEmpty())
            Toast.makeText(applicationContext, R.string.error_textos, Toast.LENGTH_SHORT).show()
        else
        {
            val isPresencial: Boolean = Modality.PRESENCIAL == modality

            val textFile: String = "0" + day.toString() + ";" + month.toString() + ";" + year.toString() +
                    ";"  + binding.etIntroducirNom.text.toString() + ";" + isPresencial.toString() +
                    ";" + cycle.name

            writeInFile(textFile)
        }
    }
    //Escribe en un fichero los datos de los estudiantes
    private fun writeInFile(textFile: String) {
        try {
            val outPut = OutputStreamWriter(openFileOutput(getString(R.string.filename), MODE_APPEND))
            outPut.write(textFile + '\n')
            outPut.flush()
            outPut.close()
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    //Comprueba que estén todos los campos con contenido
    private fun checkDataEntered(): Boolean {
        if (binding.etIntroducirNom.text.toString().isEmpty())
            return false
        return true
    }

    //Int to Enum



}