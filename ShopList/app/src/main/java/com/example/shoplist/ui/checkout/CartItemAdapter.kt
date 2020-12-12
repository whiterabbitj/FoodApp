package com.example.shoplist.ui.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.shoplist.Common.Common
import com.example.shoplist.Models.ItemList
import com.example.shoplist.R

class CartItemAdapter(val context: Context,
                      private val selectedItemList: ArrayList<ItemList>, // used to display the item in the listview
                      private val totalList:ArrayList<ItemList>   //used when user returns back to the main menu
     ) : BaseAdapter() {

    private var mPriceChangedListener:ICartChanged ?= null


    interface ICartChanged {
        /**
         * Called when an item is changed.
         *
         */
        fun onPriceChange (totalPrice: Double)
        fun onChangeOfLst(fso :ArrayList<ItemList>)
    }



    /**
     * Sets the listener that should be notified of button clicked events.
     *
     * @param listener the listener to notify.
     */
    private lateinit var listener:ICartChanged


    fun setOnRateAddButtonClickedListener(listener: ICartChanged ){
        mPriceChangedListener = listener
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                val layoutInflater = LayoutInflater.from(context)
                view = layoutInflater.inflate(R.layout.selected_list_view, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            listener = context as ICartChanged
            //current list item
            val curFso = selectedItemList[position]

            //Get single item price plus the formatting
            val singleItemPrice = totalList
                .filter { x -> x.itemuid == curFso.itemuid }
                .map { x -> x.itemPrice}.first()

        val talCount = totalList.count { x -> x.itemuid == curFso.itemuid }

            //Set textview and  othe visible elements
            vh.tvProductName.text = curFso.itemName
            vh.tvPrice.text =  Common().formatingDec().format(curFso.itemPrice).toString() + Common.CURRENCY_USD
            Glide.with(context).load(curFso.itemImage).into(vh.ivProductImage)
            vh.tvTotal.text = talCount.toString()
//                itemCount.getValue(curFso.itemuid).toString()



            vh.removeBtn.setOnClickListener {  // remove the whole item from the listview
                totalList.removeIf { x -> x.itemuid == curFso.itemuid }  // remove all items from the list (used later in MainActivity)
                selectedItemList.removeAt(position)//remove specific list item
                listener.onChangeOfLst(totalList)
                mPriceChangedListener?.onPriceChange(selectedItemList.map { x -> x.itemPrice }.sum())
                notifyDataSetChanged()
            }

            vh.increaseBtn.setOnClickListener {
                selectedItemList[position].itemPrice = curFso.itemPrice + singleItemPrice // set new price (in item list) by increasing the price by single item's price
                totalList.add(totalList.first { x -> x.itemuid == curFso.itemuid })   // add one of the items from total list
                listener.onChangeOfLst(totalList)
                mPriceChangedListener?.onPriceChange(selectedItemList.map { x -> x.itemPrice }.sum())
                notifyDataSetChanged()

            }

            vh.decreaseBtn.setOnClickListener {
                if(talCount > 1){ //check if there are more than one item added
                    selectedItemList[position].itemPrice = curFso.itemPrice - singleItemPrice// set new price (in item list) by reducing the price by single item's price
                    totalList.remove(totalList.first { x -> x.itemuid == curFso.itemuid })   // remove one of the items from total list
                    listener.onChangeOfLst(totalList)
                    mPriceChangedListener?.onPriceChange(selectedItemList.map { x -> x.itemPrice }.sum()) // update the price in the fragment
                    notifyDataSetChanged()
            }}

            return view
        }

        override fun getItem(position: Int): Any {
            return selectedItemList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return selectedItemList.size
        }

    }

    private class ViewHolder(view: View?) {
        val removeBtn: ImageButton  = view?.findViewById(R.id.btn_remove) as ImageButton
        val increaseBtn: ImageButton  = view?.findViewById(R.id.btn_increase) as ImageButton
        val decreaseBtn: ImageButton  = view?.findViewById(R.id.btn_decrease) as ImageButton
        val tvTotal: TextView  = view?.findViewById(R.id.tv_value) as TextView
        val ivProductImage: ImageView = view?.findViewById(R.id.iv_productImage) as ImageView
        val tvPrice: TextView = view?.findViewById(R.id.tv_price) as TextView
        val tvProductName:TextView = view?.findViewById(R.id.tv_productName) as TextView



    }
