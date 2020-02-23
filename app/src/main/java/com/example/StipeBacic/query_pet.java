package com.example.StipeBacic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface query_pet {

    @Insert
    void insert(IDPet IDPet);

    @Update
    void update(IDPet IDPet);

    @Delete
    void delete(IDPet IDPet);

    @Query("DELETE FROM IDPet")
    void deleteAllNotes();

    @Query("SELECT * FROM IDPet ORDER BY prioritet DESC")
    LiveData<List<IDPet>> getAllBiljeske();
}