package com.example.task



import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
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
import com.example.task.view.adapter.PopularPeopleAdapter
import com.example.task.viewmodel.PopularPeopleViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),PopularPeopleDetailsView{
    lateinit var popularPeopleAdapter: PopularPeopleAdapter
    lateinit var popularPeopleViewModel: PopularPeopleViewModel
    var my_page = 1
    lateinit var result_List: MutableList<result>

    var totalPages:Int=0

    lateinit var endlessScrollListener: EndlessScrollListener
    //private val kProgressHUD: KProgressHUD? = null


    //lateinit var searchPeopleAdapter: PopularPeopleAdapter
    var my_search_page = 1
//    lateinit var result_search_List: MutableList<result>
    var Status:Boolean=false
    var totalSearchPages:Int=0
    lateinit var endlessSearchScrollListener: EndlessScrollListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       result_List=  mutableListOf<result>()
//        result_search_List=  mutableListOf<result>()
        popularPeopleViewModel =
            ViewModelProvider(this)[PopularPeopleViewModel::class.java]
       // searchPeopleAdapter = PopularPeopleAdapter(applicationContext,result_search_List)
       popularPeopleAdapter = PopularPeopleAdapter(applicationContext,result_List)

       recyclerPopularPeople.adapter=popularPeopleAdapter
       ScrollSearch()
        performPopularPeopleList()



        SearchKeyBoard()
        EditSearchChanger()

    }

    private fun performPopularPeopleList() {
        popularPeopleAdapter.onClick(this@MainActivity)
         initScroll()
        popularPeopleList(my_page);

    }

    fun initScroll(){
        recyclerPopularPeople.layoutManager = LinearLayoutManager(applicationContext)
        recyclerPopularPeople.setItemAnimator(DefaultItemAnimator())

        endlessScrollListener = object : EndlessScrollListener(recyclerPopularPeople.layoutManager!!) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                if (Status == false) {
                    if (currentPage < totalPages) {
                        my_page++
                        popularPeopleList(my_page)
                    }
                } else {
                    if (currentPage < totalPages) {
                        my_page++
                        searchPeopleResult(my_page)
                    }
                }
            }

            override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
        }
    }




    fun ScrollSearch(){
        recyclerPopularPeople.layoutManager = LinearLayoutManager(applicationContext)
        endlessScrollListener = object : EndlessScrollListener(recyclerPopularPeople.layoutManager!!) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                if (currentPage < totalPages) {
                    my_page++
                    searchPeopleResult(my_page)
                }

            }

            override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
        }


    }

    private fun EditSearchChanger() {
        etSearch.addTextChangedListener(object : TextWatcher {


            override fun afterTextChanged(s: Editable) {
                initScroll()
                popularPeopleAdapter.onClick(this@MainActivity)
                    result_List.clear()
//                popularPeopleAdapter.notifyDataSetChanged()
                my_page = 1
                totalPages=0

                if(s.isEmpty()){
                    Status=false
                    popularPeopleList(my_page)
                }else {

                    Status=true
                    searchPeopleResult(my_page)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {


            }
        })
    }

    private fun SearchKeyBoard() {
        etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(!etSearch.text.toString().isEmpty()) {
                    my_page = 1
                    totalPages=0
                    initScroll()
                    popularPeopleAdapter.onClick(this@MainActivity)
                    searchPeopleResult(my_page);
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    val isConnected:Boolean
        get() {
            return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }

    private fun popularPeopleList (page:Int) {
        if (isConnected) {
            progressBar_people.visibility= View.VISIBLE
            popularPeopleViewModel.getPopularPeopleList(
                applicationContext, "889821def8006c20b36edf63a80b98fd",
                "en-US", page
            ).observe(this,
                Observer<PopularPeopleModel> { popularPeopleModel ->
                    progressBar_people.visibility=View.GONE

                    if (popularPeopleModel.results != null) {
                        this.totalPages = popularPeopleModel.totalPages
                        result_List.addAll(popularPeopleModel.results)

                        popularPeopleAdapter.notifyItemRangeInserted(
                            popularPeopleAdapter.getItemCount(),
                            result_List.size
                        )
                      //  Toast.makeText(this,"pop"+result_List.size,Toast.LENGTH_LONG).show()
                        recyclerPopularPeople.addOnScrollListener(endlessScrollListener)

                    }
                })
        }else {
            Toast.makeText(applicationContext, R.string.Check_network_connection, Toast.LENGTH_LONG).show()

        }
        }


    override fun showPopularPeopleDetails(mresult: result) {
        val intent = Intent(this, DetailsPopularPeopleActivity::class.java)
        intent.putExtra("personId",mresult.id.toString())
        intent.putExtra("name",mresult.name)
        intent.putExtra("popularity",mresult.popularity.toString())
        intent.putExtra("img",mresult.profilePath)
        intent.putExtra("department",mresult.knownForDepartment)
        startActivity(intent)
    }






    private fun searchPeopleResult(page:Int) {
        if (isConnected) {
           // progressBar_people.visibility = View.VISIBLE
            popularPeopleViewModel.searchPeopleResult(
                applicationContext, "889821def8006c20b36edf63a80b98fd",
                "en-US", etSearch.text.toString(), page, true, ""
            ).observe(this,
                Observer<PopularPeopleModel> { popularPeopleModel ->
                    if (popularPeopleModel.results != null) {
                       // progressBar_people.visibility=View.GONE
                        this.totalPages = popularPeopleModel.totalPages
                        // Toast.makeText(this,result_List.size.toString(),Toast.LENGTH_LONG).show()
                        result_List.addAll(popularPeopleModel.results)
                        popularPeopleAdapter.notifyItemRangeInserted(
                            popularPeopleAdapter.getItemCount(),
                            result_List.size
                        )
                     //   Toast.makeText(this,"search "+ result_List.size,Toast.LENGTH_LONG).show()
                        recyclerPopularPeople.addOnScrollListener(endlessScrollListener)

                    }

                })
        }else
        {
            Toast.makeText(applicationContext, R.string.Check_network_connection, Toast.LENGTH_LONG
            ).show()
        }
    }


}
