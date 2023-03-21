package net.azarquiel.examen2evhugo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.examen2evhugo.R
import net.azarquiel.examen2evhugo.model.Categorias


class CategoriasAdapter(val context: Context,
                        val layout: Int
                    ) : RecyclerView.Adapter<CategoriasAdapter.ViewHolder>() {

    private var dataList: List<Categorias> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setCategorias(categorias: List<Categorias>) {
        this.dataList = categorias
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Categorias){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val ivrowcategoria = itemView.findViewById(R.id.ivImagen) as ImageView

            Picasso.get().load("http://www.ies-azarquiel.es/paco/apichistes/img/${dataItem.id}.png").into(ivrowcategoria)
            val tvherorowhero = itemView.findViewById(R.id.tvTexto) as TextView

            tvherorowhero.text = dataItem.nombre


            // foto de internet a traves de Picasso



            itemView.tag = dataItem

        }

    }
}