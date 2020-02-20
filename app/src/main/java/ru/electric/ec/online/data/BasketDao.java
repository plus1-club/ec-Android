package ru.electric.ec.online.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.electric.ec.online.models.Basket;

@Dao
public interface BasketDao {

    @Insert
    void create(Basket basket);

    @Query("SELECT * FROM basket")
    List<Basket> readAll();

    @Update
    void update(Basket basket);

    @Delete
    void delete(Basket basket);

    @Query("DELETE FROM basket")
    void deleteAll();
}
