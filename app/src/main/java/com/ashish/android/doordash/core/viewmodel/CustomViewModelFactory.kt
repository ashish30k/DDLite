package com.ashish.android.doordash.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class CustomViewModelFactory<T : ViewModel>
@Inject constructor(
    private val viewModel: Lazy<T>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        viewModel as T
}