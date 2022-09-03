package com.evv.java.testpdfreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfReader {

  Context context;
  ImageView imageView;
  PdfRenderer pdfRenderer;
  PdfRenderer.Page pdfPage;

  PdfReader(Context context, ImageView imageView){
    this.context = context;
    this.imageView = imageView;
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
    pdfPage = pdfRenderer.openPage(1);

    Bitmap bitmap = Bitmap.createBitmap(pdfPage.getWidth(), pdfPage.getHeight(), Bitmap.Config.ARGB_8888);
    pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
    imageView.setImageBitmap(bitmap);

    pdfPage.close();
  }

  void close(){
    pdfRenderer.close();
  }
}
