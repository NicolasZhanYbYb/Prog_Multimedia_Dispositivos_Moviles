package com.nizhvi.practica3

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nizhvi.practica3.Utils.getModality
import com.nizhvi.practica3.Utils.getMonth
import com.nizhvi.practica3.Utils.toInt
import com.nizhvi.practica3.databinding.ItemStudentListBinding


//Nicolas Zhan
class StudentAdapterCursor(context: Context, cursor: Cursor): RecyclerView.Adapter<StudentAdapterCursor.StudentViewHolder>() {
    private var context: Context
    private var cursor: Cursor
    init {
        this.context = context
        this.cursor = cursor
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {
        Log.d("RECYCLERVIEW", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        return StudentViewHolder(
            inflater.inflate(
                R.layout.item_student_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (cursor != null)
            cursor.count
        else 0
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.name.text = cursor.getString(1)
        holder.day.text = cursor.getString(2)
        var month = getMonth(cursor.getInt(3))
        holder.month.text = month
        holder.year.text = cursor.getString(4)
        var modality = getModality(cursor.getString(5).toBoolean())
        holder.modality.text = modality
        holder.cycle.text = cursor.getString(6)
    }

    inner class StudentViewHolder : RecyclerView.ViewHolder {
        val name: TextView
        val day: TextView
        val month: TextView
        val year: TextView
        val modality: TextView
        val cycle: TextView

        constructor(view: View) : super(view) {
            val bindingItemsRV = ItemStudentListBinding.bind(view)
            this.name = bindingItemsRV.tvName
            this.day = bindingItemsRV.tvDay
            this.month = bindingItemsRV.tvMonth
            this.year = bindingItemsRV.tvYear
            this.modality = bindingItemsRV.tvModality
            this.cycle = bindingItemsRV.tvCycle
            itemView.setOnClickListener {
                val myIntent = Intent(context, StudentActivity::class.java).apply { putAllExtras(this, view) }
                context.startActivity(myIntent)
            }
        }

        private fun putAllExtras(intent: Intent, view: View) {
            val bindingItemsRV = ItemStudentListBinding.bind(view)
            val name = bindingItemsRV.tvName.text.toString()
            val day = bindingItemsRV.tvDay.text.toString()
            val month = bindingItemsRV.tvMonth.text.toString()
            val year = bindingItemsRV.tvYear.text.toString()
            val cycle = bindingItemsRV.tvCycle.text.toString()
            val modality = bindingItemsRV.tvModality.text.toString().toBoolean()

            val modal = if (modality)
                Modality.PRESENCIAL
            else
                Modality.SEMIPRESENCIAL

            val cycleE = when (cycle) {
                "ASIR" -> Cycle.ASIR
                "DAM" -> Cycle.DAM
                else -> Cycle.DAW
            }
            val group: Group = Utils.getGroupStudent(cycleE, modal)
            val classRoom: String = Utils.getClassRoom(group)

            with (intent){
                putExtra(Utils.EXTRA_DAY, day)
                putExtra(Utils.EXTRA_MONTH, month)
                putExtra(Utils.EXTRA_YEAR, year)
                putExtra(Utils.EXTRA_NAME, name)
                putExtra(Utils.EXTRA_CYCLE, cycle)
                putExtra(Utils.EXTRA_MODALITY, getModality(modality))
                putExtra(Utils.EXTRA_GROUP, group.toInt())
                putExtra(Utils.EXTRA_CLASSROOM, classRoom)
            }
        }
    }
}
