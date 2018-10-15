package com.jzh.parents.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.jzh.parents.db.dao.MessageDao;
import com.jzh.parents.db.entry.MessageEntry;

/**
 * Room数据库
 *
 * @author ding
 *         Created by ding on 24/02/2018.
 */
@Database(entities = {MessageEntry.class}, version = 1, exportSchema = false)
public abstract class BaseRoomDatabase extends RoomDatabase {

    /**
     * 消息Dao
     *
     * @return 消息Dao
     */
    public abstract MessageDao messageDao();

    /**
     * 版本迭代
     * 注意：增加新表，需要自己写create table
     */
    public static Migration[] migrations = {};


}