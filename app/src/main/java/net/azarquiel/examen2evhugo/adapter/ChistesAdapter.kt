package net.azarquiel.examen2evhugo.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.examen2evhugo.R
import net.azarquiel.examen2evhugo.model.Chiste


class ChistesAdapter(val context: Context,
                     val layout: Int
                    ) : RecyclerView.Adapter<ChistesAdapter.ViewHolder>() {

    private var dataList: List<Chiste> = emptyList()

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

    internal fun setChistes(chiste: List<Chiste>) {
        this.dataList = chiste
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Chiste){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val ivrowchiste = itemView.findViewById(R.id.ivchiste) as ImageView
            Picasso.get().load("http://www.ies-azarquiel.es/paco/apichistes/img/${dataItem.idcategoria}.png").into(ivrowchiste)
            val length = if ( dataItem.contenido.length < 25 ) dataItem.contenido.length else 25
            val tvherorowhero = itemView.findViewById(R.id.tvchiste) as TextView
            tvherorowhero.setText(Html.fromHtml(dataItem.contenido.substring(0,length)))



            // foto de internet a traves de Picasso



            itemView.tag = dataItem

        }

    }
}