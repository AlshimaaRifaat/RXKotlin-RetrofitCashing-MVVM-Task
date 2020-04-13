package com.example.task.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.task.Constants
import com.example.task.R
import com.example.task.model.personimages.PersonImagesModel
import com.example.task.model.personimages.Profile
import com.example.task.view.adapter.PersonImagesAdapter
import com.example.task.viewmodel.PersonImagesViewModel
import kotlinx.android.synthetic.main.activity_details_popular_people.*
import kotlinx.android.synthetic.main.row_popular_people.*
import kotlinx.android.synthetic.main.row_popular_people.img
import kotlinx.android.synthetic.main.row_popular_people.tName
import kotlinx.android.synthetic.main.row_popular_people.tPopularity
import java.security.AccessController.getContext

class DetailsPopularPeopleActivity : AppCompatActivity() {
    lateinit var personId:String
    lateinit var name:String
    lateinit var popularity:String
    lateinit var image:String

    lateinit var personImagesAdapter:PersonImagesAdapter
    lateinit var personImagesViewModel: PersonImagesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_popular_people)
        personImagesViewModel =
            ViewModelProvider(this)[PersonImagesViewModel::class.java]
        getData()

        personImagesList()
    }

    private fun personImagesList() {
        personImagesViewModel.getPersonImagesList(applicationContext, personId
            ,"889821def8006c20b36edf63a80b98fd").observe(this,
            Observer<PersonImagesModel> { personImagesModel ->
                if (personImagesModel != null) {
                    recyclerPersonImages.apply {
                        layoutManager= LinearLayoutManager(applicationContext)
                        personImagesAdapter = PersonImagesAdapter(applicationContext,personImagesModel.profiles)
                        adapter=personImagesAdapter
                    }

                }

            })
    }

    private fun getData() {
       personId =intent.getStringExtra("personId")
        name=intent.getStringExtra("name")
        popularity=intent.getStringExtra("popularity")
        image=intent.getStringExtra("img")
        tName.text=name
        tPopularity.text=popularity
        Glide.with(this).load(Constants.IMAGE_BASE_URL+image).into(img);
        Toast.makeText(applicationContext, personId, Toast.LENGTH_LONG).show()
    }
}
