package com.nizhvi.practica2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nizhvi.practica2.databinding.ActivityStudentsListBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
//Nicolas Zhan
class StudentsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentsListBinding
    private lateinit var myAdapter: StudentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    private fun getStudents(): MutableList<MyStudent> {
        return readStudentFile()
    }
    //Lee el fichero que guarda los datos de la lista y los devuelve como un MutableList
    private fun readStudentFile(): MutableList<MyStudent> {
        val student: MutableList<MyStudent> = arrayListOf()

        if (fileList().contains(getString(R.string.filename))) try {
            val entrada = InputStreamReader(openFileInput(getString(R.string.filename)))
            val br = BufferedReader(entrada)
            var line = br.readLine()
            
            while (!line.isNullOrEmpty()) {
                val data: List<String> = line.split(";")

                val day = data[0].toInt()
                val month = data[1].toInt()
                val year = data[2].toInt()
                val name = data[3]
                val modality = data[4].toBoolean()
                val cycle = data[5]

                student.add(MyStudent(day, month, year, name, modality, cycle))
                line = br.readLine()
            }
            br.close()
            entrada.close()
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,
                R.string.no_existe_fichero,
                Toast.LENGTH_LONG).show()
        }

        return student
    }

    private fun setUpRecyclerView() {
        binding.myStudentsList.setHasFixedSize(true)
        binding.myStudentsList.layoutManager = LinearLayoutManager(this)
        myAdapter = StudentAdapter(getStudents(), this)
        binding.myStudentsList.adapter = myAdapter
    }
}