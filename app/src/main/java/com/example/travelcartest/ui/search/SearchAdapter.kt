package com.example.travelcartest.ui.search

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelcartest.R
import com.example.travelcartest.data.entity.Vehicle
import com.example.travelcartest.utils.RecyclerViewHolder
import com.jakewharton.rxrelay2.PublishRelay
import kotlinx.android.synthetic.main.row_vehicle.view.*


class SearchAdapter(
    private var vehicles: ArrayList<SearchViewModel.VehicleModel> = ArrayList(),
    private var highlight: String = ""
) :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    private val itemClickedRelay = PublishRelay.create<SearchViewModel.VehicleModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.row_vehicle, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val spannableMake = SpannableString(vehicles[position].make)
        var startMake = vehicles[position].make.indexOf(highlight, ignoreCase = true)
        var endMake = startMake + highlight.length

        if (startMake < 0) {
            startMake = 0
            endMake = 0
        }

        spannableMake.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorAccent
                )
            ),
            startMake, endMake,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val spannableModel = SpannableString(vehicles[position].model)
        var startModel = vehicles[position].model.indexOf(highlight, ignoreCase = true)
        var endModel = startModel + highlight.length

        if (startModel < 0) {
            startModel = 0
            endModel = 0
        }

        spannableModel.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorAccent
                )
            ),
            startModel, endModel,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        holder.itemView.vehicle_make_label.text = spannableMake
        holder.itemView.vehicle_model_label.text = spannableModel
        Glide
            .with(holder.itemView.context)
            .load(vehicles[position].picture)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.itemView.vehicle_img)

        holder.itemView.setOnClickListener { itemClickedRelay.accept(vehicles[position]) }

    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    fun onItemClicked(): PublishRelay<SearchViewModel.VehicleModel> {
        return itemClickedRelay
    }

    fun updateList(vehicles: ArrayList<SearchViewModel.VehicleModel>, highlight: String = "") {
        this.vehicles = vehicles
        this.highlight = highlight
        notifyDataSetChanged()
    }
}