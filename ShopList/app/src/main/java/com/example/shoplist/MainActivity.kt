package com.example.shoplist

import ItemAdapter
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.shoplist.GetData.GetData
import com.example.shoplist.Models.AddList
import com.example.shoplist.Models.ItemList
import com.example.shoplist.ui.SectionsPagerAdapter
import com.example.shoplist.ui.checkout.ChekoutActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class MainActivity : AppCompatActivity(),  ItemAdapter.IAddItem {

    private var itemsListAdded: ArrayList<ItemList> = arrayListOf()
    private var sampleImages: ArrayList<AddList> = arrayListOf()

    lateinit var  mark: TextView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val extras: Bundle? = intent.extras

        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.view_pager)

        mark = findViewById(R.id.mark)
        fab = findViewById(R.id.checkCart)

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.isSmoothScrollingEnabled = true

        if(extras != null) {
            itemsListAdded = this.intent.getParcelableArrayListExtra("updatedItems")
            if(itemsListAdded.size > 0) {
                fab.isVisible = true
                mark.isVisible = true
                mark.setText(itemsListAdded.count().toString())
            }
        }

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val itemsList = GetData().makeRequest()
        sampleImages = GetData().makeAddRequest()

        val distinctCategoryList = itemsList.distinctBy { x -> x.itemType }.map { x -> x.itemType }

        for (i in 1..distinctCategoryList.count())
            tabs.addTab(tabs.newTab().setText(itemsList.distinctBy { x -> x.itemType }[i-1].itemType))

        val pagerViewAdapter = SectionsPagerAdapter(
            supportFragmentManager,
            tabs.tabCount,
            itemsList,
            distinctCategoryList
        )
        viewPager.adapter = pagerViewAdapter
        viewPager.adapter = pagerViewAdapter
        tabs.setupWithViewPager(viewPager, true)

        val carouselView = findViewById<CarouselView>(R.id.carouselView)
        if (carouselView != null) {
            carouselView.pageCount = sampleImages.size
            carouselView.setImageListener(imageListener)
        }

        fab.setOnClickListener {
            val intent = Intent(this, ChekoutActivity::class.java)
            intent.putExtra("cartItems", itemsListAdded)
            startActivity(intent)
        }



    }

    var imageListener = ImageListener {
            position, imageView -> Glide.with(this.applicationContext).load(sampleImages[position].addUrl).into(imageView)
    }

    override fun onaAddCliked(fso: ItemList) {
            itemsListAdded.add(fso)
            mark.isVisible = true
            fab.isVisible = true
            mark.text = itemsListAdded.count().toString()


    }

}


