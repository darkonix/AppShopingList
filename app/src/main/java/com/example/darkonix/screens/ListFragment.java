package com.example.darkonix.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.darkonix.Adapter;
import com.example.darkonix.R;
import com.example.darkonix.data.Repository;
import com.example.darkonix.enums.Importance;
import com.example.darkonix.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ListFragment extends Fragment {

    Repository repository;
    LiveData<List<Note>> liveData;
    AddFragment addFragment = new AddFragment();

    ListView listView;
    View mView;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new Repository(view.getContext(), "list_note");
        liveData = repository.getAllNotes();
        Log.d("meow", liveData.toString());
        mView = view;

        liveData.observe(getViewLifecycleOwner(), new Observer<List<Note>>() {

            @Override
            public void onChanged(List<Note> notes) {
                populateListView(new ArrayList<>(notes));

            }
        });
        listView = view.findViewById(R.id.list_view);

        // Метод, который вызывается, когда нажимается элемент списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Создаём bundle, и отправляем в него необходимые данные
                Bundle bundle = new Bundle();
                Note note = Objects.requireNonNull(liveData.getValue()).get(i);
                bundle.putInt("kUid", note.uid);
                bundle.putString("kDate", note.date);
                bundle.putInt("kFlag", note.flag.ordinal());
                bundle.putString("kName", note.name);
                bundle.putFloat("kPrice", note.price);
                bundle.putString("kLocation", note.location);
                bundle.putString("kWeight", note.weight);
                bundle.putString("kQuantity", note.quantity);
                bundle.putString("kDescription", note.description);
                bundle.putByteArray("kImage", note.image);
                // Устанавливаем аргументы в addFragment
                addFragment.setArguments(bundle);
                // Меняем текущий фрагмент на addFragment
                getParentFragmentManager().beginTransaction().replace(R.id.fragm_view, addFragment).commit();
            }
        });

        List<Note> tmp = liveData.getValue();
        if (tmp != null)
            populateListView(new ArrayList<>(tmp));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void populateListView(ArrayList<Note> notes) {
//        Сортировать здесь
        Adapter adapter = new Adapter(mView.getContext(), R.layout.item_note_list, notes, repository);
        listView.setAdapter(adapter);
    }
}