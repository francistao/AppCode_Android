package com.tc.dream.books.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.model.UserGiveBook;
import com.tc.dream.books.utils.ImageTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 可交换的书界面
 * Created by dream on 15/12/14.
 */
public class ExchangeBookActivity extends Activity implements View.OnClickListener {


    private static final int TAKE_PICTURE = 0;
    private static final int CHOOSE_PICTURE = 1;

    private Button btn_add_pic;
    private EditText et_book_name;
    private EditText et_writer_name;
    private EditText et_phonenumber;
    private EditText et_tips;
    private Button btnAdd;
    private ImageView bookImage;
    private static final int SCALE = 2;//照片缩小比例
    private String picPath;
    private BmobFile bmobFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_book);
        initView();

    }

    private void initView() {
        btn_add_pic = (Button) findViewById(R.id.btn_add_pic);
        bookImage = (ImageView) findViewById(R.id.book_image);
        btn_add_pic.setOnClickListener(this);
        btnAdd = (Button) findViewById(R.id.btn_add);
        et_book_name = (EditText) findViewById(R.id.et_book_name);
        et_writer_name = (EditText) findViewById(R.id.et_writer_name);
        et_phonenumber = (EditText) findViewById(R.id.et_phone_name);
        et_tips = (EditText) findViewById(R.id.et_tips_name);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_pic:
                showPicturePicker(ExchangeBookActivity.this);
                break;
            case R.id.btn_add:
                saveUserGive();
                break;
        }
    }

    private void showPicturePicker(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("选择图片");
        builder.setNegativeButton("取消", null);
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case TAKE_PICTURE:
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                        //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                    case CHOOSE_PICTURE:
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    //将保存在本地的图片取出并缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
                    Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                    //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    bitmap.recycle();

                    //将处理过的图片显示在界面上，并保存到本地
//                    iv_image.setImageBitmap(newBitmap);
                    bookImage.setVisibility(View.VISIBLE);
                    bookImage.setImageBitmap(newBitmap);
                    btn_add_pic.setVisibility(View.GONE);
                    picPath = Environment.getExternalStorageDirectory() + "/image.jpg";

                    bmobFile = new BmobFile(new File(picPath));
                    bmobFile.upload(this, new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ExchangeBookActivity.this, "上传文件成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(ExchangeBookActivity.this, "上传文件失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                    ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
                    break;

                case CHOOSE_PICTURE:
                    ContentResolver resolver = getContentResolver();
                    //照片的原始资源地址
                    Uri originalUri = data.getData();
                    try {
                        //使用ContentProvider通过URI获取原始图片
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                            //释放原始图片占用的内存，防止out of memory异常发生
                            photo.recycle();

//                            iv_image.setImageBitmap(smallBitmap);
                            bookImage.setVisibility(View.VISIBLE);
                            bookImage.setImageBitmap(smallBitmap);
                            btn_add_pic.setVisibility(View.GONE);
                            picPath = Environment.getExternalStorageDirectory() + "/image.jpg";
                            bmobFile = new BmobFile(new File(picPath));
                            bmobFile.upload(this, new UploadFileListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(ExchangeBookActivity.this, "上传文件成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(ExchangeBookActivity.this, "上传文件失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }

    }

    private void saveUserGive() {
        UserGiveBook book = new UserGiveBook();
        BmobUser user = BmobUser.getCurrentUser(this);
        book.setUserName(user.getUsername());
        book.setBookName(et_book_name.getText().toString());
        book.setBookWriter(et_writer_name.getText().toString());
        book.setPhone(et_phonenumber.getText().toString());
        book.setTips(et_tips.getText().toString());
        book.setBookPic(bmobFile);

        book.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(ExchangeBookActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(ExchangeBookActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
