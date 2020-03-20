package ru.electric.ec.online.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.electric.ec.online.models.Search;

@Dao
public interface SearchDao {

    @Insert
    void create(Search search);

    @Query("SELECT * FROM search")
    List<Search> readAll();

    @Query("SELECT * FROM search WHERE product LIKE :product")
    List<Search> readProduct(String product);

    @Query("SELECT * FROM search WHERE product IN(:products)")
    List<Search> readProducts(List<String> products);

    @Update
    void update(Search search);

    @Delete
    void delete(Search search);

    @Query("DELETE FROM search")
    void deleteAll();
}
