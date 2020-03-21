package cl.loopa.android.oldays

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OldaysGaleriaAdapter : RecyclerView.Adapter<OldaysGaleriaViewHolder>() {
    var list: Array<String>? = arrayOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OldaysGaleriaViewHolder {
        return OldaysGaleriaViewHolder(parent)
    }

    override fun onBindViewHolder(holder: OldaysGaleriaViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    fun setItem(list: Array<String>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list!!.size

}