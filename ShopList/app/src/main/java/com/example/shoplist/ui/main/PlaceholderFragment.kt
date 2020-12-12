package com.example.shoplist.ui.main

import ItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.shoplist.Models.ItemList
import com.example.shoplist.R

/**
 * A placeholder fragment containing a simple view.
 */

class PlaceholderFragment : Fragment() {

    private var itemsList: ArrayList<ItemList> = arrayListOf()
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemsList = arguments?.getParcelableArrayList(ARG_LIST)!!
        title = arguments?.getString(ARG_TITLE)!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val listView = root.findViewById<ListView>(R.id.lw_productlistView)


        val listViewAdapter = ItemAdapter(this.requireActivity(), itemsList)
        listView.adapter = listViewAdapter

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_TITLE = "title"
        private const val ARG_LIST = "item_list"
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, title:String, items:ArrayList<ItemList>): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_TITLE, title)
                    putParcelableArrayList(ARG_LIST, items)
                }
            }
        }
    }




}