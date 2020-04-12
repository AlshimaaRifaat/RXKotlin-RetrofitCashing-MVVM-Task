package com.example.task.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task.R
import kotlinx.android.synthetic.main.row_popular_people.*

class DetailsPopularPeopleActivity : AppCompatActivity() {
    lateinit var personId:String
    lateinit var name:String
    lateinit var popularity:String
    lateinit var img:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_popular_people)
        getData()
    }

    private fun getData() {
       personId =intent.getStringExtra("personId")
        name=intent.getStringExtra("name")
        popularity=intent.getStringExtra("popularity")
        img=intent.getStringExtra("img")
        tName.text=name
        tPopularity.text=popularity
    }
}
