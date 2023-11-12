package com.nizhvi.practica3

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nizhvi.practica3.Utils.toEnum
import com.nizhvi.practica3.databinding.ActivityStudentBinding
//Nicolas Zhan
class StudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startElements()
    }
    //Inicializa todas las funcionalidades de la activity a sus correspondiente funciones
    private fun startElements() {
        val name = intent.getStringExtra(Utils.EXTRA_NAME)
        val day = intent.getStringExtra(Utils.EXTRA_DAY)
        val month = intent.getStringExtra(Utils.EXTRA_MONTH)
        val year = intent.getStringExtra(Utils.EXTRA_YEAR)
        val classroom = intent.getStringExtra(Utils.EXTRA_CLASSROOM)
        val cycle = intent.getStringExtra(Utils.EXTRA_CYCLE)
        val modality = intent.getStringExtra(Utils.EXTRA_MODALITY)

        val indexGroup = intent.getIntExtra(Utils.EXTRA_GROUP, -1)
        val group:Group = indexGroup?.toEnum<Group>()!!

        binding.tvNameStudent.text = name
        binding.tvBirthdayDate.text = "$day de $month de $year"
        binding.tvDataStudent.text = "Group ${group.name}\n\nAula $classroom\n\n$cycle $modality"

        binding.btBack.setOnClickListener { actionsBtCancel() }
    }

    private fun actionsBtCancel() {
        setResult(Activity.RESULT_CANCELED)
        Log.d(Utils.TAG_APP, "Se devuelve cancelar")
        finish()
    }
}