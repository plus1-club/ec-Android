package ru.electric.ec.online.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.electric.ec.online.models.Detail;

@Dao
public interface DetailDao {

    @Insert
    void create(Detail detail);

    @Query("SELECT * FROM detail")
    List<Detail> readAll();

    @Query("SELECT * FROM detail WHERE number = :number")
    List<Detail> readByNumber(String number);

    @Update
    void update(Detail detail);

    @Delete
    void delete(Detail detail);

    @Query("DELETE FROM detail")
    void deleteAll();
}