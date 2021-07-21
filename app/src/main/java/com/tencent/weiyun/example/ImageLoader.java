package com.tencent.weiyun.example;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.test.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    FileCache fileCache;
    MemoryCache memoryCache = new MemoryCache();
    Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;

    public ImageLoader(Context context){
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    public void displayImage(String url, ImageView imageView)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            loadImage(url, imageView);
            imageView.setImageResource(R.drawable.ico_article);
        }
    }

    private void loadImage(String url, ImageView imageView)
    {
        LoadingImage loadingImage = new LoadingImage(url, imageView);
        executorService.submit(new PhotosLoader(loadingImage));
    }

    private Bitmap getBitmap(String url)
    {
        File file = fileCache.getFile(url);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        if (bitmap != null) {
            return bitmap;
        } else {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setInstanceFollowRedirects(true);
                InputStream is = conn.getInputStream();
                OutputStream os = new FileOutputStream(file);
                Utils.copyStream(is, os);
                os.close();
                is.close();
                bitmap = BitmapFactory.decodeFile(file.getPath());
                return bitmap;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    private class LoadingImage
    {
        public String url;
        public ImageView imageView;
        public LoadingImage(String s, ImageView i) {
            url = s;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        LoadingImage loadingImage;
        PhotosLoader(LoadingImage loadingImage){
            this.loadingImage = loadingImage;
        }

        @Override
        public void run() {
            if (imageViewReused(loadingImage)) {
                return;
            }

            Bitmap bmp = getBitmap(loadingImage.url);
            memoryCache.put(loadingImage.url, bmp);

            if (imageViewReused(loadingImage)) {
                return;
            }

            BitmapDisplayer displayer = new BitmapDisplayer(bmp, loadingImage);
            Activity activity = (Activity)loadingImage.imageView.getContext();
            activity.runOnUiThread(displayer);
        }
    }

    boolean imageViewReused(LoadingImage loadingImage)
    {
        String tag = imageViews.get(loadingImage.imageView);
        if (tag == null || !tag.equals(loadingImage.url)) {
            return true;
        }
        return false;
    }

    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        LoadingImage loadingImage;
        public BitmapDisplayer(Bitmap b, LoadingImage l){
            bitmap = b;
            loadingImage = l;
        }

        public void run() {
            if (imageViewReused(loadingImage)) {
                return;
            }

            if (bitmap != null) {
                loadingImage.imageView.setImageBitmap(bitmap);
            } else {
                loadingImage.imageView.setImageResource(R.drawable.ico_article);
            }
        }
    }

    public void clearCache()
    {
        memoryCache.clear();
        fileCache.clear();
    }
}
