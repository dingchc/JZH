package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.SearchRepository
import com.jzh.parents.viewmodel.entity.BaseLiveEntity

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
     * 数据条目
     */
    private var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()

    /**
     * 是否点击了搜索
     */
    private var isSearchable = MutableLiveData<Boolean>()

    /**
     * 数据仓库
     */
    private var repo = SearchRepository()

    init {
        isSearchable.value = true
    }


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

    /**
     * 获取是否可以搜索
     */
    fun getIsSearchable() : MutableLiveData<Boolean> {
        return isSearchable
    }

    /**
     * 设置是否可以搜索
     */
    fun setIsSearchable(searchable : Boolean) {
        isSearchable.value = searchable
    }

    /**
     * 返回条目实体
     */
    fun getItemEntities(): MutableLiveData<MutableList<BaseLiveEntity>> {

        return itemEntities
    }

    fun loadSearchRecord() {

    }

    /**
     * 加载数据条目
     */
    fun loadItemEntitiesData() {

        itemEntities.value = repo.loadItemEntities()
    }
}