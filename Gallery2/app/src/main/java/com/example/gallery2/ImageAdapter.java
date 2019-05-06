package com.example.gallery2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_gallery, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageItem imageItem = DBContext.getInstance().getListImages().get(position);
//        Uri uri = Uri.parse(imageItem.getUri());
        try {
            byte[] bytes = imageItem.getUri().getBytes("UTF-8");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imageView.setImageBitmap(bitmap);
            Log.d("onBindViewHolder", "onBindViewHolder: ok");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("onBindViewHolder", "onBindViewHolder: failes");

        }
//        holder.imageView.setImageURI(null);
//        holder.imageView.setImageURI(Uri.parse(imageItem.getUri()));
//        Picasso.get().load(uri).placeholder(R.drawable.ic_launcher_background).fit().centerCrop()
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return DBContext.getInstance().getListImages().size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}

