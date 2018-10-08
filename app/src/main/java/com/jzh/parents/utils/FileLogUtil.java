package com.jzh.parents.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log写入到sd卡
 * Created by ding on 5/10/16.
 */
public class FileLogUtil {

    private final static String SUFFIX = ".txt";

    /**
     * 产生Log日志目录
     * @return 路径
     */
    private static String getLogDirectory() {

        String dir = DirUtil.getLogDir() + File.separator;

        File file = new File(dir);

        file.mkdirs();

        return dir;
    }

    /**
     * 获取今天的日期字符串
     * @return
     */
    private static String getCurrentDay() {

        SimpleDateFormat ymdDateFormat = new SimpleDateFormat("yyyyMMdd");

        String currentDay = "";

        currentDay = ymdDateFormat.format(new Date());

        return currentDay;
    }

    /**
     * 如果文件已存在就打开，如果未存在就生成一个新的文件
     *
     * @return 文件
     */
    private static File createLogFile() {

        File file = null;

        try {

            String fileName = getLogDirectory() + getCurrentDay() + SUFFIX;

            file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 获取下Log发生的时间点
     *
     * @return 返回时间点字符串
     */
    private static String getTimeline() {

        String timeline = "";

        try {

            SimpleDateFormat timelineFormat = new SimpleDateFormat("MM-dd hh:mm:ss.SSS ");

            timeline = timelineFormat.format(new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return timeline;
    }

    /**
     * 清除多余log文件
     */
    private static void clearMoreFile() {

        String logDirectory = getLogDirectory();

        final File file = new File(logDirectory);

        String[] logFiles = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {

                if (!TextUtils.isEmpty(filename) && filename.endsWith(SUFFIX) && !filename.contains(getCurrentDay())) {
                    return true;
                }
                return false;
            }
        });

        if (logFiles != null && logFiles.length >= 5) {

            for (String fileName : logFiles) {

                File willDelFile = new File(logDirectory + fileName);

                if (willDelFile != null && willDelFile.exists()) {
                    willDelFile.delete();
                }
            }

        }
    }

    /**
     * 写日志
     * @param input
     */
    public static void writeLogToFile(String input) {

        try {

            clearMoreFile();

            File file = createLogFile();

            if (TextUtils.isEmpty(input)) {
                AppLogger.i(AppLogger.TAG, "input is null");
                return;
            }

            if (!Util.Companion.isSdcardExists()) {
                AppLogger.i(AppLogger.TAG, "sdcard is not useful");
                return;
            }

            if (file == null) {
                AppLogger.i(AppLogger.TAG, "log file is null");
                return;
            }

            String outputString = getTimeline().concat(input).concat("\n");

            FileOutputStream fos = new FileOutputStream(file, true);

            fos.write(outputString.getBytes("utf-8"));

            fos.flush();

            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
