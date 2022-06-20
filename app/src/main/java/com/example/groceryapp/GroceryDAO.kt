package com.example.groceryapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GroceryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GroceryItems)

    @Delete
    suspend fun delete(item: GroceryItems)

    @Query("select * from grocery_items")
    fun getAllGroceryItems(): LiveData<List<GroceryItems>>


}
