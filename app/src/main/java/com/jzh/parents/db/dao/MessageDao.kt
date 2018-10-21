package com.jzh.parents.db.dao

import android.arch.persistence.room.*
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
    fun insertMessageArray(messageEntries: Array<MessageEntry>)

    /**
     * 插入消息数据
     * @param message 消息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: MessageEntry)

    /**
     * 更新消息
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMessageArray(messageEntries: Array<MessageEntry>)

    /**
     * 刷新消息
     */
    @Query("select * from Message ORDER BY start_at DESC LIMIT 5")
    fun refreshMessages(): List<MessageEntry>

    /**
     * 加载更多消息
     */
    @Query("select * from Message where start_at < :startTime ORDER BY start_at DESC LIMIT 5")
    fun loadMoreMessages(startTime : Long): List<MessageEntry>

    /**
     * 获取未读消息数量
     */
    @Query("select * from Message where is_read = 0")
    fun loadUnreadMsg(): List<MessageEntry>
}