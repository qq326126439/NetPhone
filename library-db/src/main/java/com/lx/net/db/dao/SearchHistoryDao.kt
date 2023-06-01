package com.lx.net.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomWarnings
import com.lx.net.db.BaseDao

/**
 * ********************************************************************
 *
 * @description:
 *
 * @author: pengl
 *
 * @created on: 2022/8/16 0016 14:40
 *
 * @version: 1.0.0
 *
 * @modify time:2022/8/16 0016 14:40
 * ********************************************************************
 */
//@Dao
//interface SearchHistoryDao : BaseDao<SearchHistoryBean> {
//
//    @Query("select * from search_history order by id desc")
//    fun getAllSearchHistory(): List<SearchHistoryBean>
//
//    @Query("delete from search_history")
//    fun delete()
//
//    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
//    @Query("select search_content from search_history where search_content = (:searchContent)")
//    fun queryBySearchContent(searchContent: String) : List<SearchHistoryBean>
//
//    @Query("delete from search_history where search_content = (:searchContent)")
//    fun deleteBySearchContent(searchContent: String)
//}