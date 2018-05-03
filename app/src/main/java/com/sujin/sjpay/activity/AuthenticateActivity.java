package com.sujin.sjpay.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.sujin.sjpay.BuildConfig;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.UploadImgResponse;
import com.sujin.sjpay.util.BitmapUtil;
import com.sujin.sjpay.util.DialogUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.dialog.AuthenticateDialog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticateActivity extends BaseActivity {

    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.et_band_card_phone)
    EditText etBandCardPhone;
    @BindView(R.id.iv_take_photo_top)
    ImageView ivTakePhotoTop;
    @BindView(R.id.bt_take_photo_top)
    Button btTakePhotoTop;
    @BindView(R.id.ll_take_photo_top)
    LinearLayout llTakePhotoTop;
    @BindView(R.id.iv_take_photo_bottom)
    ImageView ivTakePhotoBottom;
    @BindView(R.id.bt_take_photo_bottom)
    Button btTakePhotoBottom;
    @BindView(R.id.ll_take_photo_bottom)
    LinearLayout llTakePhotoBottom;
    @BindView(R.id.tv_take_photo_top)
    TextView tvTakePhotoTop;
    @BindView(R.id.tv_take_photo_bottom)
    TextView tvTakePhotoBottom;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    public static int PHOTO_STEP; //上传照片步骤记录
    public static int UPLOAD_STEP; //上传照片步骤记录
    public static final int FLAG_CHOOSE_IMG = 1;
    public static final int FLAG_CHOOSE_PHONE = 2;
    private static String localTempImageFileName = StringUtil.EMPTY;
    public static final File FILE_SDCARD = Environment.getExternalStorageDirectory();
    public static final File FILE_LOCAL = new File(FILE_SDCARD, "sj_id_card_" + PHOTO_STEP);
    public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL, "images/screenshots");

    private AuthenticateDialog authenticateDialog;
    private boolean isCanUpload = false;
    private Bitmap tempBitmap;
    private Bitmap imageFrontBitmap;
    private Bitmap imageBackmap;
    private String imageFrontPath;
    private String imageBackPath;
    private File imageFront;
    private File imageBack;
    private String userId, name, idCard;
    private int IdCardPhoto = 0, IdCardBackPhoto = 0;

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if (imageFront != null) {
                        upLoad(imageFrontBitmap);
                        UPLOAD_STEP = 1;
                    }
                    break;
                case 2:
                    if (imageBack != null) {
                        upLoad(imageBackmap);
                        UPLOAD_STEP = 2;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        ButterKnife.bind(this);
        userId = SJApplication.getInstance().getUserId();
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.bt_take_photo_top, R.id.bt_take_photo_bottom, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_take_photo_top:
                PHOTO_STEP = 0;
                showChosePhoto();
                break;
            case R.id.bt_take_photo_bottom:
                PHOTO_STEP = 1;
                showChosePhoto();
                break;
            case R.id.tv_confirm:
                name = etRealName.getText().toString().trim();
                if (!StringUtil.checkName(name)){
                    return;
                }

                idCard = etBandCardPhone.getText().toString().trim().replace(" ", "");
                if (!StringUtil.isLegalIdNum(idCard)) {
                    ToastUtil.show(getResources().getString(R.string.please_input_id_card));
                    return;
                }

//                if (isCanUpload && imageBack != null && imageFront != null) {
//                    tvConfirm.setEnabled(false);
//                    upLoad(imageFrontBitmap);
//                    UPLOAD_STEP = 1;
//                } else {
//                    ToastUtil.show("请您先上传照片");
//                }
                if (IdCardBackPhoto != 0 && IdCardPhoto != 0) {
                    Intent intent = new Intent(AuthenticateActivity.this, BandCardActivity.class);
                    intent.putExtra("IdCard", idCard);
                    intent.putExtra("RealName", name);
                    intent.putExtra("IdCardPhoto", IdCardPhoto);
                    intent.putExtra("IdCardBackPhoto", IdCardBackPhoto);
                    startActivity(intent);
                } else {
                    ToastUtil.show(getResources().getString(R.string.please_ouload_photo));
                }
                break;
        }
    }

    private void showChosePhoto() {
        authenticateDialog = new AuthenticateDialog(this);
        authenticateDialog.setLiftBankCardListener(new AuthenticateDialog.LiftBankCardListener() {
            @Override
            public void pickPhoto() {
                AndPermission.with(AuthenticateActivity.this)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                goPickPhoto();
                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.show("拒绝访问将不能上传您的证件");
                    }
                }).start();
            }

            @Override
            public void takePhoto() {
                AndPermission.with(AuthenticateActivity.this)
                        .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                goTakePhoto();
                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.show("拒绝访问将不能上传您的证件");
                    }
                }).start();
            }

            @Override
            public void cancel() {
                if (authenticateDialog != null) {
                    authenticateDialog.dismiss();
                }
            }
        });

        if (authenticateDialog != null) {
            authenticateDialog.show();
        }
    }


    /**
     * 图库
     */
    private void goPickPhoto() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, FLAG_CHOOSE_IMG);
    }

    /**
     * 拍照
     */
    private void goTakePhoto() {
        try {
            //拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
            //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
            String status = Environment.getExternalStorageState();
            if (status.equals(Environment.MEDIA_MOUNTED)) {
                try {
                    localTempImageFileName = "";
                    localTempImageFileName = String
                            .valueOf((new Date()).getTime())
                            + ".jpg";
                    File filePath = FILE_PIC_SCREENSHOT;
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    Intent intent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(filePath,
                            localTempImageFileName);
                    // localTempImgDir和localTempImageFileName是自己定义的名字
//                    Uri u = Uri.fromFile(f);
                    Uri u;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        u = FileProvider.getUriForFile(AuthenticateActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                f);
                    } else {
                        u = Uri.fromFile(f);
                    }
                    intent.putExtra(
                            MediaStore.Images.Media.ORIENTATION,
                            0);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                    startActivityForResult(intent,
                            FLAG_CHOOSE_PHONE);
                } catch (ActivityNotFoundException e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        DialogUtil.showLoading(this, false);
        String path = StringUtil.EMPTY;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FLAG_CHOOSE_PHONE) { // 获取拍照图片路径
                File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);
                path = f.getAbsolutePath();
            } else if (requestCode == FLAG_CHOOSE_IMG) {// 获取相册图片路径
                if (data != null) {
                    Uri uri = data.getData();
                    if (!TextUtils.isEmpty(uri.getAuthority())) {
                        Cursor cursor = getContentResolver().query(uri,
                                new String[]{MediaStore.Images.Media.DATA},
                                null, null, null);
                        if (null == cursor) {
                            ToastUtil.show("图片没找到");
                            return;
                        }
                        cursor.moveToFirst();
                        path = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                        cursor.close();
                    } else {
                        path = uri.getPath();
                    }
                }
            }
//            DialogUtil.dismissLoading();
            if (authenticateDialog != null) {
                authenticateDialog.dismiss();
            }
            LogUtils.i("SJ", "path=" + path);
            tempBitmap = BitmapUtil.getLocalBitmap(path);
//            if (data != null) {
            if (tempBitmap != null) {
                if (PHOTO_STEP == 0) {
                    DialogUtil.showLoading(this, "上传中...",false);
                    btTakePhotoTop.setText("重新上传");
                    btTakePhotoBottom.setVisibility(View.VISIBLE);
                    ivTakePhotoTop.setImageBitmap(tempBitmap);
//                            ivTakePhotoTop.setImageURI(u);
                    imageFrontPath = path;
                    imageFrontBitmap = tempBitmap;
                    //涉及I/O操作，子线程运行
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            imageFront = null;
                            imageFront = BitmapUtil.saveBitmap(tempBitmap, getCacheDir().getAbsolutePath(), "jiebang1.png");
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }).start();
                } else if (PHOTO_STEP == 1) {
                    DialogUtil.showLoading(this, "上传中...",false);
                    btTakePhotoTop.setText("重新上传");
                    btTakePhotoBottom.setVisibility(View.VISIBLE);
                    btTakePhotoBottom.setText("重新上传");
                    ivTakePhotoBottom.setImageBitmap(tempBitmap);
                    isCanUpload = true;
                    imageBackPath = path;
                    imageBackmap = tempBitmap;
                    //涉及I/O操作，子线程运行
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            imageBack = null;
                            imageBack = BitmapUtil.saveBitmap(tempBitmap, getCacheDir().getAbsolutePath(), "jiebang2.png");
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
            }
        }
    }

    private void upLoad(Bitmap bit) {
        String imgBase64 = BitmapUtil.Bitmap2StrByBase64(bit);
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getUploadImg, RequestMethod.POST);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.UpImg, s, ApiConstants.API_UPLOAD);
        request.add("UserId", userId);
        request.add("base64", imgBase64);
        request(0, request, httpListener, md5, false, false);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {
            String registerJson = response.get();
            UploadImgResponse uploadImgResponse = getGson().fromJson(registerJson, UploadImgResponse.class);
            LogUtils.d("SJHttp", uploadImgResponse.getBackStatus());
            if (TextUtils.equals(uploadImgResponse.getBackStatus(), "0")) {
                UploadImgResponse.UploadImg data = uploadImgResponse.getData();
                int id = data.getID();
                if (UPLOAD_STEP == 1) {
                    IdCardPhoto = id;
//                    upLoad(imageBackmap);
//                    UPLOAD_STEP = 2;
                } else if (UPLOAD_STEP == 2) {
                    IdCardBackPhoto = id;
//                    Intent intent = new Intent(AuthenticateActivity.this, BandCardActivity.class);
//                    intent.putExtra("IdCard", idCard);
//                    intent.putExtra("RealName", name);
//                    intent.putExtra("IdCardPhoto", IdCardPhoto);
//                    intent.putExtra("IdCardBackPhoto", IdCardBackPhoto);
//                    startActivity(intent);
//                    tvConfirm.setEnabled(true);
//                    DialogUtil.dismissLoading();
//                    finish();
                }
            }else {
                ToastUtil.show(uploadImgResponse.getMessage());
            }
            DialogUtil.dismissLoading();
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            tvConfirm.setEnabled(true);
            DialogUtil.dismissLoading();
            LogUtils.d("SJHttp", json);
        }
    };
}
