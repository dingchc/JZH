package com.jzh.parents.datamodel.response

import com.jzh.parents.app.Constants
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 响应类的父类
 *
 * @author ding
 * Created by Ding on 2018/9/12.
 */
open class BaseRes(val code : Int = ResultInfo.CODE_SUCCESS, val tip : String = "") {
}