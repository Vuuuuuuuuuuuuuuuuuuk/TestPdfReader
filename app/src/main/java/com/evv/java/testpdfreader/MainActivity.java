package com.evv.java.testpdfreader;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
  ImageView imageView;
  PdfReader reader;
  HorizontalScrollView horizontalScrollView;

  Button btnpp, btnpm, btnzp, btnzm;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    imageView = findViewById(R.id.imageView);
    reader = new PdfReader(this, imageView);

    try {


      reader.createFromAssets("e_maxx_algo.pdf");
    } catch (IOException e) {
      e.printStackTrace();
    }

    reader.showPage();

    btnpm = findViewById(R.id.buttonPm);
    btnpp = findViewById(R.id.buttonPp);
    btnzm = findViewById(R.id.buttonZm);
    btnzp = findViewById(R.id.buttonZp);

    btnpm.setOnClickListener(this);
    btnpp.setOnClickListener(this);
    btnzm.setOnClickListener(this);
    btnzp.setOnClickListener(this);

    checkPermission();
  }


  @Override
  protected void onStop() {
    reader.close();
    super.onStop();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.buttonPm:
        reader.changePage(-1);
        break;
      case R.id.buttonPp:
        reader.changePage(+1);
        break;
      case R.id.buttonZm:
        reader.changeZoom(-0.2f);
        break;
      case R.id.buttonZp:
        reader.changeZoom(+0.2f);
        break;
    }

    reader.showPage();
  }


  private static final int REQUEST_CODE_READ_FROM_STORAGE = 18;
  void checkPermission(){
    if (ContextCompat.checkSelfPermission(
      this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
      PackageManager.PERMISSION_GRANTED) {
      // You can use the API that requires the permission.
      //performAction(...);
    } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE )) {
      // In an educational UI, explain to the user why your app requires this
      // permission for a specific feature to behave as expected. In this UI,
      // include a "cancel" or "no thanks" button that allows the user to
      // continue using your app without granting the permission.
      //showInContextUI(...);
    } else {
      // You can directly ask for the permission.
      ActivityCompat.requestPermissions(this,
        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
        REQUEST_CODE_READ_FROM_STORAGE);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case REQUEST_CODE_READ_FROM_STORAGE:
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 &&
          grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // Permission is granted. Continue the action or workflow
          // in your app.
        }  else {
          Toast.makeText(this, "Deny...", Toast.LENGTH_SHORT).show();
          // Explain to the user that the feature is unavailable because
          // the features requires a permission that the user has denied.
          // At the same time, respect the user's decision. Don't link to
          // system settings in an effort to convince the user to change
          // their decision.
        }
        return;
    }
    // Other 'case' lines to check for other
    // permissions this app might request.
  }
}