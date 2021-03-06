package com.example.task.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.model.personimages.PersonImagesModel
import com.example.task.network.APIClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PersonImagesViewModel : ViewModel(){
    public var personImagesListMutableLiveData: MutableLiveData<PersonImagesModel>? = null
    private lateinit var context: Context

    public fun getPersonImagesList(context: Context, Person_id:String,Api_key: String)
            : LiveData<PersonImagesModel> {
        personImagesListMutableLiveData = MutableLiveData<PersonImagesModel>()
        this.context = context
        getPersonImagesListValues(Person_id,Api_key)

        //  return listProductsMutableLiveData
        return personImagesListMutableLiveData as MutableLiveData<PersonImagesModel>

    }

    private fun getPersonImagesListValues(person_id:String, api_key:String) {

        val call = APIClient.getInstance()?.personImages(person_id,api_key)
        call?.enqueue(object : Callback, retrofit2.Callback<PersonImagesModel> {
            override fun onResponse(
                call: Call<PersonImagesModel>,
                response: Response<PersonImagesModel>
            ) {

                if (response.code() == 200) {
                    personImagesListMutableLiveData?.setValue(response.body())

                } else {
                    personImagesListMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PersonImagesModel>, t: Throwable) {
                personImagesListMutableLiveData?.setValue(null)

            }
        })

    }

}