package com.example.task


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.model.popularpeople.KnownFor
import com.example.task.model.popularpeople.PopularPeopleModel
import com.example.task.model.popularpeople.result
import com.example.task.paging.EndlessRecyclerViewScrollListener
import com.example.task.view.adapter.PopularPeopleAdapter
import com.example.task.viewmodel.PopularPeopleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.security.auth.login.LoginException
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    lateinit var popularPeopleAdapter: PopularPeopleAdapter
    lateinit var popularPeopleViewModel: PopularPeopleViewModel
    var my_page = 1

    lateinit var result_List: MutableList<result>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       result_List=  mutableListOf<result>()
        popularPeopleViewModel =
            ViewModelProvider(this)[PopularPeopleViewModel::class.java]

        recyclerPopularPeople.apply {
            layoutManager = GridLayoutManager(applicationContext,2)
            recyclerPopularPeople.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            popularPeopleAdapter = PopularPeopleAdapter(result_List)
            adapter = popularPeopleAdapter


            recyclerPopularPeople.addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(layoutManager as GridLayoutManager) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?) {
                    my_page++
                    popularPeopleList(my_page)
                }
            })

        }


        popularPeopleList(my_page);
    }

    private fun popularPeopleList (page:Int){
        popularPeopleViewModel.getPopularPeopleList(applicationContext,"889821def8006c20b36edf63a80b98fd",
            "en-US",page).observe(this,
            Observer<PopularPeopleModel> { popularPeopleModel ->

                result_List.addAll(popularPeopleModel.results)
                Toast.makeText(this,result_List.size.toString(),Toast.LENGTH_LONG).show()
                popularPeopleAdapter.notifyItemRangeInserted(
                    popularPeopleAdapter.getItemCount(),
                    result_List.size
                )

            })
            }


}
