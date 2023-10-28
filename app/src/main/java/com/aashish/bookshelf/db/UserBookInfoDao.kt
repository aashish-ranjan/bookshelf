package com.aashish.bookshelf.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aashish.bookshelf.model.UserBookInfo

@Dao
interface UserBookInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userBookInfo: UserBookInfo)

    @Update
    suspend fun update(userBookInfo: UserBookInfo)

    @Query("SELECT * FROM user_book_info_table WHERE userId = :userId AND bookId = :bookId")
    suspend fun getSavedBookInfo(userId: Long, bookId: String): UserBookInfo?

}