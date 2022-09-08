package com.evv.java.testpdfreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfReader {

  Context context;
  ImageView imageView;
  PdfRenderer pdfRenderer;
  PdfRenderer.Page pdfPage;
  int w, h, page;
  float zoom;

  PdfReader(Context context, ImageView imageView){
    this.context = context;
    this.imageView = imageView;

    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    w = display.getWidth();
    h = display.getHeight();
    page = 0;
    zoom = 1.0f;
  }

  void createFromRaw(int id) throws IOException{
    File fileCopy = new File(context.getCacheDir(), "sample.pdf");
    copyToCache(fileCopy, context.getResources().openRawResource(id));
    pdfRenderer = new PdfRenderer(ParcelFileDescriptor.open(fileCopy, ParcelFileDescriptor.MODE_READ_ONLY));
  }

  void createFromAssets(String nameOfFile) throws IOException{
    File fileCopy = new File(context.getCacheDir(), "sample.pdf");
    copyToCache(fileCopy, context.getAssets().open(nameOfFile));
    pdfRenderer = new PdfRenderer(ParcelFileDescriptor.open(fileCopy, ParcelFileDescriptor.MODE_READ_ONLY));
  }

  void copyToCache(File file, InputStream input) throws IOException {
    if(!file.exists()){
      FileOutputStream output = new FileOutputStream(file);
      byte[] buffer = new byte[1024];

      for(int size; (size = input.read(buffer)) != -1;)
        output.write(buffer, 0, size);

      input.close();
      output.close();
    }
  }

  void showPage(){
    pdfPage = pdfRenderer.openPage(page);

    Bitmap bitmap = Bitmap.createBitmap((int)(w*zoom),(int)(h*zoom), Bitmap.Config.ARGB_8888);
    pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
    imageView.setImageBitmap(bitmap);

    pdfPage.close();
  }

  void close(){
    pdfRenderer.close();
  }

  public void changePage(int i) {
    page += i;
    if(page < 0) page = 0;
    else if(page >= pdfRenderer.getPageCount()) page = pdfRenderer.getPageCount() - 1;

    //if(page + i >= 0 && page + i < pdfRenderer.getPageCount()) page += i;
  }

  public void changeZoom(float v) {
    if(zoom + v >= 0.4f && zoom + v < 2.1) zoom += v;
  }
}
