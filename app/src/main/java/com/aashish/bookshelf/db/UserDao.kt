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

    @Query("SELECT * from user_table WHERE email=:email AND password=:password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @Query("SELECT * from user_table WHERE id=:id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT email from user_table WHERE id=:id")
    suspend fun getUserEmailById(id: Int): String?

    @Query("SELECT * from user_table WHERE email=:email")
    suspend fun getUserByEmail(email: String): User?
}