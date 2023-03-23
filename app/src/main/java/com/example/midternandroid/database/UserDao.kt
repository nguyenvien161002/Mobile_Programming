package com.example.midternandroid.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.midternandroid.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM tbl_users")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM tbl_users WHERE id_user = :id")
    fun findById(id: Int): User?

    @Insert
    fun insert(student: User)

    @Delete
    fun delete(student: User)

    @Update
    fun update(student: User)

}