package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * ViewModel基类
 * @author ding
 * Created by Ding on 2018/8/20.
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    /**
     * 状态返回及信息
     */
    open val resultInfo: MutableLiveData<ResultInfo> = MutableLiveData<ResultInfo>()

}