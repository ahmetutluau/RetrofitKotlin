package com.ahmetutlu.retrofitkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetutlu.RecyclerAdapter
import com.ahmetutlu.retrofitkotlin.R
import com.ahmetutlu.retrofitkotlin.databinding.ActivityMainBinding
import com.ahmetutlu.retrofitkotlin.model.CryptoModel
import com.ahmetutlu.retrofitkotlin.service.CryptoApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),RecyclerAdapter.Listener {
    private val BASE_URL="https://api.nomics.com/v1/"
    private var cryptoModels:ArrayList<CryptoModel>?=null
    private lateinit var binding:ActivityMainBinding
    //adapter'ı burda oluşturup tanımlıyoruz
    private var recyclerAdapter:RecyclerAdapter?=null

    //Disposable(kullan at)
    private var compositeDisposable:CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //API key: b619a326e28d9d8ae94d644e85f1dbd2919bfbed
        //https://api.nomics.com/v1/prices?key=b619a326e28d9d8ae94d644e85f1dbd2919bfbed

        compositeDisposable = CompositeDisposable()

        loadData()

        //RecyclerView
        val layoutManager:RecyclerView.LayoutManager=LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager

    }
    private fun loadData(){
            val retrofit=Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(CryptoApi::class.java)


            compositeDisposable?.add(retrofit.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse))


        /*
        //Api ile retrofiti birbirine bağlamak için service oluşturuyoruz
        val service=retrofit.create(CryptoApi::class.java)

        val call=service.getData()
        call.enqueue(object:Callback<List<CryptoModel>> {
            override fun onResponse(
                    call: Call<List<CryptoModel>>,
                    response: Response<List<CryptoModel>>) {

                if (response.isSuccessful){
                    response.body()?.let {
                        cryptoModels=ArrayList(it)

                        cryptoModels?.let {
                            recyclerAdapter= RecyclerAdapter(it,this@MainActivity )
                            binding.recyclerView.adapter=recyclerAdapter
                        }


                        for (cryptoModel:CryptoModel in cryptoModels!!){
                            println(cryptoModel.currency)
                            println(cryptoModel.price)
                        }


                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })

         */
    }
    private fun handleResponse(cryptoList : List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            recyclerAdapter= RecyclerAdapter(it,this@MainActivity )
            binding.recyclerView.adapter=recyclerAdapter
        }
    }

    override fun onItemclick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked: ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }
    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }

}