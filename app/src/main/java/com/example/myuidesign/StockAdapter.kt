package com.example.myuidesign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myuidesign.databinding.MarketMoverLayoutBinding
import com.example.myuidesign.model.MarketMover

class StockAdapter:RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    private var stockmoverlist= mutableListOf<MarketMover>()

    fun setStockMover(list: List<MarketMover>){
        this.stockmoverlist=list.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=MarketMoverLayoutBinding.inflate(inflater,parent,false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
      val stocklist=stockmoverlist[position]
      holder.binding.ivcmplogo.setImageResource(stocklist.image)
      holder.binding.tvcmpname.text=stocklist.comp_name
      holder.binding.tvcmpdetails.text=stocklist.comp_fullName
      holder.binding.tvstockrate.text=stocklist.stockValue
      holder.binding.tvstockratepercen.text=stocklist.stockper
      holder.binding.tvshares2.text=stocklist.shares
      holder.binding.tvstockrateDoller.text=stocklist.priceDoller
    }

    override fun getItemCount(): Int {
      return stockmoverlist.size
    }

    inner class StockViewHolder(val binding: MarketMoverLayoutBinding):RecyclerView.ViewHolder(binding.root){

    }
}