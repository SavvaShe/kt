package com.example.kval

interface IMainView {

    fun noInternet()
    fun error()
    fun setResultData(list : List<Data>)
    fun setResultDb(list: List<DbModel>)
}