package com.example.helloworld.crop;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.helloworld.COM.MyApp;
import com.example.helloworld.COM.com;
import com.example.helloworld.R;
import com.example.helloworld.optionframe.OptionBottomDialog;
import com.example.helloworld.setting.AccountInforActivity;
import com.kevin.crop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ChangePictureActivity extends Activity {
    private ImageView imageView;
    private static final int REQUEST_SELECT_PICTURE = 0x01;
    // 剪切后图像文件
    private Uri mDestinationUri;
    private Button cropBtn;
    private ImageView imageIv;
    public Bitmap bitmp;
    public String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepicture);
        MyApp.getAppManager().addActivity(this);


        mDestinationUri = Uri.fromFile(new File(getCacheDir(), "cropImage.jpeg"));
        initView();

        cropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_change:
                        pickFromGallery();
                        break;
                }
            }
        });


    }
    private void initView() {
        cropBtn = (Button) this.findViewById(R.id.btn_change);
        imageIv = (ImageView) this.findViewById(R.id.userpicture);
    }
    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    Log.d("ChooseFile","File Uri:" + selectedUri.toString());
                    try {
                        path = getPath(this,selectedUri);
                        Log.d("ChooseFile","File Path: "+path);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(ChangePictureActivity.this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
    }

    /**
     * 开始剪切图片
     * @param uri
     */
    private void startCropActivity(Uri uri) {
        UCrop.of(uri, mDestinationUri)
                .withTargetActivity(WeiChatCropActivity.class)
                .withAspectRatio(1, 1)
//                .withMaxResultSize(500, 500)
                .start(ChangePictureActivity.this);

    }

    /**
     * 处理剪切后的返回值
     * @param result
     */
    private void handleCropResult(Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
//            imageIv.setImageURI(resultUri);
            Bitmap bmp;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                imageIv.setImageBitmap(bmp);
                bitmp = bmp;
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        } else {
            Toast.makeText(ChangePictureActivity.this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }
    public void changePictureDialog(View view) {
        List<String> stringList = new ArrayList<String>();
        stringList.add("拍照");
        stringList.add("从相册选择");
        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(ChangePictureActivity.this, stringList);
        optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                optionBottomDialog.dismiss();
            }
        });
    }
    public void toAccountInfor(View view){
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(ChangePictureActivity.this,AccountInforActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void toAccountInfor2(View view) throws InterruptedException {
        getImage();
        Intent intent = new Intent();
        Bundle bundle = this.getIntent().getExtras();
        intent.setClass(ChangePictureActivity.this,AccountInforActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void getImage() throws InterruptedException {
        System.out.println("图片路径"+path);
        final String imageByte = path;
        //System.out.println(imageByte);
        Bundle bundle = this.getIntent().getExtras();
        final String userid = bundle.getString("username");

        Thread storeRunable = new Thread(){
            @Override
            public void run(){
                super.run();
                Connection con = null;
                try {
                    con = com.getCon();
                    String sql = "update user set image='"+imageByte+"' where user_id="+userid;
                    Statement st = (Statement) con.createStatement();
                    int result = st.executeUpdate(sql);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        storeRunable.start();
        storeRunable.join();
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

}
