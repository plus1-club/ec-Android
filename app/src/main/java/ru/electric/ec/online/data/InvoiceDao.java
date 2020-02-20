package ru.electric.ec.online.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.electric.ec.online.models.Invoice;

@Dao
public interface InvoiceDao {

    @Insert
    void create(Invoice invoice);

    @Query("SELECT * FROM invoice")
    List<Invoice> readAll();

    @Query("SELECT * FROM invoice WHERE status = :status")
    List<Invoice> readByStatus(String status);

    @Query("SELECT * FROM invoice WHERE status = :status1 OR status = :status2")
    List<Invoice> readByStatus(String status1, String status2);

    @Query("SELECT * FROM invoice WHERE number = :number")
    List<Invoice> readByNumber(String number);

    @Update
    void update(Invoice invoice);

    @Delete
    void delete(Invoice invoice);

    @Query("DELETE FROM invoice")
    void deleteAll();
}

