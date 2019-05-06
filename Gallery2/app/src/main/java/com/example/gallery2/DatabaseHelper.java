package com.example.gallery2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GalleryManager";
    private static final int DATABASE_VERSION = 1;
    private static final String IMAGE = "Image";
    private static final String TYPE = "Type";
    private static final String IMAGE_ID = "id";
    private static final String IMAGE_URI = "uri";
    private static final String IMAGE_TYPE = "type";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", IMAGE, IMAGE_ID,IMAGE_URI,IMAGE_TYPE);
        db.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
//        db.execSQL(drop_students_table);
//
//        onCreate(db);
    }
    public void addImage(ImageItem imageItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IMAGE_ID, imageItem.getId());
        values.put(IMAGE_URI, imageItem.getUri());
        values.put(IMAGE_TYPE, imageItem.getType());

        db.insert(IMAGE, null, values);
        db.close();
    }

    public void getAllImage() {
        ArrayList<ImageItem>  imageList = new ArrayList<>();
        String query = "SELECT * FROM " + IMAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            ImageItem imageItem = new ImageItem(cursor.getInt(0), cursor.getString(1),cursor.getString(2));
            imageList.add(imageItem);
            cursor.moveToNext();
        }
        Log.d("getAllImage", "getAllImage: "+imageList.size());
        DBContext.getInstance().setListImages(imageList);
    }

    public void updateImage(ImageItem imageItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMAGE_ID, imageItem.getId());
        values.put(IMAGE_URI, imageItem.getUri());
        values.put(IMAGE_TYPE, imageItem.getType());

        db.update(IMAGE, values, IMAGE_ID + " = ?", new String[] { String.valueOf(imageItem.getId()) });
        db.close();
    }

    public void deleteImage(ImageItem imageItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(IMAGE, IMAGE_ID + " = ?", new String[] { String.valueOf(imageItem.getId()) });
        db.close();
    }


}