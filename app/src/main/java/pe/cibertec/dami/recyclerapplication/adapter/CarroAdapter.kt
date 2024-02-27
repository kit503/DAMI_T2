package pe.cibertec.dami.recyclerapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.cibertec.dami.recyclerapplication.R
import pe.cibertec.dami.recyclerapplication.model.Carro

class CarroAdapter(
    private val carroList: List<Carro>,
    private val clickListener: (Carro) -> Unit
) :
    RecyclerView.Adapter<CarroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val vh = CarroViewHolder(layoutInflater.inflate(R.layout.layout_carro, parent, false)) {
            clickListener(carroList[it])
        }
        return vh
    }

    override fun onBindViewHolder(holder: CarroViewHolder, position: Int) {
        val itemCarro = carroList[position]
        holder.render(itemCarro)
    }

    override fun getItemCount(): Int = carroList.size

}