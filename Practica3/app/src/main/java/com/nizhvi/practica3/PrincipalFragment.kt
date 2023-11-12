package com.nizhvi.practica3

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.nizhvi.practica3.Utils.EXTRA_CYCLE
import com.nizhvi.practica3.Utils.EXTRA_DAY
import com.nizhvi.practica3.Utils.EXTRA_MODALITY
import com.nizhvi.practica3.Utils.EXTRA_MONTH
import com.nizhvi.practica3.Utils.EXTRA_YEAR
import com.nizhvi.practica3.Utils.closeKeyboard
import com.nizhvi.practica3.Utils.getClassRoom
import com.nizhvi.practica3.Utils.getGroupStudent
import com.nizhvi.practica3.Utils.showSnackBar
import com.nizhvi.practica3.Utils.toEnum
import com.nizhvi.practica3.databinding.FragmentPrincipalBinding
import java.io.IOException
import java.io.OutputStreamWriter
//Nicolas Zhan
class PrincipalFragment : Fragment() {
    private lateinit var binding: FragmentPrincipalBinding
    private lateinit var studentDBHelper: MyStudentsDBOpenHelper
    private var day = 0
    private var month = 0
    private var year = 0
    private lateinit var modality: Modality
    private lateinit var cycle: Cycle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrincipalBinding.inflate(inflater)
        studentDBHelper = MyStudentsDBOpenHelper(requireContext())
        startElements()

        return binding.root
    }

    //Inicializa todas las funcionalidades de la activity a sus correspondiente funciones
    @SuppressLint("SetTextI18n")
    private fun startElements() {
        with (binding){
            btLeerDatos.setOnClickListener {actionsBtShowData(it) }
            btObtDatos.setOnClickListener {actionsBtGetData(it) }
            btGuardarHistorico.setOnClickListener {actionsBtSaveHistory(it) }
            txtDatos.text = getString(R.string.date_of_birth_modality_cycle)
        }
    }

    private fun myAlertDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setTitle("Histórico")
            setMessage(message)
            setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener(function = actionBtAlertDialog))
            setNegativeButton(android.R.string.cancel) { _, _ ->
                showSnackBar(getString(R.string.informacion_no_registrada), requireView())
            }
        }

        builder.show()
    }

    @SuppressLint("SetTextI18n")
    private val actionBtAlertDialog = { dialog: DialogInterface, which: Int ->
        enterDataInDB()
        showSnackBar(getString(R.string.informacion_registrada), requireView())

        with (binding){
            btObtDatos.isEnabled = false
            btGuardarHistorico.isEnabled = false
            etIntroducirNom.text.clear()
            txtDatosCalculados.text = " "
            txtDatos.text = getString(R.string.date_of_birth_modality_cycle)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun actionsBtSaveHistory(view: View) {
        closeKeyboard(view, requireActivity())
        myAlertDialog(getString(R.string.desea_guardar_historico))
    }

    @SuppressLint("SetTextI18n")
    private fun actionsBtGetData(view: View) {
        closeKeyboard(view, requireActivity())

        val date = Date(day, month, year)

        val age = date.getAge()
        val group: Group = getGroupStudent(cycle, modality)
        val classRoom: String = getClassRoom(group)

        with (binding){
            txtDatosCalculados.text = "Age: " + age + "\nGroup " + group.name + "\nClassroom " + classRoom
            btObtDatos.isEnabled = false
            btGuardarHistorico.isEnabled = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun actionsBtShowData(view: View) {
        closeKeyboard(view, requireActivity())

        if (!checkDataEntered()) {
            showSnackBar(getString(R.string.campos_sin_datos), requireView())
            return
        }

        val myIntent = Intent(requireContext(), SecondActivity::class.java).apply {
                putExtra(Utils.EXTRA_NAME, binding.etIntroducirNom.text.toString())
        }

        getResult.launch(myIntent)

        with (binding){
            txtDatosCalculados.text = " "
            btGuardarHistorico.isEnabled = false
            txtDatos.text = "Date of birth: ?\n" + "Modality: ? Cycle: ?"
        }
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

    //Recoje los datos que se van a introducir en la DB
    private fun enterDataInDB(){
        if(day == 0 || month == 0 || binding.etIntroducirNom.text.isBlank())
            showSnackBar(getString(R.string.error_textos), requireView())
        else
            studentDBHelper.addStudent(binding.etIntroducirNom.text.toString(), day, month, year, modality, cycle)
    }

    //Comprueba que estén todos los campos con contenido
    private fun checkDataEntered(): Boolean {
        if (binding.etIntroducirNom.text.toString().isEmpty())
            return false
        return true
    }
}