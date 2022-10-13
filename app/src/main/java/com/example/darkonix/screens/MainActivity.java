package com.example.darkonix.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.darkonix.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    ListFragment listFragment = new ListFragment();
    AddFragment addFragment = new AddFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//      Получаем объект по его id
        bottomNavigationView = findViewById(R.id.bottom_nav);

//      обращение к менеджеру смены фрагментов, запуск транзакции,
//      поменять фрагмент в активной области фрагментов
        getSupportFragmentManager().beginTransaction().replace(R.id.fragm_view,listFragment).commit();



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragm_view,listFragment).commit();
                        return true;
                    case R.id.add:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragm_view,addFragment).commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}