package com.example.travelcartest.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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
        setHasOptionsMenu(true)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {

                (recyclerViewHelper.adapter as SearchAdapter).updateList(filter(newText), newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                (recyclerViewHelper.adapter as SearchAdapter).updateList(filter(query), query)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchAdapter()
        recyclerViewHelper =
            RecyclerViewHelper(recyclerview_vehicles, adapter)

        swipe_refresh_layout.isRefreshing = true

        searchViewModel.fetchVehicles()
        searchViewModel.getVehicles().observe(this, Observer { vehicles ->
            activity?.invalidateOptionsMenu()
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

    fun filter(filter: String) : ArrayList<SearchViewModel.VehicleModel> {
        searchViewModel.getVehicles().value?.let { vehicles->
            return ArrayList(vehicles.filter {
                it.make.contains(filter, true) ||
                        it.model.contains(filter, true)
            })
        }
        return ArrayList()
    }

}