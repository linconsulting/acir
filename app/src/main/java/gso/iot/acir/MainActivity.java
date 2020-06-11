package gso.iot.acir;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;
import gso.iot.acir.lib.NanoWs;

public class MainActivity extends AppCompatActivity {

    public NanoWs server = null;
    public SharedPreferences settings;
    public SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                R.id.navigation_settings, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        sharedSettingsAndListener();

        try {
            handleWs();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sharedSettingsAndListener(){
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                actionOnSharedPrefChangeWs(sharedPreferences, key);
            }
        };

        settings.registerOnSharedPreferenceChangeListener(listener);

    }

    private void actionOnSharedPrefChangeWs(SharedPreferences sharedPreferences, String key){
        if (key.equals("webServerEnabled") && (sharedPreferences.getBoolean(key, false) == true)) {
            try {
                startWs();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (key.equals("webServerEnabled") && (sharedPreferences.getBoolean(key, false) == false)) {
            stopWs();
        }
    }



    private void handleWs() throws IOException {

        if (settings.getBoolean("webServerEnabled", false)){
            startWs();
        }

    }

    private void startWs() throws IOException {

        String port = settings.getString("webServerPort","0");
        server = new NanoWs(Integer.parseInt(port));
        server.start();

    }

    private void stopWs(){
        server.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        settings.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        settings.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server == null){
            return;
        }else{
            server.stop();
        }

    }








}
