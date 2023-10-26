package com.aashish.bookshelf.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aashish.bookshelf.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun register(user: User)

    @Query("SELECT * from user_table WHERE username=:username AND password=:password")
    suspend fun getUserByNameAndPassword(username: String, password: String): User?

    @Query("SELECT * from user_table WHERE id=:id")
    suspend fun getUserById(id: String): User?
}