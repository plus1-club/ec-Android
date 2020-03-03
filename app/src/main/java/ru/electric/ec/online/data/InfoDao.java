package ru.electric.ec.online.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.electric.ec.online.models.Info;

@Dao
public interface InfoDao {

    @Insert
    void create(Info info);

    @Query("SELECT * FROM info")
    List<Info> readAll();

    @Query("SELECT * FROM info ORDER BY date LIMIT 1")
    Info readLast();

    @Update
    void update(Info info);

    @Delete
    void delete(Info info);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("DELETE FROM user WHERE date <= :date")
    void deleteBefore(long date);
}
