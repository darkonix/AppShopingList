package com.example.darkonix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.darkonix.data.Repository;
import com.example.darkonix.model.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Adapter extends ArrayAdapter<Note> {

    private Context mContext;
    int mResource;
    private final LayoutInflater layoutInflater;
    Repository mRepository;

    public Adapter(@NonNull Context context, int resource, ArrayList<Note> objects, Repository repository) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

        mRepository = repository;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView flagTV = convertView.findViewById(R.id.flagTV);
        TextView dateTV = convertView.findViewById(R.id.dateTV);
        TextView nameTV = convertView.findViewById(R.id.nameTV);
        TextView priceTV = convertView.findViewById(R.id.priceTV);
        TextView weightTV = convertView.findViewById(R.id.weightTV);
        TextView quantityTV = convertView.findViewById(R.id.quantityTV);
        ImageView deleteIB = convertView.findViewById(R.id.delete_item);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        Note note = getItem(position);
        switch (note.flag){
            case HIGH:
                flagTV.setText("Флаг: "+"Высокая");
                break;
            case MEDIUM:
                flagTV.setText("Флаг: "+"Средняя");
                break;
            case LOW:
                flagTV.setText("Флаг: "+"Низкая");
                break;
        }
        dateTV.setText("Дата: "+note.date);
        nameTV.setText("Имя: "+note.name);
        priceTV.setText("Цена: "+Float.toString(note.price));
        weightTV.setText("Вес: "+note.weight);
        quantityTV.setText("Кол-во: "+note.quantity);
        // Условие проверки наличия изображения
        if (!Arrays.equals(note.image, new byte[0])){
            imageView.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(note.image, 0, note.image.length);
            imageView.setImageBitmap(bitmap);
        }
        deleteIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepository.delete(note);
            }
        });

        return convertView;
    }
}
