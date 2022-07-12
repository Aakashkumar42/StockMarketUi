package com.example.myuidesign.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myuidesign.R
import com.example.myuidesign.StockAdapter
import com.example.myuidesign.databinding.FragmentStockBinding
import com.example.myuidesign.databinding.StockActivityLayoutBinding
import com.example.myuidesign.model.MarketMover


class StockFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private val adapter=StockAdapter()
    private var stocklist= listOf<MarketMover>()
    lateinit var binding: FragmentStockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentStockBinding.inflate(inflater,container,false)
      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvstock.adapter=adapter

        val list= listOf(
            MarketMover(
            "NVD",
            R.drawable.ic_baseline_search_24,
            "NVIDIA",
            "$230",
            "15%",
            "$230",
            "15 Shares"
            ),
            MarketMover(
                "Abode",
                R.drawable.ic_baseline_search_24,
                "Adobe Inc",
                "$233",
                "13%",
                "$233",
                "12 Shares"
            ),
            MarketMover(
                "Apple",
                R.drawable.ic_baseline_search_24,
                "Apple Inc",
                "$133",
                "10%",
                "$230",
                "10 Shares"
            ))
                adapter.setStockMover(list)
    }

}