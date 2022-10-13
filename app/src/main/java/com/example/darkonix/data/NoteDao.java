package com.example.darkonix.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.darkonix.model.Note;
import com.example.darkonix.enums.Importance;

import java.util.List;

// data access object объект, через который осуществляется доступ к таблице бд: запись, чтение и тд
@Dao
public interface NoteDao {

    // анотация для запросов к бд, реализуем выборку
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    // используем LiveData
    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getAllLiveData();

    // реализация выборки по flag для сортировки
    @Query("SELECT * FROM Note WHERE flag = :searchFlag")
    List<Note> findByFlag(Importance searchFlag);

    // анотация для вставки в бд
    // можно вставить сущность с уже существующим id, она заменит предыдущую
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    // анотация для обновления бд
    @Update
    void update(Note note);

    // анотация для удаления из бд
    @Delete
    void delete(Note note);
}
