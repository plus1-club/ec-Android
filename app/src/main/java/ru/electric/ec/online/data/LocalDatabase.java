package ru.electric.ec.online.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.electric.ec.online.models.Basket;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.models.User;

@Database(version = 3, exportSchema = false,
          entities = {User.class, Request.class, Basket.class, Invoice.class, Detail.class})
public abstract class LocalDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract RequestDao requestDao();
    public abstract BasketDao basketDao();
    public abstract InvoiceDao invoiceDao();
    public abstract DetailDao detailDao();

    static LocalDatabase mInstance;

    public static LocalDatabase getLocalDatabase(Context context) {
        if (context.getApplicationContext() == null){
            return null;
        }
        if (mInstance == null) {
            mInstance = Room
                .databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "db")
                .fallbackToDestructiveMigration()
                .build();
        } else {
            return null;
        }
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }
}
