package com.tencent.weiyun.example;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.tencent.weiyun.WeiyunDir;
import com.tencent.weiyun.WeiyunFile;
import com.tencent.weiyun.WeiyunSDK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Object> list;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public ListAdapter(Activity a, ArrayList<Object> l) {
        activity = a;
        list = l;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_dir, null);
        }

        TextView titleView = (TextView)vi.findViewById(R.id.title);
        TextView summaryView = (TextView)vi.findViewById(R.id.summary);
        TextView otherView = (TextView)vi.findViewById(R.id.other);
        ImageView imageView = (ImageView)vi.findViewById(R.id.image);


        String title;
        long time;

        Object obj = list.get(position);
        if (obj instanceof WeiyunDir) {
            WeiyunDir dir = (WeiyunDir)obj;
            title = dir.getName();
            time = dir.mtime;
            imageView.setImageResource(R.drawable.ico_folder);
        } else if (obj instanceof WeiyunFile) {
            WeiyunFile file = (WeiyunFile)obj;
            title = file.getName();
            if (file.type == WeiyunSDK.WeiyunFileTypePhoto) {
                time = file.ttime;
                imageLoader.displayImage(file.thumburl + "&size=128*128", imageView);
            } else if (file.type == WeiyunSDK.WeiyunFileTypeVideo) {
                time = file.ttime;
                imageLoader.displayImage(file.thumburl + "/128", imageView);
            } else {
                time = 0;
                imageView.setImageResource(R.drawable.ico_article);
            }
        } else {
            title = "unknow";
            time = 0;
            imageView.setImageResource(R.drawable.ico_article);
        }

        titleView.setText(title);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String str = format.format(date);
        summaryView.setText(str);
        otherView.setText("");

        return vi;
    }
}
