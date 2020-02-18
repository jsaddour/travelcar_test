package com.example.travelcartest.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.travelcartest.R
import com.example.travelcartest.network.ErrorManager
import com.example.travelcartest.utils.RecyclerViewHelper
import com.example.travelcartest.utils.RecyclerViewHolder
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var recyclerViewHelper: RecyclerViewHelper<RecyclerViewHolder>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchAdapter()
        recyclerViewHelper =
            RecyclerViewHelper(recyclerview_vehicles, adapter)

        swipe_refresh_layout.isRefreshing = true

        searchViewModel.fetchVehicles()
        searchViewModel.getVehicles().observe(this, Observer {vehicles ->
            swipe_refresh_layout.isRefreshing = false
            adapter.updateList(ArrayList(vehicles))
        })

        swipe_refresh_layout.setOnRefreshListener {
            searchViewModel.fetchVehicles()
        }

        searchViewModel.error.observe(this, Observer { error ->
            swipe_refresh_layout.isRefreshing = false
            ErrorManager.displayError(context, error)
        })

        adapter.onItemClicked().subscribe {
            findNavController(this).navigate(
                SearchFragmentDirections.actionNavigationSearchToVehicleDetailsFragment(it.id)
            )
        }
    }

}