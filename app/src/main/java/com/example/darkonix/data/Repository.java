package com.example.darkonix.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.darkonix.enums.Importance;
import com.example.darkonix.model.Note;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Repository {

    private  final NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes = new MutableLiveData<>();

    public Repository(Context context, String dbName) {
        AppDatabase appDatabase = AppDatabase.getClearDatabase(context, dbName);
        this.mNoteDao = appDatabase.noteDao();
        this.mAllNotes = mNoteDao.getAllLiveData();
    }

    public void insert(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> mNoteDao.insert(note));
    }

    public void update(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> mNoteDao.update(note));
    }

    public void delete(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> mNoteDao.delete(note));
    }

    public List<Note> getNoteByFlag(Importance importance) {
        Future<List<Note>> future = AppDatabase.databaseWriteExecutor.submit(()
                -> mNoteDao.findByFlag(importance));
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }
}
