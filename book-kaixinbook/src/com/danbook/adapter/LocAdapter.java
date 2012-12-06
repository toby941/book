package com.danbook.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import nl.siegmann.epublib.domain.Resource;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.danbook.R;

public class LocAdapter extends BaseAdapter {
    private final Context mContext;
    private ArrayList<HashMap<String, Object>> listItem = null;
    protected int i;

    public LocAdapter(Context context, ArrayList<HashMap<String, Object>> listItem, int i) {
        this.mContext = context;
        this.listItem = listItem;
        this.i = i;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressWarnings("unused")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item, null);
        ImageView itemback = (ImageView) view.findViewById(R.id.itemback);
        ImageView ItemImage = (ImageView) view.findViewById(R.id.ItemImage);
        TextView bookName = (TextView) view.findViewById(R.id.bookName);
        TextView ItemTitle = (TextView) view.findViewById(R.id.ItemTitle);
        TextView ItemTitle1 = (TextView) view.findViewById(R.id.ItemTitle1);
        TextView ItemTitle2 = (TextView) view.findViewById(R.id.ItemTitle2);
        ImageView ItemImage9 = (ImageView) view.findViewById(R.id.ItemImage9);
        TextView last = (TextView) view.findViewById(R.id.last);

        final int p = position;
        ItemImage.setImageResource((Integer) (listItem.get(p).get("ItemImage")));
        if (listItem.get(p).get("itemback") != null) {
            itemback.setImageResource((Integer) (listItem.get(p).get("itemback")));
            itemback.setScaleType(ScaleType.CENTER_CROP);
        }
        if (listItem.get(p).get("epubImage") != null) {
            Bitmap coverImage = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Resource r = ((Resource) listItem.get(p).get("epubImage"));
                // 获取这个图片的宽和高
                coverImage = BitmapFactory.decodeStream(r.getInputStream(), null, options);// 此时返回bm为空
                options.inJustDecodeBounds = false;
                // 计算缩放比
                int be = (int) (options.outHeight / (float) 200);
                if (be <= 0)
                    be = 1;
                options.inSampleSize = be;
                options.inJustDecodeBounds = false;
                coverImage = BitmapFactory.decodeStream(r.getInputStream(), null, options);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (OutOfMemoryError err) {
                err.printStackTrace();
            }
            ItemImage.setImageBitmap(coverImage);
            itemback.setScaleType(ScaleType.CENTER_CROP);
        }
        bookName.setText((String) (listItem.get(p).get("BookName")));
        ItemTitle.setText((String) (listItem.get(p).get("ItemTitle")));
        ItemTitle1.setText((String) (listItem.get(p).get("ItemTitle1")));
        last.setText((String) (listItem.get(p).get("LastImage")));
        return view;
    }
}
