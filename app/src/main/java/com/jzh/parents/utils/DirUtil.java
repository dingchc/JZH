package com.jzh.parents.utils;

import android.os.Environment;

import com.jzh.parents.app.JZHApplication;

import java.io.File;

/**
 * SD卡目录创建工具类
 * Created by ding on 11/2/15.
 */
public class DirUtil {

    private static final String ROOT = "sports";

    private static final String UPLOAD = "upload";

    private static final String DOWNLOAD = "download";

    private static final String UNZIP = "unzip";

    private static final String FILE = "file";

    private static final String LOG = "log";

    private static final String IMG = "img";

    private static final String WEB_CACHE = "web_cache";

    /**
     * 获取SD卡存储根目录
     *
     * @return 路径
     */
    public static String getRootDir() {

        String filePath = JZHApplication.Companion.getInstance().getCacheDir().getPath();

        if (isExternalStorageMounted()) {
            filePath = JZHApplication.Companion.getInstance().getExternalCacheDir().getPath();
        }

        return filePath;
    }

    /**
     * 是否外部存储可用
     *
     * @return true 可用， false 不可用
     */
    private static boolean isExternalStorageMounted() {

        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡文件上传目录
     *
     * @return 路径
     */
    public static String getUploadDir() {
        String filePath = getRootDir() + File.separator + UPLOAD;

        if (!createFileDir(filePath)) {
            filePath = null;
        }

        return filePath;
    }

    /**
     * 获取SD卡文件下载目录
     *
     * @return 路径
     */
    public static String getDownloadDir() {
        String filePath = getRootDir() + File.separator + DOWNLOAD;

        if (!createFileDir(filePath)) {
            filePath = null;
        }

        return filePath;
    }

    /**
     * 获取SD卡解压目录
     *
     * @return 路径
     */
    public static String getUnZipDir() {

        String filePath = getRootDir() + File.separator + UNZIP;

        if (!createFileDir(filePath)) {
            filePath = null;
        }

        return filePath;
    }

    /**
     * 获取SD卡日志目录
     *
     * @return 路径
     */
    public static String getLogDir() {

        String filePath = getRootDir() + File.separator + LOG;

        if (!createFileDir(filePath)) {
            filePath = null;
        }

        return filePath;
    }

    /**
     * 获取SD卡图片目录
     *
     * @return 路径
     */
    public static String getImgDir() {

        String filePath = getRootDir() + File.separator + IMG;

        if (!createFileDir(filePath)) {
            filePath = null;
        }

        return filePath;
    }

    /**
     * 获取SD卡文件缓存目录
     *
     * @return 路径
     */
    public static String getFileCacheDir() {

        String filePath = getRootDir() + File.separator + FILE;

        if (!createFileDir(filePath)) {
            filePath = null;
        }

        return filePath;
    }

    /**
     * 创建文件目录
     *
     * @param filePath 文件
     * @return true 创建成功或已存在 false 创建失败
     */
    private static boolean createFileDir(String filePath) {

        boolean createSuccess = true;

        if (filePath == null) {
            return false;
        }

        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }

        File file = new File(filePath);

        AppLogger.i("filePath = " + filePath);

        if (!file.exists()) {
            createSuccess = file.mkdirs();
        }

        return createSuccess;
    }

    /**
     * 获取并创建WebView缓存目录
     *
     * @return 缓存目录
     */
    public static String getWebCacheDir() {

        String filePath = getRootDir() + File.separator + WEB_CACHE;

        if (Util.Companion.mkDirs(filePath)) {
            return filePath;
        }

        return null;
    }


    /**
     * 根据目录名字及文件名，得到正确的全路径字符串
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return 路径
     */
    public static String getValidPath(String dir, String fileName) {

        String path = null;

        if (dir == null || fileName == null) {
            return null;
        }

        if (dir.endsWith(File.separator)) {
            path = dir + fileName;
        } else {
            path = dir + File.separator + fileName;
        }

        return path;
    }


    /**
     * 清除缓存
     *
     * @return
     */
    public static boolean deleteCache() {
        File file = new File(getRootDir());
        return deleteDir(file);
    }

    private static boolean deleteDir(File dir) {
        if (!dir.exists()) {
            return true;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // 目录此时为空，可以删除
        return dir.delete();
    }


}
