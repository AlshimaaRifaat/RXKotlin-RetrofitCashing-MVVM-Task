package com.example.task.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.interfaceview.PopularPeopleDetailsView
import com.example.task.model.popularpeople.PopularPeopleModel
import com.example.task.model.popularpeople.result
import com.example.task.paging.EndlessScrollListener
import com.example.task.view.adapter.PopularPeopleAdapter
import com.example.task.viewmodel.PopularPeopleViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity(),PopularPeopleDetailsView{
    lateinit var key:String
    lateinit var searchPeopleAdapter: PopularPeopleAdapter
    lateinit var popularPeopleViewModel: PopularPeopleViewModel

    var my_page = 1
    lateinit var result_List: MutableList<result>

     var totalPages:Int=0
    lateinit var endlessScrollListener: EndlessScrollListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        result_List=  mutableListOf<result>()
        popularPeopleViewModel =
            ViewModelProvider(this)[PopularPeopleViewModel::class.java]
        getData()
        //ic_search.setOnClickListener { view-> if(!etSearch.text.toString().isEmpty())searchPeopleResult()}


        recyclerSearchPeople.apply {
            layoutManager = LinearLayoutManager(applicationContext)
//            recyclerSearchPeople.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            searchPeopleAdapter = PopularPeopleAdapter(result_List)
            searchPeopleAdapter.onClick(this@SearchResultActivity)
            adapter = searchPeopleAdapter


            endlessScrollListener = object : EndlessScrollListener(layoutManager!!) {
                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                    if(currentPage<totalPages) {
                        my_page++
                        searchPeopleResult(my_page)
                    }
                }

                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
            }

            recyclerSearchPeople.addOnScrollListener(endlessScrollListener)



        }
        searchPeopleResult(my_page);

    }

    private fun searchPeopleResult(page:Int) {
        popularPeopleViewModel.searchPeopleResult(applicationContext,"889821def8006c20b36edf63a80b98fd",
            "en-US",key,page,true,"").observe(this,
            Observer<PopularPeopleModel> { popularPeopleModel ->
                if(popularPeopleModel.results!=null) {
                    this.totalPages=popularPeopleModel.totalPages
                   // Toast.makeText(this,result_List.size.toString(),Toast.LENGTH_LONG).show()
                    result_List.addAll(popularPeopleModel.results)
                    searchPeopleAdapter.notifyItemRangeInserted(
                        searchPeopleAdapter.getItemCount(),
                        result_List.size
                    )
                    Toast.makeText(this,searchPeopleAdapter.getItemCount().toString(),Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun getData() {
        key =intent.getStringExtra("key")

    }

    override fun showPopularPeopleDetails(mresult: result) {
        val intent = Intent(this, DetailsPopularPeopleActivity::class.java)
        intent.putExtra("personId",mresult.id.toString())
        intent.putExtra("name",mresult.name)
        intent.putExtra("popularity",mresult.popularity.toString())
        intent.putExtra("img",mresult.profilePath)
        startActivity(intent)
    }
}
