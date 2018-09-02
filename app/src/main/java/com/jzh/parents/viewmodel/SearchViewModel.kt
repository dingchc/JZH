package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData

/**
 * 搜索ViewModel
 *
 * @author ding
 * Created by Ding on 2018/9/2.
 */
class SearchViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 正在搜索的字符串
     */
    private var searchingContent = MutableLiveData<String>()

    /**
     * 获取搜索的字符串的LiveData
     */
    fun getSearchingContent(): MutableLiveData<String> {
        return searchingContent
    }

    /**
     * 设置搜索的字符串的LiveData
     */
    fun setSearchingContent(input: String) {

        return searchingContent.postValue(input)
    }
}