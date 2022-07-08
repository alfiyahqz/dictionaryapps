package com.alfiyah.dictionaryapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alfiyah.dictionaryapps.HelpSlide
import com.alfiyah.dictionaryapps.R
import com.google.rpc.Help

class HelpSlideAdapter(private val helpSlides: List<HelpSlide>) :
    RecyclerView.Adapter<HelpSlideAdapter.HelpSlideViewHolder>() {

    inner class HelpSlideViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val imgIcon = view.findViewById<ImageView>(R.id.slideIcon)
        private val tvTitle = view.findViewById<TextView>(R.id.slideTitle)
        private val tvDesc = view.findViewById<TextView>(R.id.slideDesc)

        fun bind(helpSlide: HelpSlide){
            imgIcon.setImageResource(helpSlide.icon)
            tvTitle.text = helpSlide.title
            tvDesc.text = helpSlide.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpSlideViewHolder {
        return HelpSlideViewHolder((
                LayoutInflater.from(parent.context).inflate(
                    R.layout.slider_item,
                    parent,
                    false
                )
                ))
    }

    override fun onBindViewHolder(holder: HelpSlideViewHolder, position: Int) {
        holder.bind(helpSlides[position])
    }

    override fun getItemCount(): Int {
        return helpSlides.size
    }
}