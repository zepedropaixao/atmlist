package me.paixao.atmlist.ui

import android.arch.lifecycle.ViewModel
import android.util.Log
import retrofit2.Retrofit
import javax.inject.Inject

class MainActViewModel: ViewModel() {

    @Inject
    lateinit var retrofit: Retrofit

    fun initViewModel() {
        // Do an asynchronous operation to fetch users.
        Log.e("TEST", "INITTING VM")
    }
}