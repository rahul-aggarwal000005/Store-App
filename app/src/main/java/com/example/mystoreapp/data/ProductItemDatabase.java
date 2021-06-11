package com.example.mystoreapp.data;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ProductItem.class},version = 1,exportSchema = false)
public abstract class ProductItemDatabase extends RoomDatabase {


    /* Instance of the Dao in order to query */
    public abstract ProductItemDao productItemDao();

    /* Instance of the ProductItemDatabase in order to get a singleton }*/
    private static volatile ProductItemDatabase INSTANCE;

    /* Number of thread so that these queries are not run on the main thread */
    private static final int NUMBER_OF_THREADS = 5;

    /*
     *  use to run database operations asynchronously on a background thread.
     *  */
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    /* Get the singleton database */
    static ProductItemDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ProductItemDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,ProductItemDatabase.class,"Store").addCallback(roomCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    // below line is to create a callback for our room database.
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
           databaseWriteExecutor.execute(() -> {
                ProductItemDao productItemDao = INSTANCE.productItemDao();
           });
        }
    };


}
