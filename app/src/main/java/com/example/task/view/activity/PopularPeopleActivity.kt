package com.example.task



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.interfaceview.PopularPeopleDetailsView
import com.example.task.model.popularpeople.PopularPeopleModel
import com.example.task.model.popularpeople.result
import com.example.task.paging.EndlessScrollListener
import com.example.task.view.activity.DetailsPopularPeopleActivity
import com.example.task.view.activity.SearchResultActivity
import com.example.task.view.adapter.PopularPeopleAdapter
import com.example.task.viewmodel.PopularPeopleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity(),PopularPeopleDetailsView{
    lateinit var popularPeopleAdapter: PopularPeopleAdapter
    lateinit var popularPeopleViewModel: PopularPeopleViewModel
    var my_page = 1
    lateinit var result_List: MutableList<result>

    var totalPages:Int=0

    lateinit var endlessScrollListener: EndlessScrollListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       result_List=  mutableListOf<result>()
        popularPeopleViewModel =
            ViewModelProvider(this)[PopularPeopleViewModel::class.java]

        recyclerPopularPeople.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            recyclerPopularPeople.setItemAnimator(DefaultItemAnimator())
            popularPeopleAdapter = PopularPeopleAdapter(result_List)
            popularPeopleAdapter.onClick(this@MainActivity)
            adapter = popularPeopleAdapter

            endlessScrollListener = object : EndlessScrollListener(layoutManager!!) {
                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                    if(currentPage<totalPages) {
                        my_page++
                        popularPeopleList(my_page)
                    }
                }

                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
            }

            recyclerPopularPeople.addOnScrollListener(endlessScrollListener)



        }
        popularPeopleList(my_page);

        etSearch.setOnClickListener { sendKeySearch() }

    }

    private fun sendKeySearch() {
            if (etSearch.length()>=3) {
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra("key", etSearch.text.toString())
                startActivity(intent)
            }else
            {
                Toast.makeText(this,getResources().getString(R.string.PleaseCharacters),Toast.LENGTH_LONG).show()
            }


    }


    private fun popularPeopleList (page:Int){
        popularPeopleViewModel.getPopularPeopleList(applicationContext,"889821def8006c20b36edf63a80b98fd",
            "en-US",page).observe(this,
            Observer<PopularPeopleModel> { popularPeopleModel ->
                if(popularPeopleModel.results!=null) {
                    this.totalPages=popularPeopleModel.totalPages
                    result_List.addAll(popularPeopleModel.results)

                    popularPeopleAdapter.notifyItemRangeInserted(
                        popularPeopleAdapter.getItemCount(),
                        result_List.size
                    )
                    Toast.makeText(this,popularPeopleAdapter.getItemCount().toString(),Toast.LENGTH_LONG).show()
                }
            })
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
