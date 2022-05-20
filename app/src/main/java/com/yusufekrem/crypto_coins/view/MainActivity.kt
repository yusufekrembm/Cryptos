package com.yusufekrem.crypto_coins.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yusufekrem.crypto_coins.R
import com.yusufekrem.crypto_coins.adapter.CryptoAdapter
import com.yusufekrem.crypto_coins.databinding.ActivityMainBinding
import com.yusufekrem.crypto_coins.model.CryptoModel
import com.yusufekrem.crypto_coins.service.CryptoAPI
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {

    private val baseUrl = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter: CryptoAdapter? = null

    //Disposable
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadData()
        compositeDisposable = CompositeDisposable()
    }

    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
   //         .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) --> RXJAVA DİSPOSABLE
            .build()

// RX JAVA DİSPOSABLE
//        compositeDisposable?.add(retrofit.getData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe(this::handleResponse))

        val service = retrofit.create(CryptoAPI::class.java)

        val call = service.getData()
        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        cryptoModels?.let{
                            recyclerViewAdapter = CryptoAdapter(it,this@MainActivity)
                            recyclerView.adapter = recyclerViewAdapter
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }


        })
    }

//    private fun handleResponse(cryptoList : List<CryptoModel>){
//        cryptoModels = ArrayList(cryptoList)
//
//        cryptoModels?.let {
//            recyclerViewAdapter = CryptoAdapter(it,this@MainActivity)
//            binding.recyclerView.adapter = recyclerViewAdapter
//        }
//    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Clicked : ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        compositeDisposable?.clear()
//    }
}