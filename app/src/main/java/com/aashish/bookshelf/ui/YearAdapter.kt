package com.aashish.bookshelf.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textview.MaterialTextView
import androidx.recyclerview.widget.RecyclerView
import com.aashish.bookshelf.R

class YearAdapter(private val years: List<Int>, private val onClick: (Int) -> Unit) : RecyclerView.Adapter<YearAdapter.ViewHolder>() {
    private var highlightedYear = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val yearTextView: MaterialTextView = view.findViewById(R.id.tv_year)
        val underline: View = view.findViewById(R.id.underline)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick(years[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_year, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val year = years[position]
        holder.yearTextView.text = year.toString()

        if (year == highlightedYear) {
            holder.underline.visibility = View.VISIBLE
        } else {
            holder.underline.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = years.size

    fun updateHighlightedYear(year: Int) {
        val previousHighlightedPosition = years.indexOf(highlightedYear)
        highlightedYear = year
        val newHighlightedPosition = years.indexOf(year)
        notifyItemChanged(previousHighlightedPosition)
        notifyItemChanged(newHighlightedPosition)
    }
}