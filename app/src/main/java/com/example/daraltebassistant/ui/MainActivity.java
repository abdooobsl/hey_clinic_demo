package com.example.daraltebassistant.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.util.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        pref = getApplicationContext().getSharedPreferences("UserData", 0);
        editor= pref.edit();
        if(Common.currentUser!=null){
            editor.putString("user_id", Common.currentUser.user_id);
            editor.putString("user_fullname", Common.currentUser.user_fullname);
            editor.putString("user_email", Common.currentUser.user_email);
            editor.putString("user_phone", Common.currentUser.user_phone);
            editor.apply();}
    }

}
