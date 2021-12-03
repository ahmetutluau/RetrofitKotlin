package com.ahmetutlu

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetutlu.retrofitkotlin.databinding.RecyclerRowBinding
import com.ahmetutlu.retrofitkotlin.model.CryptoModel

class RecyclerAdapter(private val cryptoList : ArrayList<CryptoModel>,private val listener:Listener) :RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> (){

    interface Listener{
        fun onItemclick(cryptoModel: CryptoModel)
    }

    //rengarenk bir background yapmak için bu diziyi gösteriyoruz
    private val colors:Array<String> = arrayOf("#57ff19","#fff538","#ff3624","#ff47f3","#0800ff")

    class RecyclerHolder( val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cryptoModel: CryptoModel,colors:Array<String>,position: Int,listener:Listener){
            itemView.setOnClickListener {
                listener.onItemclick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 5]))
            binding.textName.text=cryptoModel.currency
            binding.priceName.text=cryptoModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecyclerHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }


}