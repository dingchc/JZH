package com.jzh.parents.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jzh.parents.db.entry.MessageEntry

/**
 * 消息Dao
 *
 * @author ding
 * Created by Ding on 2018/10/14.
 */
@Dao
interface MessageDao {

    /**
     * 批量插入消息数据
     * @param messageEntries 消息列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMessageArray(messageEntries: Array<MessageEntry>)

    /**
     * 插入消息数据
     * @param message 消息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMessage(message: MessageEntry)

    /**
     * 刷新消息
     */
    @Query("select * from Message ORDER BY start_at DESC LIMIT 5")
    abstract fun refreshMessages(): List<MessageEntry>

    /**
     * 加载更多消息
     */
    @Query("select * from Message where start_at < :startTime ORDER BY start_at DESC LIMIT 5")
    abstract fun loadMoreMessages(startTime : Long): List<MessageEntry>
}