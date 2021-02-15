package com.lumi.threelabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NavBottom, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_PERMISSION= 444;
    private BottomNavigationView mBottomNav;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNav = findViewById(R.id.bottomNavigationBar);
        mBottomNav.setOnNavigationItemSelectedListener(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mBottomNav.setSelectedItemId(R.id.navigation_textFile);
        obtainPermission();
    }



    private void obtainPermission() {
        if (!isExternalStorageAvailable())return;
        int permissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED || permissionWriteStorage != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_multimedia: {
                navMediaList();
                return true;
            }
            case R.id.navigation_image: {
                navImgList();
                return true;
            }
            case R.id.navigation_textFile: {
                navFileEditList();
                return true;
            }
            default: return false;
        }
    }

    @Override
    public void navMediaList() {
        navController.navigate(R.id.multimediaFragment);
    }

    @Override
    public void navImgList() {
        navController.navigate(R.id.imagesFragment);
    }

    @Override
    public void navFileEditList() {
        navController.navigate(R.id.textEditFragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_PERMISSION: {
                if (grantResults.length > 2
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    break;
                }else {
                    obtainPermission();
                    break;
                }
            }
        }
    }


    public boolean isExternalStorageAvailable(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


}