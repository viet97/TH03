package com.example.gallery2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class AddActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_GALLERY = 100;
    private static final int PICK_IMAGE = 1;
    ImageView choosenImage;
    Button takeImage;
    Button submit;
    EditText type;

    String uri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        type = findViewById(R.id.image_type);
        takeImage = findViewById(R.id.take_image);
        submit = findViewById(R.id.submit);
        choosenImage = findViewById(R.id.choosen_image);

        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("getImage", "getImage: ");
//                ActivityCompat.requestPermissions(
//                        AddActivity.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        REQUEST_CODE_GALLERY
//                );
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!type.getText().toString().equals("") && !uri.equals("")) {
                    DatabaseHelper db = new DatabaseHelper(AddActivity.this);
                    try {
                        db.addImage(new ImageItem(DBContext.getInstance().getListImages().size()+1, new String(imageViewToByte(choosenImage),"UTF-8"), type.getText().toString()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    db.getAllImage();
                }
            }
        });
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            this.uri = uri.toString();
            Log.d("onActivityResult", "onActivityResult: " + uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                choosenImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
