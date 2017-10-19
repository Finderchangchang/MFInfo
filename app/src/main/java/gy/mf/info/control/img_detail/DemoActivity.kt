package gy.mf.info.control.img_detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView

import gy.mf.info.R
import gy.mf.info.control.HorizontalAdapter
import java.util.*

class DemoActivity : AppCompatActivity() {
    internal var titles = arrayOf("Android", "Beta", "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Nougat", "Android O")
    var list:MutableList<String> =ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        for(s in titles){
            list.add(s)
        }
        val firstAdapter = HorizontalAdapter(list, this)
        val firstRecyclerView = findViewById(R.id.first_recycler_view) as MultiSnapRecyclerView
        val firstManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        firstRecyclerView.layoutManager = firstManager
        firstRecyclerView.adapter = firstAdapter
    }
}
