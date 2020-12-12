package com.example.shoplist.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.shoplist.MainActivity
import com.example.shoplist.Models.ItemList
import com.example.shoplist.R
import com.example.shoplist.ui.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class ChekoutActivity : AppCompatActivity() , CartItemAdapter.ICartChanged {


    private var selectedItemList :ArrayList<ItemList> = arrayListOf<ItemList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedItemList = this.intent.getParcelableArrayListExtra("cartItems")

        setContentView(R.layout.activity_cart)

        val viewPager: ViewPager = findViewById(R.id.view_pager)

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.isSmoothScrollingEnabled = true

        val backButton : ImageButton =  findViewById(R.id.btn_goBack)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("updatedItems", selectedItemList)
            startActivity(intent)
        }


        val orderTabs :ArrayList<String> = arrayListOf()
        orderTabs.add(getString(R.string.tab_text_1))
        orderTabs.add(getString(R.string.tab_text_2))
        orderTabs.add(getString(R.string.tab_text_3))



        for (i in 1..orderTabs.count())
            tabs.addTab(tabs.newTab().setText(orderTabs[i-1]))

        val pagerViewAdapter = SectionsPagerAdapter(
            supportFragmentManager,
            tabs.tabCount,
            selectedItemList,
            orderTabs
        )
        viewPager.adapter = pagerViewAdapter
        viewPager.adapter = pagerViewAdapter
        tabs.setupWithViewPager(viewPager, true)
    }

    override fun onPriceChange(totalPrice: Double) {
        //do nothing only fragment need this
    }

    override fun onChangeOfLst(fso: ArrayList<ItemList>) {
        selectedItemList = fso

    }


}


