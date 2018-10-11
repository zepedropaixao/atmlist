package me.paixao.atmlist.ui

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.paixao.atmlist.AppATMList
import me.paixao.atmlist.comm.AtmAPI
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class MainActViewModel: ViewModel() {

    @Inject
    lateinit var retrofit: Retrofit

    val disposables = CompositeDisposable()

    val apiService: AtmAPI

    init{
        AppATMList.netComponent.inject(this)
        apiService = retrofit.create(AtmAPI::class.java)
    }


    fun initViewModel() {
        // Do an asynchronous operation to fetch users.
        Timber.e("INITTING VM")
        val disp = apiService.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.e("I HAVE ANSWER")
                            for(item in it) {
                                Timber.e("ATM: ${item.name}")
                            }
                        },
                        Timber::e)

        disposables.add(disp)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}