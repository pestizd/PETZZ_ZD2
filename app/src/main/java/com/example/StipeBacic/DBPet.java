package com.example.StipeBacic;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {IDPet.class}, version = 1, exportSchema = false)
public abstract class DBPet extends RoomDatabase {

    private static DBPet instance;

    public abstract query_pet biljeskeQuery();

    public static synchronized DBPet getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DBPet.class, "biljeske_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private query_pet querypet;

        private PopulateDbAsyncTask(DBPet db) {
            querypet = db.biljeskeQuery();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            querypet.insert(new IDPet("Naslov 1", "Opis 1", 1));
            querypet.insert(new IDPet("Naslov 2", "Opis 2", 2));
            querypet.insert(new IDPet("Naslov 3", "Opiss 3", 3));
            return null;
        }
    }
}
