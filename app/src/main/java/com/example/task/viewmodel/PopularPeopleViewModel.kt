package com.example.task.viewmodel

//import com.example.task.model.PopularPeopleModel
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.model.popularpeople.PopularPeopleModel
import com.example.task.network.APIClient
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class PopularPeopleViewModel : ViewModel(){
    public var popularPeopleListMutableLiveData: MutableLiveData<PopularPeopleModel>? = null
    private lateinit var context: Context

    public var searchPeopleMutableLiveData: MutableLiveData<PopularPeopleModel>? = null


    val compositeDisposable = CompositeDisposable()

    private val autoCompletePublishSubject = PublishRelay.create<String>()
    public fun getPopularPeopleList(context: Context, Api_key: String, Language:String, Page:Int)
            : LiveData<PopularPeopleModel> {
        popularPeopleListMutableLiveData = MutableLiveData<PopularPeopleModel>()
        this.context = context
        getPopularPeopleListValues(Api_key,Language,Page)

        //  return listProductsMutableLiveData
        return popularPeopleListMutableLiveData as MutableLiveData<PopularPeopleModel>

    }

    private fun getPopularPeopleListValues( api_key:String, language:String, page:Int) {
        compositeDisposable.add(
            APIClient.getInstance()
                .popularPeople_List(api_key,language,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({response -> popularPeopleListMutableLiveData?.setValue(response)}, {t -> onFailure(t) }))

        /* val call = APIClient.getInstance().api
             .popularPeople_List(api_key,language,page)
         call.enqueue(object : Callback, retrofit2.Callback<PopularPeopleModel> {
             override fun onResponse(
                 call: Call<PopularPeopleModel>,
                 response: Response<PopularPeopleModel>
             ) {

                 if (response.code() == 200) {
                     popularPeopleListMutableLiveData?.setValue(response.body())

                 } else {
                     popularPeopleListMutableLiveData?.setValue(null)
                 }
             }

             override fun onFailure(call: Call<PopularPeopleModel>, t: Throwable) {
                 popularPeopleListMutableLiveData?.setValue(null)

             }
         })*/

    }

    private fun onFailure(t: Throwable) {

            Toast.makeText(context,t.message, Toast.LENGTH_SHORT).show()


    }


    public fun searchPeopleResult(context: Context, Api_key: String, Language:String, Query: String,Page:Int,Include_adult:Boolean,Region:String)
            : LiveData<PopularPeopleModel> {
        searchPeopleMutableLiveData = MutableLiveData<PopularPeopleModel>()
        this.context = context
        searchPeopleResultValues(Api_key,Language,Query,Page,Include_adult,Region)

        //  return listProductsMutableLiveData
        return searchPeopleMutableLiveData as MutableLiveData<PopularPeopleModel>
    }

    private fun searchPeopleResultValues(api_key:String, language:String, query: String, page:Int, include_adult:Boolean, region:String) {
   /*     val call = APIClient.getInstance().api
            .searchPeople(api_key,language,query,page,include_adult,region)
        call.enqueue(object : Callback, retrofit2.Callback<PopularPeopleModel> {
            override fun onResponse(
                call: Call<PopularPeopleModel>,
                response: Response<PopularPeopleModel>
            ) {

                if (response.code() == 200) {
                    searchPeopleMutableLiveData?.setValue(response.body())

                } else {
                    searchPeopleMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PopularPeopleModel>, t: Throwable) {
                searchPeopleMutableLiveData?.setValue(null)

            }
        })*/
       /* compositeDisposable.add(APIClient.getInstance().api
                .searchPeople(api_key, language, query, page, include_adult, region)
                .debounce(500, TimeUnit.MILLISECONDS)
               *//* .filter {
                    it?.isNullOrBlank()
                }*//*
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .subscribe { response -> searchPeopleMutableLiveData?.postValue(response) })*/
        autoCompletePublishSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { APIClient.getInstance()?.searchPeople(api_key, language, query, page, include_adult, region) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> searchPeopleMutableLiveData?.postValue(response) },{t -> onFailure(t) })

    }
    fun onOmnibarInputStateChanged(query: String) {
        autoCompletePublishSubject.accept(query)
    }

}