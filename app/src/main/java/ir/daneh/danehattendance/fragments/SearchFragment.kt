package ir.daneh.danehattendance.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.SearchView

import ir.daneh.danehattendance.R
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {
    lateinit var listViewAdapter : ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container , false)

        val listView = view.listViewSearch
        val searchedItemArray = arrayOf("آیتم اول", "آیتم دوم", "آیتم سوم", "آیتم چهارم")
        listViewAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, searchedItemArray)
        listView.adapter = listViewAdapter

        setHasOptionsMenu(true)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        //val searchedItemArray = arrayOf("آیتم اول", "آیتم دوم", "آیتم سوم", "آیتم چهارم")
        super.onCreateOptionsMenu(menu, inflater)
        val item: MenuItem = menu.findItem(R.id.search_bar)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listViewAdapter.filter.filter(newText)
                return false
            }

        })
    }
}
