package com.jzh.parents.db;

import com.jzh.parents.db.dao.MessageDao;
import com.jzh.parents.db.entry.MessageEntry;

import java.util.List;

/**
 * 课时数据库
 *
 * @author ding
 *         Created by ding on 09/04/2018.
 */

public class MessageDb {

    /**
     * Room数据库
     */
    private BaseRoomDatabase mDb;

    /**
     * 课时Dao
     */
    private MessageDao mDao;


    public MessageDb(BaseRoomDatabase db) {

        this.mDb = db;
        this.mDao = db.messageDao();
    }

    /**
     * 插入消息数据
     *
     * @param messageEntryList 消息数据列表
     */
    public void insert(List<MessageEntry> messageEntryList) {

        if (messageEntryList != null) {

            mDb.beginTransaction();

            MessageEntry[] entries = new MessageEntry[messageEntryList.size()];
            messageEntryList.toArray(entries);

            mDao.insertMessageArray(entries);

            mDb.setTransactionSuccessful();
            mDb.endTransaction();
        }
    }

    /**
     * 插入消息数据
     *
     * @param messageEntry 消息数据列表
     */
    public void insert(MessageEntry messageEntry) {

        if (messageEntry != null) {

            mDao.insertMessage(messageEntry);

        }
    }

    /**
     * 加载消息
     *
     * @return 消息
     */
    public List<MessageEntry> refreshMessages() {

        return mDao.refreshMessages();

    }

    /**
     * 加载消息
     *
     * @param startTime 开始时间
     * @return 消息
     */
    public List<MessageEntry> loadMoreMessages(long startTime) {

        return mDao.loadMoreMessages(startTime);

    }
}
