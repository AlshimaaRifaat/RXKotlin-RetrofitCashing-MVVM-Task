package com.example.task


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task.model.PopularPeopleModel
import com.example.task.paging.EndlessScrollListener
import com.example.task.view.adapter.PopularPeopleAdapter
import com.example.task.viewmodel.PopularPeopleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var popularPeopleAdapter: PopularPeopleAdapter
    lateinit var popularPeopleViewModel: PopularPeopleViewModel

    var my_page = 1

    lateinit var endlessScrollListener: EndlessScrollListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularPeopleViewModel =
            ViewModelProvider(this)[PopularPeopleViewModel::class.java]

        popularPeopleList()
    }

    private fun popularPeopleList (){
        popularPeopleViewModel.getPopularPeopleList(applicationContext,"889821def8006c20b36edf63a80b98fd",
            "en-US",1).observe(this,
            Observer<PopularPeopleModel> { popularPeopleModel ->


                recyclerPopularPeople.apply {
                    layoutManager = GridLayoutManager(applicationContext, 2)
                    // recyclerPopularPeople.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                    popularPeopleAdapter = PopularPeopleAdapter(popularPeopleModel.results)
                    adapter = popularPeopleAdapter
                }

                });
            }


}
