package com.ahmetutlu.retrofitkotlin.service

import com.ahmetutlu.retrofitkotlin.model.CryptoModel
import retrofit2.Call
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

interface CryptoApi {
    //GET,POST,UPDATE,DELETE
    //API key: b619a326e28d9d8ae94d644e85f1dbd2919bfbed
    //https://api.nomics.com/v1/
    // prices?key=b619a326e28d9d8ae94d644e85f1dbd2919bfbed

    //aşağıda get diyip prices adresinden verileri getData fonku yardımıyla aldık
    @GET("prices?key=b619a326e28d9d8ae94d644e85f1dbd2919bfbed")
    fun getData():Observable<List<CryptoModel>>

    //fun getData():Call<List<CryptoModel>>//Call retrofit olcak
}