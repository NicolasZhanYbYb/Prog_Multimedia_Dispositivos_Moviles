package com.nzv.exam

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nzv.exam.databinding.ActivityDatosRecycleBinding
import com.nzv.exam.databinding.ItemAppListBinding

class AppAdapterCursor(context: Context, cursor: Cursor):
    RecyclerView.Adapter<AppAdapterCursor.AppViewHolder>() {
    private var context: Context
    private var cursor: Cursor
    init {
        this.context = context
        this.cursor = cursor
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppViewHolder {
        Log.d("RECYCLERVIEW", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        return AppViewHolder(
            inflater.inflate(
                R.layout.item_app_list,
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
    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.app.text = cursor.getString(1)
        holder.tema.text = cursor.getString(2)
        holder.hora.text = cursor.getString(3)
        holder.modalidad.text = cursor.getString(4)

    }

    inner class AppViewHolder : RecyclerView.ViewHolder {
        val app: TextView
        val tema: TextView
        val hora: TextView
        val modalidad: TextView

        constructor(view: View) : super(view) {
            val bindingItemsRV = ItemAppListBinding.bind(view)
            this.app = bindingItemsRV.tvApp
            this.tema = bindingItemsRV.tvTema
            this.hora = bindingItemsRV.tvHoras
            this.modalidad = bindingItemsRV.tvModality
            itemView.setOnClickListener {
                val myIntent = Intent(context, DatosRecycle::class.java).apply {
                    putExtra(Utils.EXTRA_HORAS, hora.text.toString())
                    putExtra(Utils.EXTRA_TEMA, tema.text.toString())
                    putExtra(Utils.EXTRA_APP, app.text.toString())
                    putExtra(Utils.EXTRA_MODALITY, modalidad.text.toString())
                }
                context.startActivity(myIntent)
            }
        }
    }
}