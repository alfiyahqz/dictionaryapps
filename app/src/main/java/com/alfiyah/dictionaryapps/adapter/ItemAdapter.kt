package com.alfiyah.dictionaryapps.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.alfiyah.dictionaryapps.R
import com.alfiyah.dictionaryapps.detail.DetailActivity
import com.alfiyah.dictionaryapps.model.Item

class ItemAdapter(
    private var mContext: Context,
    private var mItem: List<Item>,
    private var isFragment: Boolean = false
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var onClick: ((Item) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.word_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val item = mItem[position]
        holder.itemWord.text = item.word
        holder.itemTerm.text = item.term

    }

    override fun getItemCount(): Int {
        return mItem.size
    }

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemWord: TextView = itemView.findViewById(R.id.tv_word)
        var itemTerm: TextView = itemView.findViewById(R.id.tv_term)

        init {
            itemView.setOnClickListener {
                onClick?.invoke(mItem[adapterPosition])
            }
        }

    }

}