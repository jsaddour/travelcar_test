package com.example.travelcartest.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelcartest.R
import com.example.travelcartest.data.entity.Vehicle
import com.example.travelcartest.utils.RecyclerViewHolder
import com.jakewharton.rxrelay2.PublishRelay
import kotlinx.android.synthetic.main.row_vehicle.view.*


class SearchAdapter(private var vehicles: ArrayList<SearchViewModel.VehicleModel> = ArrayList()) :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    private val itemClickedRelay = PublishRelay.create<SearchViewModel.VehicleModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.row_vehicle, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.vehicle_make_label.text = vehicles[position].make
        holder.itemView.vehicle_model_label.text = vehicles[position].model
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

    fun updateList(vehicles: ArrayList<SearchViewModel.VehicleModel>) {
        this.vehicles = vehicles
        notifyDataSetChanged()
    }
}