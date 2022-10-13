package com.example.darkonix.model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.darkonix.enums.Importance;

import java.util.Objects;

// класс, описывающий модель записи
// анотация Entity для того, чтобы Room могла использовать класс Note
@Entity
public class Note {

    // анотация для уникального ключа
    @PrimaryKey(autoGenerate = true)
    public int uid;

    // анотация для создания колонки с указанным именем
    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "flag")
    public Importance flag;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public Float price;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "weight")
    public String weight;

    @ColumnInfo(name = "quantity")
    public String quantity;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "image")
    public byte[] image;


    public Note(int uid, String date, Importance flag, String name, Float price, String location,
                String weight, String quantity, String description, byte[] image) {
        this.uid = uid;
        this.date = date;
        this.flag = flag;
        this.name = name;
        this.price = price;
        this.location = location;
        this.weight = weight;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
    }

    // метод equals для сравнения объектов
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return uid == note.uid && Objects.equals(date, note.date) && flag == note.flag &&
                Objects.equals(name, note.name) && Objects.equals(price, note.price) &&
                Objects.equals(location, note.location) && Objects.equals(weight, note.weight) &&
                Objects.equals(quantity, note.quantity) && Objects.equals(description, note.description);
    }

    // метод hashCode для сравнения по хэшу
    @Override
    public int hashCode() {
        return Objects.hash(uid, date, flag, name, price, location, weight, quantity, description);
    }

}