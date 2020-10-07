package com.project.firstkotlin.ui.createchat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.firstkotlin.R
import kotlinx.android.synthetic.main.activity_createchat.*
import java.util.*

class NewchatActivity : AppCompatActivity() {

    var lstUser: ArrayList<String> = ArrayList()
    var mainAdapter: CreatechatAdapter? = null

    private val newchatViewModel: NewchatViewModel by lazy {
        ViewModelProvider(this, NewchatViewModel.NewchatViewModelFactory(this.application)).get(
            NewchatViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createchat)
        initControl()
        initEvents()
    }

    private fun initControl() {
        edt_search.requestFocus()
        mainAdapter = CreatechatAdapter(this, lstUser)
        rv_search.apply {
            layoutManager =
                LinearLayoutManager(this@NewchatActivity, RecyclerView.VERTICAL, false)
            adapter = mainAdapter
        }
        loadDataFromFirebase()
    }

    private fun initEvents() {
        back_to_main.setOnClickListener { super.onBackPressed() }
    }

    private fun loadDataFromFirebase() {
        lstUser = newchatViewModel.loadDataFromFirebase()
        mainAdapter!!.notifyDataSetChanged()
        create_loading.visibility = View.GONE
    }
}