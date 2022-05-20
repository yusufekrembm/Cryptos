package com.yusufekrem.crypto_coins.service

import com.yusufekrem.crypto_coins.model.CryptoModel
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoAPI {
    //GET , POST,UPDATE, DELETE
//https://raw.githubusercontent.com/
// atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): retrofit2.Call<List<CryptoModel>>


// RX JAVA DİSPOSABLE
   // fun getData(): Observable<List<CryptoModel>>
}