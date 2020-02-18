package com.example.travelcartest.utils

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelcartest.R


class RecyclerViewHelper<H : RecyclerView.ViewHolder>(
    recyclerView: RecyclerView,
    var adapter: RecyclerView.Adapter<H>,
    reversed: Boolean = false
) {
    var itemTouchHelper: ItemTouchHelper? = null

    init {
        // Adapter
        recyclerView.adapter = adapter

        // Layout Manager
        var linearLayoutManager: RecyclerView.LayoutManager

            linearLayoutManager = LinearLayoutManager(recyclerView.context)
            if (reversed) {
                linearLayoutManager.stackFromEnd = true
                linearLayoutManager.reverseLayout = true
            }


        recyclerView.layoutManager = linearLayoutManager

    }
}

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)