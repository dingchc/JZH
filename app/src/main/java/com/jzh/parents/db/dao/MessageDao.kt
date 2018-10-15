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
    abstract fun insertLesson(messageEntries: List<MessageEntry>)

    @Query("select * from Message ORDER BY start_at DESC LIMIT 10")
    abstract fun loadTopLessons(): List<MessageEntry>
}