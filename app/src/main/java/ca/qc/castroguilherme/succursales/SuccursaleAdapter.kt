package ca.qc.castroguilherme.succursales

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ca.qc.castroguilherme.succursales.model.Succursale
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SuccursaleAdapter() : RecyclerView.Adapter<SuccursaleAdapter.SuccursaleViewHolder>() {


    lateinit var onDeleteClickListener: ((Succursale) -> Unit)
    lateinit var onEditClickListener: ((Succursale) -> Unit)
    lateinit var onFavClickListener: ((Succursale) -> Unit)
    private var succursales: List<Succursale> = emptyList()

    inner class SuccursaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val villeItem: TextView = itemView.findViewById(R.id.villeItem)
        val budgetItem: TextView = itemView.findViewById(R.id.budgetItem)
        val editBtn: FloatingActionButton = itemView.findViewById(R.id.editActBtn)
        val delteBtn: FloatingActionButton = itemView.findViewById(R.id.deleteActBtn)
        val favoriteBtn: FloatingActionButton = itemView.findViewById(R.id.addFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuccursaleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_succursale, parent, false)
        return SuccursaleViewHolder(view)
    }

    override fun getItemCount(): Int = succursales.size

    fun setSuccursales(succursales: List<Succursale>) {
        this.succursales = succursales
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: SuccursaleViewHolder, position: Int) {
        val succursale = succursales[position]

        holder.villeItem.text = succursale.ville
        holder.budgetItem.text = "${succursale.budget} $"

        holder.delteBtn.setOnClickListener { onDeleteClickListener.invoke(succursale) }
        holder.editBtn.setOnClickListener { onEditClickListener.invoke(succursale) }
        holder.favoriteBtn.setOnClickListener { onFavClickListener.invoke(succursale) }



    }
}