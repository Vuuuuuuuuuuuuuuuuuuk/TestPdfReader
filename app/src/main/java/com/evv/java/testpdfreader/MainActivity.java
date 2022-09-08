package com.evv.java.testpdfreader;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
  ImageView imageView;
  PdfReader reader;

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
}