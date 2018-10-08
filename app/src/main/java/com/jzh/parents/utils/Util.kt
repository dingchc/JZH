package com.jzh.parents.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Environment
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.google.gson.Gson
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.listener.IDialogCallback
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.lang.reflect.Type
import java.util.*
import java.util.regex.Pattern

/**
 * 工具类
 *
 * @author ding
 * Created by Ding on 2018/8/13.
 */
class Util {

    companion object {

        /**
         * convert dp to its equivalent px
         *
         * 将px转换为与之相等的dp
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * convert px to its equivalent dp
         *
         * 将px转换为与之相等的dp
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * convert px to its equivalent sp
         *
         * 将px转换为sp
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }


        /**
         * convert sp to its equivalent px
         *
         * 将sp转换为px
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * 获取非空字符串
         */
        fun getEmptyString(input: String?): String {

            return input ?: ""
        }

        /**
         * 是否为 MI UI
         *
         * @return true 是、 false 否
         */
        fun isMiUI(): Boolean {

            var inputStream: InputStream? = null

            try {
                val properties = Properties()
                val file = File(Environment.getRootDirectory(), "build.prop")

                inputStream = FileInputStream(file)
                properties.load(inputStream)

                val miUiVersionCode = properties.getProperty("ro.miui.ui.version.code")
                val miUiVersionName = properties.getProperty("ro.miui.ui.version.name")

                return !TextUtils.isEmpty(miUiVersionCode) && !TextUtils.isEmpty(miUiVersionName)

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }

            return false
        }

        /**
         * 获取包名
         *
         * @param context 上下文
         * @return 包名
         */
        fun getPackageName(context: Context?): String? {

            return context?.packageName
        }

        /**
         * 当授权失败后，反馈用户，是否进入设置页面
         *
         * @param title          标题
         * @param message        消息
         * @param dialogCallback 回调
         */
        fun showPermissionFailedDialog(activity: Activity, title: String, message: String, dialogCallback: IDialogCallback?) {

            AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton(R.string.confirm, DialogInterface.OnClickListener { dialog, which ->
                if (dialogCallback != null) {
                    dialogCallback.onConfirm()
                } else {
                    MPermissionUtil.goSettingAppDetail(activity)
                }
            }).setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                if (dialogCallback != null) {
                    dialogCallback.onCancel()
                }
            }).setCancelable(false).show()
        }

        /**
         * 当授权失败后，反馈用户，是否进入设置页面
         *
         * @param title 标题
         * @param message 信息
         */
        fun showOpsPermissionDenyDialog(activity: Activity, title: String, message: String) {

            AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton(R.string.confirm, DialogInterface.OnClickListener { dialog, which -> }).setCancelable(false).show()
        }

        /**
         * 检查对象是否为空
         *
         * @param obj 对象
         * @return true 不为空、 false为空
         */
        fun checkObjNotNull(obj: Any?): Boolean {

            return obj != null
        }

        /**
         * 检查list是否为空
         *
         * @param dataList 数据表
         * @return true 不为空、 false为空
         */
        fun checkListNotNull(dataList: List<*>?): Boolean {

            return dataList != null
        }

        /**
         * 检查list size是否大于0
         *
         * @param dataList 数据表
         * @return true 大于0、 false 小于等于0
         */
        fun checkListNotEmpty(dataList: List<*>): Boolean {

            return checkListNotNull(dataList) && dataList.size > 0
        }

        /**
         * 检查Map非空
         *
         * @param map 数据Map
         * @return true 非空、 false 空
         */
        fun checkMapNotNull(map: Map<*, *>?): Boolean {

            return map != null
        }

        /**
         * 检查Map size是否大于0
         *
         * @param map 数据Map
         * @return true 大于0、 false 小于等于0
         */
        fun checkMapNotEmpty(map: Map<*, *>): Boolean {

            return checkMapNotNull(map) && map.size > 0
        }

        /**
         * 检查文件是否存在
         *
         * @param filePath 文件路径
         * @return true 文件存在、 false 文件不存在
         */
        fun checkFileExist(filePath: String): Boolean {

            var ret = false
            if (!TextUtils.isEmpty(filePath)) {
                val file = File(filePath)
                ret = file.exists() && file.isFile
            }

            return ret
        }

        /**
         * 获取颜色
         *
         * @param color 颜色资源值
         * @return 颜色Int值
         */
        fun getColorCompat(color: Int): Int {
            return ContextCompat.getColor(JZHApplication.instance!!, color)

        }

        /**
         * 获取Drawable
         *
         * @param drawableId drawable资源值
         * @return drawable
         */
        fun getDrawableCompat(drawableId: Int): Drawable? {
            return ContextCompat.getDrawable(JZHApplication.instance!!, drawableId)

        }

        /**
         * 根据图片资源id，返回Drawable
         * @param resources 资源
         * @param iconIdRes 资源id
         * @return Drawable
         */
        fun getCompoundDrawable(resources: Resources, @DrawableRes iconIdRes: Int): Drawable? {

            val iconBitmap = BitmapFactory.decodeResource(resources, iconIdRes)

            var iconStarDrawable: BitmapDrawable? = null

            if (iconBitmap != null) {

                iconStarDrawable = BitmapDrawable(resources, iconBitmap)
                iconStarDrawable.setBounds(0, 0, iconBitmap.width, iconBitmap.height)
            }

            return iconStarDrawable
        }

        /**
         * 根据图片资源id，返回Drawable
         *
         * @param resources 资源
         * @param iconIdRes 资源id
         * @param targetWidth 目标宽度
         * @param targetHeight 目标高度
         * @return Drawable
         */
        fun getCompoundDrawable(context: Context, @DrawableRes iconIdRes: Int, targetWidth: Int, targetHeight: Int): Drawable? {

            val drawable = ContextCompat.getDrawable(context, iconIdRes)

            drawable!!.setBounds(0, 0, targetWidth, targetHeight)

            return drawable
        }

        /**
         * 获取年份列表
         */
        fun getLearningYears(): List<Int> {

            val calendar: Calendar = Calendar.getInstance()

            calendar.timeInMillis = System.currentTimeMillis()

            return Constants.MIN_YEAR_OF_LEANING.rangeTo(calendar.get(Calendar.YEAR)).toList()
        }

        /**
         * json转对象
         */
        fun <T> fromJson(json: String, type: Type): T {

            val gson = Gson()

            return gson.fromJson<T>(json, type)
        }

        /**
         * 对象转json
         */
        fun <T> toJson(t: T, type: Type): String {

            val gson = Gson()

            return gson.toJson(t, type)
        }

        /**
         * 检查SD卡是否可用
         *
         * @return true 存在、 false 不存在
         */
        fun checkSDExists(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /**
         * 获取App路径
         */
        fun getAppDir(): String {
            return JZHApplication.instance?.externalCacheDir.toString() + File.separator + Constants.FILE_NAME_PREFIX + File.separator
        }

        /**
         * 创建目录
         *
         * @param path 路径
         * @return true 创建成功、false 创建失败
         */

        fun mkDirs(path: String): Boolean {

            if (!TextUtils.isEmpty(path)) {

                val file = File(path)

                if (!file.exists()) {
                    return file.mkdirs()
                }
            }

            return false
        }

        /**
         * 检查手机号
         *
         * @return true 正确、false 否
         */
        fun checkPhoneNumberValid(phoneNumber: String?): Boolean {

            val pattern = Pattern.compile("1[0-9]{10}")

            return pattern.matcher(phoneNumber).find()
        }

        /**
         * SDcard是否可用
         *
         * @return true 可用、false 不可用
         */
        fun isSdcardExists(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }
    }


}