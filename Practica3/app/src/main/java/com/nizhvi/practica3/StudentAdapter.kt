package com.nizhvi.practica3

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nizhvi.practica3.Utils.getModality
import com.nizhvi.practica3.Utils.toInt
import com.nizhvi.practica3.databinding.ItemStudentListBinding


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
        private val binding = ItemStudentListBinding.bind(view)

        fun bind(student: MyStudent, context: Context) {
            Log.d("bind", student.name)
            binding.tvMonth.text = Utils.getMonth(student.month)
            binding.tvDay.text = student.day.toString()
            binding.tvYear.text = student.year.toString()
            binding.tvName.text = student.name
            binding.tvModality.text = getModality(student.isPresencial)
            binding.tvCycle.text = student.cycle

            itemView.setOnClickListener {
                val myIntent = Intent(context, StudentActivity::class.java).apply { putAllExtras(this, student) }

                context.startActivity(myIntent)
            }
        }

        private fun putAllExtras(intent: Intent, student: MyStudent) {
            val modal = if (student.isPresencial)
                Modality.PRESENCIAL
            else
                Modality.SEMIPRESENCIAL

            val cycle = when (student.cycle) {
                "ASIR" -> Cycle.ASIR
                "DAM" -> Cycle.DAM
                else -> Cycle.DAW
            }

            val group: Group = Utils.getGroupStudent(cycle, modal)
            val classRoom: String = Utils.getClassRoom(group)

            with (intent){
                putExtra(Utils.EXTRA_DAY, student.day.toString())
                putExtra(Utils.EXTRA_MONTH, Utils.getMonth(student.month))
                putExtra(Utils.EXTRA_YEAR, student.year.toString())
                putExtra(Utils.EXTRA_NAME, student.name)
                putExtra(Utils.EXTRA_CYCLE, student.cycle)
                putExtra(Utils.EXTRA_MODALITY, getModality(student.isPresencial))
                putExtra(Utils.EXTRA_GROUP, group.toInt())
                putExtra(Utils.EXTRA_CLASSROOM, classRoom)
            }
        }
    }
}
