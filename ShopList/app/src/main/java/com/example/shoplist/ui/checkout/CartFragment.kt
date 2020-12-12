package com.example.shoplist.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shoplist.Common.Common
import com.example.shoplist.Models.ItemList
import com.example.shoplist.R

class CartFragment : Fragment(),  CartItemAdapter.ICartChanged{

    private var selectedItemsList: ArrayList<ItemList> = arrayListOf()
    private lateinit var tvTotalValue: TextView


    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        selectedItemsList = arguments?.getParcelableArrayList(ARG_LIST)!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        val listView = root.findViewById<ListView>(R.id.lw_productlistView)

        val res:ArrayList<ItemList> = ArrayList((selectedItemsList
            .groupingBy {x -> x.itemName }
            .reduce {_,  acc, element ->
                acc.copy(itemName = element.itemName,
                    itemDesc = element.itemDesc,
                    itemImage = element.itemImage,
                    itemType = element.itemType,
                    itemuid = element.itemuid,
                    itemValues = element.itemValues,
                    itemPrice = acc.itemPrice + element.itemPrice
                    )
            }
            .values.toList()))// Calculate the total values for each item

        tvTotalValue = root.findViewById(R.id.tv_totalValue)
        val price =selectedItemsList.map { x -> x.itemPrice }.sum() // the total price
        tvTotalValue.text = getText(R.string.totalPrice).toString() + " " + Common().formatingDec().format(price) +Common.CURRENCY_USD

        val listViewAdapter = CartItemAdapter(this.requireActivity(), res, selectedItemsList)
        listViewAdapter.setOnRateAddButtonClickedListener(this)
        listView.adapter = listViewAdapter

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_LIST = "item_list"
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(items: ArrayList<ItemList>): CartFragment {
            return CartFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_LIST, items)
                }
            }
        }
    }

    override fun onChangeOfLst(fso: ArrayList<ItemList>) {
        //do Nothing dont need the list for the fragment
    }

    override fun onPriceChange(totalPrice: Double) {
        if(totalPrice <= 0.1)
            tvTotalValue.text = "Your bag is empty :("
        else
            tvTotalValue.text = getText(R.string.totalPrice).toString() + " " +  Common().formatingDec().format(totalPrice) +Common.CURRENCY_USD
    }
}