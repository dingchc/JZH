package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.SearchRepository
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.ResultInfo

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
    var searchingContent = MutableLiveData<String>()

    /**
     * 数据条目
     */
    var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()

    /**
     * 是否点击了搜索
     */
    var isSearchable = MutableLiveData<Boolean>()

    /**
     * 数据仓库
     */
    private var repo = SearchRepository()

    init {
        isSearchable.value = true

        resultInfo.value = ResultInfo()
    }

    /**
     * 根据关键词搜索
     */
    fun searchLives(keyWord: String) {

        repo.refreshLives(keyWord, itemEntities, resultInfo)
    }

    /**
     * 加载数据
     */
    fun fetchHotWord() {

        return repo.fetchHotWord(resultInfo)
    }

    /**
     * 加载本地搜索关键字
     */
    fun loadHistoryKeyWord(): List<String>? {

        return repo.loadHistoryKeyWord()
    }

    /**
     * 加载本地搜索关键字
     */
    fun saveKeyWord(keyWord: String) {

        repo.saveKeyWord(keyWord)
    }
}