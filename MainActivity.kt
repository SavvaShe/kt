package com.example.kval

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), IMainView {

    private var presentation: Presentation? = null
    private var adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presentation = Presentation.getInstance(this, this)
        initRecyclerView()
        getLast()
        findViewById<Button>(R.id.button).setOnClickListener {
            val a = findViewById<EditText>(R.id.editText).text.toString()
            if (a != "")
                presentation?.get(a)
            else
                Toast.makeText(this, "Пустая строка", Toast.LENGTH_LONG).show()
        }
    }

    private fun getLast() {
        if (adapter?.list?.isEmpty() == true)
            presentation?.getDb()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        recyclerView.adapter = adapter
    }

    override fun noInternet() {
        Toast.makeText(this, "Нет интернет соединения", Toast.LENGTH_LONG).show()
    }

    override fun error() {
        Toast.makeText(this, "Произошла ошибка, запрос неверен", Toast.LENGTH_LONG).show()
    }

    override fun setResultData(list: List<Data>) {
        adapter?.list = list
    }

    override fun setResultDb(list: List<DbModel>) {
        val newList = list.map {
            Data(
                text = it.text
            )
        }
        adapter?.list = newList
    }
}