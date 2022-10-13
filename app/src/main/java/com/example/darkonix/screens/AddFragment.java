package com.example.darkonix.screens;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.darkonix.R;
import com.example.darkonix.data.Repository;
import com.example.darkonix.enums.Importance;
import com.example.darkonix.model.Note;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class AddFragment extends Fragment {

    private static final int SELECT_PICTURE = 1;
    Spinner spinnerImportance;
    EditText editTextDate;
    EditText editTextName;
    EditText editTextPrice;
    EditText editTextLocation;
    EditText editTextWeight;
    EditText editTextQuantity;
    EditText editTextDescription;
    Button pickImage;
    Button buttonAdd;
    byte[] imgBlob = new byte[0];
    Context mContext;
    ImageView imageViewAdd;

    Repository repository;
    LiveData<List<Note>> liveData;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();
        // Находим элементы по id
        buttonAdd = view.findViewById(R.id.buttonAdd);
        pickImage = view.findViewById(R.id.pickImage);
        imageViewAdd = view.findViewById(R.id.imageViewAdd);
        spinnerImportance = view.findViewById(R.id.spinnerImportance);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextName = view.findViewById(R.id.editTextName);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        editTextWeight = view.findViewById(R.id.editTextWeight);
        editTextQuantity = view.findViewById(R.id.editTextQuantity);
        editTextDescription = view.findViewById(R.id.editTextDescription);


        repository = new Repository(getContext(), "list_note");

        // Адаптер, который устанавливает то, как будет выглядеть спиннер
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.importance_array, android.R.layout.simple_spinner_item);

        spinnerImportance.setAdapter(adapter);

        // Принимаем аргументы из bundle и устанавливаем каждый в необходимое поле
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            spinnerImportance.setSelection(bundle.getInt("kFlag"));
            editTextDate.setText(bundle.getString("kDate"));
            editTextName.setText(bundle.getString("kName"));
            editTextPrice.setText(Float.toString(bundle.getFloat("kPrice")));
            editTextLocation.setText(bundle.getString("kLocation"));
            editTextWeight.setText(bundle.getString("kWeight"));
            editTextQuantity.setText(bundle.getString("kQuantity"));
            editTextDescription.setText(bundle.getString("kDescription"));
            imgBlob = bundle.getByteArray("kImage");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bundle.getByteArray("kImage"),
                    0, bundle.getByteArray("kImage").length);
            imageViewAdd.setImageBitmap(bitmap);

        }

        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        // Функция, которая вызывается при нажатии на кнопку "Добавить"
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Получаем информацию из полей
                String textDate = editTextDate.getText().toString();
                int importance = spinnerImportance.getSelectedItemPosition();
                Importance flag;
                switch (importance) {
                    case 0:
                        flag = Importance.HIGH;
                        break;
                    case 1:
                        flag = Importance.MEDIUM;
                        break;
                    case 2:
                        flag = Importance.LOW;
                        break;
                    default:
                        flag = Importance.MEDIUM;
                        break;
                }
                String textName = editTextName.getText().toString();
                float textPrice = Float.parseFloat(editTextPrice.getText().toString());
                String textLocation = editTextLocation.getText().toString();
                String textWeight = editTextWeight.getText().toString();
                String textQuantity = editTextQuantity.getText().toString();
                String textDescription = editTextDescription.getText().toString();

                // Если bundle не пуст, значит фрагмент открыли с помощью клика на элемент списка,
                // следовательно выполнить update
                if (bundle != null) {
                    Note note = new Note(bundle.getInt("kUid"), textDate, flag, textName,
                            textPrice, textLocation, textWeight, textQuantity, textDescription, imgBlob);
                    repository.update(note);
                }
                // Если в bundle ничего нет, значит фрагмент открыли из навигационной панели,
                // следовательно выполнить else
                else {
                    Note note = new Note(0, textDate, flag, textName, textPrice, textLocation,
                            textWeight, textQuantity, textDescription, imgBlob);
                    repository.insert(note);
                }

                // Функция для перехода на ListFragment после нажатия на кнопку "Добавить"
                ListFragment listFragment = new ListFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragm_view, listFragment).commit();
            }
        });
    }

    private void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    Bitmap yourBitmap = null;
                    try {
                        yourBitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), selectedImageUri);
                        imageViewAdd.setImageBitmap(yourBitmap);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        yourBitmap.compress(Bitmap.CompressFormat.PNG, 10, bos);
                        imgBlob = bos.toByteArray();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
