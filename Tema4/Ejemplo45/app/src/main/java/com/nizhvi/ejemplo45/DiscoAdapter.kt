import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nizhvi.ejemplo45.MyDisco
import com.nizhvi.ejemplo45.R

class DiscoAdapter(discosList: MutableList<MyDisco>, context: Context): RecyclerView.Adapter<DiscoAdapter.DiscoViewHolder>() {
    var myDiscos: MutableList<MyDisco>
    var myContext: Context

    init {
        myDiscos = discosList
        myContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            DiscoViewHolder {
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
    class DiscoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titulo = view.findViewById(R.id.tv_tituloDisco) as TextView
        private val autor = view.findViewById(R.id.tv_autorDisco) as TextView
        private val discoImg = view.findViewById(R.id.iv_discoImage) as ImageView
        fun bind(disco: MyDisco, context: Context) {
            Log.d("bind", disco.titulo.toString())
            titulo.text = disco.titulo
            autor.text = disco.autor
            discoImg.setImageResource(disco.imagen)
            // Definimos el código a ejecutar si se hace click en el item
            itemView.setOnClickListener {
                Toast.makeText(
                    context,
                    disco.titulo,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}
