package com.sujin.sjpay.activity;

import android.Manifest;
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
import android.util.Log;
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
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.PayCardListResponse;
import com.sujin.sjpay.protocol.UploadImgResponse;
import com.sujin.sjpay.util.BitmapUtil;
import com.sujin.sjpay.util.DialogUtil;
import com.sujin.sjpay.util.StringUtil;
import com.sujin.sjpay.util.ToastUtil;
import com.sujin.sjpay.view.BankCellView;
import com.sujin.sjpay.view.dialog.AuthenticateDialog;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BandCardActivity extends BaseActivity {

    @BindView(R.id.et_bank_card_number)
    EditText etBankCardNumber;
    @BindView(R.id.bankcell_band_card)
    BankCellView bankcellBandCard;
    @BindView(R.id.tv_handle_address)
    TextView tvHandleAddress;
    @BindView(R.id.ll_handle_address)
    LinearLayout llHandleAddress;
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

    private String province, city, cityCode, bankId, bankName, singleQuota, dayQuota, mouthQuota, bankCardNumber, idCard, realName;
    private AuthenticateDialog authenticateDialog;
    private boolean isCanUpload = false;
    private Bitmap tempBitmap;
    private Bitmap imageFrontBitmap;
    private Bitmap imageBackmap;
    private String imageFrontPath;
    private String imageBackPath;
    private int BankCardPhoto = 0;
    private int PersonPhoto;
    private int idCardPhoto;
    private int idCardBackPhoto;
    private String userId;
    private File imageFront;
    private File imageBack;

    private Serializable serializableExtra;

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (imageFront != null) {
                upLoad(imageFrontBitmap);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_card);
        ButterKnife.bind(this);
        userId = SJApplication.getInstance().getUserId();
        Intent intent = getIntent();
        idCard = intent.getStringExtra("IdCard");
        realName = intent.getStringExtra("RealName");
        idCardPhoto = intent.getIntExtra("IdCardPhoto", -1);
        idCardBackPhoto = intent.getIntExtra("IdCardBackPhoto", -1);
        initView();
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.bankcell_band_card, R.id.ll_handle_address, R.id.bt_take_photo_top, R.id.bt_take_photo_bottom, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bankcell_band_card:
                Intent intent = new Intent(this, SelectBankActivity.class);
                startActivityForResult(intent, AppConstants.INTENT_REQUEST_CODE_FROM_BAND_CARD);
                break;
            case R.id.ll_handle_address:
                Intent selectAddressIntent = new Intent(this, SelectAddressActivity.class);
                startActivityForResult(selectAddressIntent, AppConstants.INTENT_REQUEST_CODE_REFRESH);
                break;
            case R.id.bt_take_photo_top:
                PHOTO_STEP = 0;
                showChosePhoto();
                break;
            case R.id.bt_take_photo_bottom:
                PHOTO_STEP = 1;
                showChosePhoto();
                break;
            case R.id.tv_confirm:
                bankCardNumber = etBankCardNumber.getText().toString().trim();
                if (!StringUtil.checkBankCardNumber(bankCardNumber)){
                    return;
                }

                if (serializableExtra == null){
                    ToastUtil.show("请您选择开户行");
                    return;
                }
                if (BankCardPhoto != 0) {
//                    tvConfirm.setEnabled(false);
//                    upLoad(imageFrontBitmap);
                    DialogUtil.showLoading(this, false);
                    yeePayRegister(userId, idCard, realName, bankName, bankCardNumber, cityCode, BankCardPhoto, idCardPhoto, idCardBackPhoto, 0);
                } else {
                    ToastUtil.show("请您先上传照片");
                }
                break;
        }
    }

    private void showChosePhoto() {
        authenticateDialog = new AuthenticateDialog(this);
        authenticateDialog.setLiftBankCardListener(new AuthenticateDialog.LiftBankCardListener() {
            @Override
            public void pickPhoto() {
                AndPermission.with(BandCardActivity.this)
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
                AndPermission.with(BandCardActivity.this)
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
                        u = FileProvider.getUriForFile(BandCardActivity.this,
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

    /**
     * 注册易宝商户
     * @param userId
     * @param idCard
     * @param realName
     * @param bankName
     * @param bankCardNumber
     * @param cityCode
     * @param bankCardPhoto
     * @param idCardPhoto
     * @param idCardBackPhoto
     * @param personPhoto
     */
    private void yeePayRegister(String userId, String idCard, String realName, String bankName, String bankCardNumber, String cityCode, int bankCardPhoto, int idCardPhoto, int idCardBackPhoto, int personPhoto) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getYeePayRegister, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&IdCard=" + idCard + "&RealName=" + realName + "&BankName=" + bankName
                + "&BankAccountNumber=" + bankCardNumber + "&AreaCode=" + cityCode + "&BankCardPhoto=" + bankCardPhoto
                + "&IdCardPhoto=" + idCardPhoto + "&IdCardBackPhoto=" + idCardBackPhoto + "&PersonPhoto=" + personPhoto).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.Register, s, ApiConstants.API_YEEPAY);
        request.add("UserId", userId);
        request.add("IdCard", idCard);
        request.add("RealName", realName);
        request.add("BankName", bankName);
        request.add("BankAccountNumber", bankCardNumber);
        request.add("AreaCode", cityCode);
        request.add("BankCardPhoto", bankCardPhoto);
        request.add("IdCardPhoto", idCardPhoto);
        request.add("IdCardBackPhoto", idCardBackPhoto);
        request.add("PersonPhoto", personPhoto);
        com.lidroid.xutils.util.LogUtils.d("---" + s + "---"  + md5);
        request(1, request, httpListener, md5, false, false);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case 0:
                    String registerJson = response.get();
                    UploadImgResponse uploadImgResponse = getGson().fromJson(registerJson, UploadImgResponse.class);
                    LogUtils.d("SJHttp", uploadImgResponse.getBackStatus());
                    if (TextUtils.equals(uploadImgResponse.getBackStatus(), "0")) {
                        UploadImgResponse.UploadImg data = uploadImgResponse.getData();
                        int id = data.getID();
//                        if (UPLOAD_STEP == 1) {
//                            UPLOAD_STEP = 0;
                            BankCardPhoto = id;
//                            upLoad(imageBackmap);
//                            UPLOAD_STEP = 2;
//                        } else if (UPLOAD_STEP == 2) {
//                            UPLOAD_STEP = 0;
//                            PersonPhoto = id;

//                        }
                    } else {
                        ToastUtil.show(uploadImgResponse.getMessage());
                    }
                    DialogUtil.dismissLoading();
                    break;
                case 1:
                    String yeePayRegisterJson = response.get();
                    UploadImgResponse yeePayRegister = getGson().fromJson(yeePayRegisterJson, UploadImgResponse.class);
                    LogUtils.d("SJHttp", yeePayRegister.getBackStatus());
                    if (TextUtils.equals(yeePayRegister.getBackStatus(), "0")) {
                        ToastUtil.show("实名认证成功");
//                        startActivity(new Intent(BandCardActivity.this, MyInfoActivity.class));
                        finish();
                    } else {
                        ToastUtil.show(yeePayRegister.getMessage());
                    }
                    tvConfirm.setEnabled(true);
                    DialogUtil.dismissLoading();
                    break;
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            tvConfirm.setEnabled(true);
            DialogUtil.dismissLoading();
            LogUtils.d("SJHttp", getResources().getString(R.string.net_error));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = StringUtil.EMPTY;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.INTENT_REQUEST_CODE_REFRESH:
                    if (data != null) {
                        city = data.getStringExtra(AppConstants.INTENT_KEY_CITY);
                        cityCode = data.getStringExtra(AppConstants.INTENT_KEY_CITY_CODE);
                        province = data.getStringExtra(AppConstants.INTENT_KEY_PROVINCE);
                        tvHandleAddress.setText(province + " " + city);
                        tvHandleAddress.setTextColor(getResources().getColor(R.color.black_262626));
//                        canConfirm();
                    }
                    break;
                case AppConstants.INTENT_REQUEST_CODE_FROM_BAND_CARD:
//                    Serializable serializableExtra = data.getSerializableExtra(AppConstants.INTENT_KEY_BANK_CELL);

                    serializableExtra = data.getSerializableExtra(AppConstants.INTENT_KEY_BANK_CELL);
                    if (serializableExtra != null) {
                        PayCardListResponse.DataBean bankCell = (PayCardListResponse.DataBean) serializableExtra;
                        bankName = bankCell.getBankName();
                        bankcellBandCard.setData(bankName, "", "");
                    }

//                    if (serializableExtra != null) {
//                        GetPayBankQuotaList bankCell = (GetPayBankQuotaList) serializableExtra;
//                        String bankId = bankCell.getId();
//                        bankName = bankCell.getBankName();
//                        singleQuota = bankCell.getSingleQuota();
//                        dayQuota = bankCell.getDayQuota();
//                        dayQuota = bankCell.getMouthQuota();
//                        Resources resources = getResources();
//                    }
                    break;
                case FLAG_CHOOSE_PHONE:
                    File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);
                    path = f.getAbsolutePath();
                    setBit(path);
                    break;
                case FLAG_CHOOSE_IMG:
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
                    setBit(path);
                    break;
            }
        }
    }

    private void setBit(String path){
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
                isCanUpload = true;
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
            }
        }
    }
}
