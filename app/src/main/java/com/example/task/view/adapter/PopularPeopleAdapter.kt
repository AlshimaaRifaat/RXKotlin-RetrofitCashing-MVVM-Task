package com.example.task.view.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.popularpeople.result
//import com.example.task.model.result

class PopularPeopleAdapter(var resultList: List<result>) : RecyclerView.Adapter<PopularPeopleAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPeopleAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_popular_people, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: PopularPeopleAdapter.ViewHolder, position: Int) {
        holder.bindItems(resultList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return resultList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(mResult: result) {
          //  val img = itemView.findViewById(R.id.img) as ImageView
            val tName  = itemView.findViewById(R.id.tName) as TextView

           // img.loadImage(Constants.IMAGE_BASE_URL+referncesModel.refrenceImage)
            tName.text = mResult.knownFor.get(0).originalName

        }
    }
}