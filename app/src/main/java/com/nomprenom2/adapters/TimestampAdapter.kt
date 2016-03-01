package com.nomprenom2.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import com.nomprenom2.model.Timestamp
import io.realm.RealmBaseAdapter
import io.realm.RealmResults

/**
 * Created by musachev on 01.03.2016.
 */
class TimestampAdapter(context : Context, realmResults : RealmResults<Timestamp>, automaticUpdate : Boolean) : RealmBaseAdapter<Timestamp>(context, realmResults, automaticUpdate), ListAdapter
{
    companion object ViewHolder
    {
        var timestamp : TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parentViewGroup: ViewGroup?): View?
    {
        val viewHolder = convertView?.tag as ViewHolder
        val timestamp = realmResults[position]
        viewHolder?.timestamp?.text = timestamp.timeStamp

        return convertView
    }
}