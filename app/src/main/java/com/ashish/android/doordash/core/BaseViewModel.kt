package com.ashish.android.doordash.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    var compositeDisposable = CompositeDisposable()
    protected var errorMutableLiveData = MutableLiveData<String>()
    val errorLiveData:LiveData<String> = errorMutableLiveData

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}