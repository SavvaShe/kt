package com.example.kval

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Presentation {

    private fun save(list: List<Data>) {
        val newList = list.map {
            DbModel(
                text = it.text
            )
        }
        db?.insert(newList)
    }

    fun get(str: String) {
        val call = service?.getData(str)
        call?.enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch {
                        save(response.body()!!)
                    }
                    view?.setResultData(response.body()!!)
                } else {
                    view?.error()
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                view?.noInternet()
            }

        })
    }

    fun getDb() {
        CoroutineScope(Dispatchers.Main).launch {
            val list: List<DbModel>?
            withContext(Dispatchers.IO) {
                list = db?.getData()
            }
            view?.setResultDb(list ?: listOf())
        }
    }

    companion object {
        private var view: IMainView? = null
        private var presentation: Presentation? = null
        private var service: ApiService? = null
        private var db: DbDao? = null

        fun getInstance(newView: IMainView, context: Context): Presentation {
            if (presentation == null) {
                presentation = Presentation()
                service = ApiService.getApi()
                db = DbClass.build(context)
            }
            view = newView
            return presentation!!
        }
    }
}