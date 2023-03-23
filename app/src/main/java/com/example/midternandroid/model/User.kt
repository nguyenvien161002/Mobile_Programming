package com.example.midternandroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_users")
data class User (
    @PrimaryKey(autoGenerate = true) val id_user: Int? = 1,
    var user_name: String,
    var user_email: String,
    var password: String,
    var confirm_password: String
)