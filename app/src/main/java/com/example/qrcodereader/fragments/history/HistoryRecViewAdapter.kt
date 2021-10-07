package com.example.qrcodereader.fragments.history

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodereader.R
import com.example.qrcodereader.localdata.modelfordb.HistoryClass
import com.example.qrcodereader.localdata.modelfordb.TimeToSdf
import java.text.SimpleDateFormat

class HistoryRecViewAdapter : RecyclerView.Adapter<HistoryRecViewAdapter.MyViewHolder>() , TimeToSdf{

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private var historyList = emptyList<HistoryClass>()

    var number = 0
    private val resnumber get() = run {
        number+=1
        number
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = historyList[position]
        setTextForItem(holder,R.id.historyId, resnumber.toString())
        setTextForItem(holder,R.id.historyData,currentItem.qrContent)
        setTextForItem(holder,R.id.historyTimestamp,timeToSDF(currentItem.time))

        holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setOnClickListener {
            val action = HistoryFragmentDirections.actionHistoryFragmentToViewQRFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }


    override fun getItemCount(): Int {
        return historyList.size
    }

    private fun setTextForItem(holder: MyViewHolder,id:Int,text: String){
        holder.itemView.findViewById<TextView>(id).text = text
    }

    override fun timeToSDF(timeRequest: Long): String {
        return  SimpleDateFormat("yyyy.MM.dd HH:mm").format(timeRequest)
    }

    fun setData(student: List<HistoryClass>){
        this.historyList = student
        notifyDataSetChanged()
    }


}