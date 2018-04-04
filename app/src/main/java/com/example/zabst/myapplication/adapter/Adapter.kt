package com.example.zabst.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.zabst.myapplication.R
import com.example.zabst.myapplication.model.responsemodel.ResponseModel

class Adapter(var list: List<ResponseModel>, var context: Context): RecyclerView.Adapter<Adapter.ViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ResponseModel = list[position]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.post!!.text = Html.fromHtml(model.elementPureHtml, Html.FROM_HTML_MODE_LEGACY)
        } else {
            holder.post!!.text = Html.fromHtml(model.elementPureHtml)
        }

        holder.site!!.text = model.site
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var post: TextView ?= null
        var site: TextView ?= null

        init {
            post = itemView!!.findViewById(R.id.postitem_post) as TextView
            site = itemView.findViewById(R.id.postitem_site) as TextView
        }
    }
}