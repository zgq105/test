package com.tencent.weiyun.example;


import android.content.Context;

import java.io.File;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context)
    {
        cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url)
    {
        String filename = String.valueOf(url.hashCode());
        File file = new File(cacheDir, filename);
        return file;
    }

    public void clear()
    {
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return;
        }

        for (File f : files) {
            f.delete();
        }
    }

}
