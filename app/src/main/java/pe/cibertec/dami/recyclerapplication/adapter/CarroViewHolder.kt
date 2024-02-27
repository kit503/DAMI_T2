package pe.cibertec.dami.recyclerapplication.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.cibertec.dami.recyclerapplication.R
import pe.cibertec.dami.recyclerapplication.model.Carro

class CarroViewHolder(view: View, position: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

    val lblMarca: TextView = view.findViewById(R.id.tvMarcaRecycler)
    val lblModelo: TextView = view.findViewById(R.id.tvModeloRecycler)

    init {
        itemView.setOnClickListener {
            position(adapterPosition)
        }
    }

    fun render(carro: Carro) {
        lblMarca.text = carro.marca
        lblModelo.text = carro.modelo
    }
}