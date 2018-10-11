package me.paixao.atmlist.comm

import io.reactivex.Observable
import me.paixao.atmlist.data.Atm
import retrofit2.http.GET

interface AtmAPI {

    @GET("ATM_20181005_DEV.json")
    fun get(): Observable<List<Atm>>
}