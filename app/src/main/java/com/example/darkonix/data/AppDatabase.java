package com.example.darkonix.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.darkonix.model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // метод, который позволяет получить доступ к Dao (автоматически реализует интерфейс Dao)
    public abstract NoteDao noteDao();
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    static public AppDatabase getDatabase(Context context, String dbName) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).build();
    }
    static public AppDatabase getClearDatabase(Context context, String dbName){
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration().build();
    }
}
