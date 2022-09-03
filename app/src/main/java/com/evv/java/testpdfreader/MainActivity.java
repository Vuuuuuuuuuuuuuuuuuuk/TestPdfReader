package com.evv.java.testpdfreader;

import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  ImageView imageView;
  PdfReader reader;

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
  }

  @Override
  protected void onStop() {
    reader.close();
    super.onStop();
  }
}