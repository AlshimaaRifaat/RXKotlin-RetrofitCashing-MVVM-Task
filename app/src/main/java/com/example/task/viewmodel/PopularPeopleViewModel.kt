package com.example.task.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.model.PopularPeopleModel
import com.example.task.network.APIClient
import com.example.task.network.APIInterface
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PopularPeopleViewModel : ViewModel(){
    public var popularPeopleListMutableLiveData: MutableLiveData<PopularPeopleModel>? = null
    private lateinit var context: Context


    public fun getPopularPeopleList(context: Context, Api_key: String, Language:String, Page:Int)
            : LiveData<PopularPeopleModel> {
        popularPeopleListMutableLiveData = MutableLiveData<PopularPeopleModel>()
        this.context = context
        getPopularPeopleListValues(Api_key,Language,Page)

        //  return listProductsMutableLiveData
        return popularPeopleListMutableLiveData as MutableLiveData<PopularPeopleModel>

    }

    private fun getPopularPeopleListValues( api_key:String, language:String, page:Int) {
        val call = APIClient.getClient()?.create(APIInterface::class.java)
            ?.popularPeople_List(api_key,language,page)
        call?.enqueue(object : Callback, retrofit2.Callback<PopularPeopleModel> {
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
        })


    }
}