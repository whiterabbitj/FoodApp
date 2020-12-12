package com.example.shoplist.ui
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.shoplist.Models.ItemList
import com.example.shoplist.ui.checkout.CartFragment
import com.example.shoplist.ui.checkout.NotImplementedFrag
import com.example.shoplist.ui.main.PlaceholderFragment
import java.util.stream.Collectors


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager, numberOfTabs: Int, items:ArrayList<ItemList>, titles:List<String>): FragmentPagerAdapter(fm) {

    private var fragment: Fragment? = null
    private var noOfTabs: Int = numberOfTabs
    private var itemsList: ArrayList<ItemList> = items
    private var titleList: List<String> = titles

    override fun getItem(position: Int): Fragment {

        for (i in 0..noOfTabs){ // populate fragments accordingly  each title determines a type of frgment
            if (i == position){
               val title =  titleList[i]
                fragment = if(title == "Cart")
                    CartFragment.newInstance(itemsList)
                else if(title == "Order" || title == "Process")
                    NotImplementedFrag.newInstance()
                else {
                    PlaceholderFragment.newInstance(
                        i,
                        title,
                        itemsList.stream().filter { x -> x.itemType == title }.collect(
                            Collectors.toList()
                        ) as ArrayList<ItemList>
                    )
                }
                break
            }
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return noOfTabs
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]

    }
}
