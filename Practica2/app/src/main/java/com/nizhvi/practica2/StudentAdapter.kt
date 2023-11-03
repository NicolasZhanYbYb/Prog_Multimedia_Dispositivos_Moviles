package com.nizhvi.practica2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

//Nicolas Zhan
class StudentAdapter(discosList: MutableList<MyStudent>, context: Context): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var myDiscos: MutableList<MyStudent>
    private var myContext: Context

    init {
        myDiscos = discosList
        myContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_student_list, parent, false)
        return StudentViewHolder(view)
    }
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val item = myDiscos[position]
        holder.bind(item, myContext)
    }
    override fun getItemCount(): Int {
        return myDiscos.size
    }
    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val month = view.findViewById(R.id.tvMonth) as TextView
        private val day = view.findViewById(R.id.tvDay) as TextView
        private val year = view.findViewById(R.id.tvYear) as TextView
        private val name = view.findViewById(R.id.tvName) as TextView
        private val modality = view.findViewById(R.id.tvModality) as TextView
        private val cycle = view.findViewById(R.id.tvCycle) as TextView
        fun bind(student: MyStudent, context: Context) {
            Log.d("bind", student.name)
            month.text = getMonth(student.month)
            day.text = student.day.toString()
            year.text = student.year.toString()
            name.text = student.name
            modality.text = getModality(student.isPresencial)
            cycle.text = student.cycle

            val modal = if (student.isPresencial)
                Modality.PRESENCIAL
            else
                Modality.SEMIPRESENCIAL

            val cycle = when (student.cycle) {
                "ASIR" -> Cycle.ASIR
                "DAM" -> Cycle.DAM
                else -> Cycle.DAW
            }

            val group: Group = MainActivity.getGroupStudent(cycle, modal)
            val classRoom: String = MainActivity.getClassRoom(group)

            itemView.setOnClickListener {
                Toast.makeText(
                    context,
                    "Group " + group.name + "\nClassroom " + classRoom,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        //Devuelve la modalidad según el boolean isPresencial
        private fun getModality(isPresencial: Boolean): String {
            return if (isPresencial)
                "(Presencial)"
            else
                "(Semipresencial)"
        }
        //Devuelve un string con el nombre del mes según su número
        private fun getMonth(position: Int): String {
            return when (position) {
                1 -> "Enero"
                2 -> "Febrero"
                3 -> "Marzo"
                4 -> "Abril"
                5 -> "Mayo"
                6 -> "Junio"
                7 -> "Julio"
                8 -> "Agosto"
                9 -> "Septiembre"
                10 -> "Octubre"
                11 -> "Noviembre"
                12 -> "Diciembre"
                else -> "Sin asignar"
            }
        }
    }
}
