package com.ashish.android.doordash.core.viewmodel

import androidx.lifecycle.ViewModel
import com.ashish.android.doordash.core.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    var compositeDisposable = CompositeDisposable()
    protected var errorMutableLiveData = SingleLiveEvent<String>()
    val errorLiveData: SingleLiveEvent<String> = errorMutableLiveData

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}