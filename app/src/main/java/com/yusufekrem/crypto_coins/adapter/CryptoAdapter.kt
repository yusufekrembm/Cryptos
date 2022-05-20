package com.yusufekrem.crypto_coins.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufekrem.crypto_coins.R
import com.yusufekrem.crypto_coins.model.CryptoModel
import kotlinx.android.synthetic.main.recycler_row.view.*

class CryptoAdapter(private val cryptoList : ArrayList<CryptoModel>, private val listener:Listener) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {
    private val colors = arrayOf("#8A0A0A","#A86565","#B2CCA2","#62ED0D","#C19EDF")
    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(cryptoModel: CryptoModel,colors : Array<String>, position: Int, listener: Listener){
            itemView.setOnClickListener{
                listener.onItemClick(cryptoModel)
            }

            itemView.setBackgroundColor(Color.parseColor(colors[position % 4]))
            itemView.currencyText.text = cryptoModel.currency
            itemView.priceText.text = cryptoModel.price

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cryptoList[position], colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

}