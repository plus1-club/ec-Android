package ru.electric.ec.online.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.electric.ec.online.models.Request;

@Dao
public interface RequestDao {

    @Insert
    void create(Request request);

    @Query("SELECT * FROM request")
    List<Request> readAll();

    @Query("SELECT * FROM request WHERE product LIKE :product")
    List<Request> readProduct(String product);

    @Query("SELECT * FROM request WHERE product IN(:products)")
    List<Request> readProducts(List<String> products);

    @Update
    void update(Request request);

    @Delete
    void delete(Request request);

    @Query("DELETE FROM request")
    void deleteAll();
}
