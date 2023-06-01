package com.lx.net.db

import androidx.room.*

/***********************************************************************
 * <p>@description: 操作数据库dao base类
 * <p>@author: pengl
 * <p>@created on: 2022/8/8 0008 9:53
 * <p>@version: 1.0.0
 * <p>@modify time:2022/8/8 0008 9:53
 **********************************************************************/
@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: T)

    @Delete
    fun delete(item: T)

    @Delete
    fun deleteList(elements: List<T>)

    @Delete
    fun deleteSome(vararg elements: T)

    @Update
    fun update(item: T)

}