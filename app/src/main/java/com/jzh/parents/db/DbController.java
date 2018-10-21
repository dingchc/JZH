package com.jzh.parents.db;

import android.arch.persistence.room.Room;

import com.jzh.parents.app.JZHApplication;
import com.jzh.parents.utils.PreferenceUtil;

/**
 * 数据库操作类
 *
 * @author ding
 *         Created by ding on 2/20/17.
 */

public class DbController {

    public static DbController INSTANCE = new DbController();

    /**
     * 数据库
     */
    private BaseRoomDatabase db;

    /**
     * 消息DB
     */
    private MessageDb messageDb;


    private DbController() {
        init();
    }

    private void init() {

        initDb();

        messageDb = new MessageDb(db);
    }

    /**
     * 用户注销等，需要reset
     */
    public void reset() {
        init();
    }

    /**
     * 不同用户需要创建不同的db存储文件
     */
    public void initDb() {

        String composeDbName = getComposeDbName();
        if (db != null && !db.getOpenHelper().getDatabaseName().equals(composeDbName)) {
            db = null;
        }

        //

        JZHApplication app = JZHApplication.Companion.getInstance();

        //.addMigrations(BaseRoomDatabase.migrations)
        if (app != null) {
            db = Room.databaseBuilder(app, BaseRoomDatabase.class, composeDbName).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
    }

    /**
     * 根据用户id，构造用户db名称
     *
     * @return db名称
     */
    private String getComposeDbName() {

        return PreferenceUtil.Companion.getInstance().getCurrentUserId() + ".db";
    }

    /**
     * 获取课程DB操作
     *
     * @return 课程DB
     */
    public MessageDb getMessageDb() {
        return messageDb;
    }

    public void closeDb() {

        if (db != null) {
            db.close();
        }
    }
}
