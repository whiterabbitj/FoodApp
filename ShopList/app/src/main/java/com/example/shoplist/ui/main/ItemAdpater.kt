import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.shoplist.Common.Common
import com.example.shoplist.Models.ItemList
import com.example.shoplist.R


class ItemAdapter(val context: Context, val itemList: ArrayList<ItemList>) : BaseAdapter() {

    interface IAddItem {
        /**
         * Called when an item is added.
         *
         */
        fun onaAddCliked(fso :ItemList)
    }

    /**
     * Sets the listener that should be notified of button clicked events.
     *
     * @param listener the listener to notify.
     */
    private lateinit var listener:IAddItem




    @SuppressLint("ClickableViewAccessibility")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view: View?
            val vh: ViewHolder

        // Instantiate the NoticeDialogListener so we can send events to the host
        listener = context as IAddItem


            if (convertView == null) {
                val layoutInflater = LayoutInflater.from(context)
                view = layoutInflater.inflate(R.layout.prduct_list_view, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            vh.tvProductDescription.text = itemList[position].itemDesc
            vh.tvProductName.text = itemList[position].itemName
            vh.btnProductPrice.text = itemList[position].itemPrice.toString() + Common.CURRENCY_USD
            vh.tvProductValues.text = itemList[position].itemValues

            vh.btnProductPrice.setOnClickListener {
                listener.onaAddCliked(itemList[position])
            }

            Glide.with(context).load(itemList[position].itemImage).into(vh.ivProductImage)

            return view
        }

        override fun getItem(position: Int): Any {
            return itemList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return itemList.size
        }
    }


    private class ViewHolder(view: View?) {
        val ivProductImage: ImageView = view?.findViewById(R.id.iv_productImage) as ImageView
        val btnProductPrice: Button = view?.findViewById(R.id.btn_productAddPrice) as Button
        val tvProductName: TextView = view?.findViewById(R.id.tv_productName) as TextView
        val tvProductDescription: TextView = view?.findViewById(R.id.tv_productDescription) as TextView
        val tvProductValues: TextView  = view?.findViewById(R.id.tv_productValues) as TextView

    }
