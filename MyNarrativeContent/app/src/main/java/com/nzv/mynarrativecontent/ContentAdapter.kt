package com.nzv.mynarrativecontent

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nzv.mynarrativecontent.databinding.ItemContentListBinding

class ContentAdapter (discosList: MutableList<MyContent>, context: Context): RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {
    private var myDiscos: MutableList<MyContent>
    private var myContext: Context
    init {
        myDiscos = discosList
        myContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_content_list, parent, false)
        return ContentViewHolder(view, myContext)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val item = myDiscos[position]
        holder.bind(item, myContext)
    }

    override fun getItemCount(): Int {
        return myDiscos.size
    }

    class ContentViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {
        private val binding = ItemContentListBinding.bind(view)
        private var actionMode: ActionMode? = null

        fun bind(content: MyContent, context: Context) {
            Log.d("bind", content.name)
            binding.tvCycle.text = content.name

            itemView.setOnLongClickListener() {
                when (actionMode) {
                    null -> {
                        // Se lanza el ActionMode.
                        actionMode = it.startActionMode(actionModeCallback)
                        it.isSelected = true
                        true
                    }
                    else -> false
                }
            }

            itemView.setOnClickListener {
                val myIntent = Intent(context, ContentActivity::class.java).apply { putAllExtras(this, content) }

                context.startActivity(myIntent)
            }
        }

        private fun putAllExtras(intent: Intent, student: MyContent) {
            with (intent){
                /*putExtra(Utils.EXTRA_DAY, student.day.toString())
                putExtra(Utils.EXTRA_MONTH, Utils.getMonth(student.month))
                putExtra(Utils.EXTRA_YEAR, student.year.toString())
                putExtra(Utils.EXTRA_NAME, student.name)
                putExtra(Utils.EXTRA_CYCLE, student.cycle)
                putExtra(Utils.EXTRA_MODALITY, getModality(student.isPresencial))
                putExtra(Utils.EXTRA_GROUP, group.toInt())
                putExtra(Utils.EXTRA_CLASSROOM, classRoom)*/
            }
        }

        private val actionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val inflater = MenuInflater(context)
                inflater.inflate(R.menu.action_menu, menu)
                return true
            }
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }
            override fun onActionItemClicked(mode: ActionMode, item: MenuItem):
                    Boolean {
                return when (item.itemId) {
                    R.id.option01 -> {
                        return true
                    }
                    R.id.option02 -> {
                        return true
                    }
                    else -> false
                }
            }
            override fun onDestroyActionMode(mode: ActionMode) {
                actionMode = null
            }
        }
    }

}