package com.fbe.lib_dataitem.store

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * 用于item类型的viewModel作用域,Item销毁后ViewModel也会销毁
 */
class ItemScopeViewModelStore : ViewModelStoreOwner {
    private var viewModelStore: ViewModelStore = ViewModelStore()

    override fun getViewModelStore(): ViewModelStore {
        return viewModelStore
    }

    fun clearViewModelStore() {
        viewModelStore.clear()
    }
}