package com.dagoda.my87recyclerviewactionmodeode

import android.content.Context
import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dagoda.my87recyclerviewactionmodeode.databinding.ItemDiscoListBinding

class DiscoAdapter (discosList: MutableList<MyDisco>, context: Context):
    RecyclerView.Adapter<DiscoAdapter.DiscoViewHolder>() {

    var myDiscos: MutableList<MyDisco>
    var myContext: Context

    init {
        myDiscos = discosList
        myContext = context
    }

    // Menú actionMode
    private var actionMode: ActionMode? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_disco_list, parent, false)
        return DiscoViewHolder(view)

    }

    override fun onBindViewHolder(holder: DiscoViewHolder, position: Int) {
        val item = myDiscos.get(position)
        holder.bind(item, myContext)
    }

    override fun getItemCount(): Int {
        return myDiscos.size
    }

    inner class DiscoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Se usa View Binding para localizar los elementos en la vista.
        // Evitamos de esa forma la utilización de findViewById
        private val binding = ItemDiscoListBinding.bind(view)

        fun bind(disco: MyDisco, context: Context) {
            Log.d("bind", disco.titulo.toString())

            binding.tvTituloDisco.text = disco.titulo
            binding.tvAutorDisco.text = disco.autor
            binding.ivDiscoImage.setImageResource(disco.imagen)

            // Definimos el código a ejecutar si se hace click en el item
            itemView.setOnClickListener {
                Toast.makeText(
                    context,
                    disco.titulo,
                    Toast.LENGTH_SHORT
                ).show()
            }

            itemView.setOnLongClickListener {
                when (actionMode) {
                    null -> {
                        // Se lanza el ActionMode.
                        actionMode = it.startActionMode(actionModeCallback)
                        it.isSelected = true
                        true
                    }
                    else -> false
                }

                return@setOnLongClickListener true
            }
        }

        /**
         * Modo de acción contextual
         */
        private val actionModeCallback = object : ActionMode.Callback {
            // Método llamado al selección una opción del menú.
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?)
                    : Boolean {

                return when (item!!.itemId) {
                    R.id.optionDelete -> {
                        // adapterPosition tiene la posición del item en la lista
                        Toast.makeText(myContext, "Eliminar el elemento: ${adapterPosition}", Toast.LENGTH_LONG)
                            .show()
                        mode!!.finish()
                        return true
                    }
                    R.id.optionShare -> {
                        Toast.makeText(myContext, R.string.menu_op_share, Toast.LENGTH_LONG)
                            .show()
                        return true
                    }
                    else -> false
                }
            }

            // Llamado cuando al crear el modo acción a través de startActionMode().
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                //val inflater = mActivity?.menuInflater
                // Así no necesito la activity
                val inflater = mode?.menuInflater
                inflater?.inflate(R.menu.action_mode_menu, menu)
                return true
            }

            // Se llama cada vez que el modo acción se muestra, siempre
            // después de onCreateActionMode().
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?)
                    : Boolean {
                return false
            }

            // Se llama cuando el usuario sale del modo de acción.
            override fun onDestroyActionMode(mode: ActionMode?) {
                actionMode = null
            }
        }


    }
}
