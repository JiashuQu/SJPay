package com.sujin.sjpay.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sujin.sjpay.BuildConfig;
import com.sujin.sjpay.R;
import com.sujin.sjpay.activity.AboutUsActivity;
import com.sujin.sjpay.activity.AuthenticateActivity;
import com.sujin.sjpay.activity.ChangePasswordActivity;
import com.sujin.sjpay.activity.ChoseBankCardActivity;
import com.sujin.sjpay.activity.FeeInfoActivity;
import com.sujin.sjpay.activity.GetVipActivity;
import com.sujin.sjpay.activity.InviteActivity;
import com.sujin.sjpay.activity.InviteIncomeActivity;
import com.sujin.sjpay.activity.MyAccountActivity;
import com.sujin.sjpay.activity.MyInfoActivity;
import com.sujin.sjpay.activity.PayListActivity;
import com.sujin.sjpay.activity.WebActivity;
import com.sujin.sjpay.android.ApiConstants;
import com.sujin.sjpay.android.AppConstants;
import com.sujin.sjpay.android.SJApplication;
import com.sujin.sjpay.nohttp.HttpListener;
import com.sujin.sjpay.protocol.MyInfoResponse;
import com.sujin.sjpay.protocol.RegisterResponse;
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
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment extends BaseFragment {
    private static final int WHAT_MY_INFO = 0;
    private static final int WHAT_UPLOAD = 1;
    private static final int WHAT_SET_ICON = 2;

    Unbinder unbinder;
    @BindView(R.id.tv_my_secret)
    TextView tvMySecret;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.tv_my_credit_card)
    TextView tvMyCreditCard;
    @BindView(R.id.srl_my)
    SmartRefreshLayout srlMy;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_get_vip)
    TextView tvGetVip;
    @BindView(R.id.ll_my_info)
    LinearLayout llMyInfo;
    @BindView(R.id.ll_contact_us)
    LinearLayout llContactUs;
    @BindView(R.id.tv_fee_info)
    TextView tvFeeInfo;
    @BindView(R.id.tv_my_account)
    TextView tvMyAccount;
    @BindView(R.id.tv_invite)
    TextView tvInvite;
    @BindView(R.id.tv_invite_income)
    TextView tvInviteIncome;
    @BindView(R.id.tv_guide_user)
    TextView tvGuideUser;
    @BindView(R.id.tv_contact_us)
    TextView tvContactUs;
    @BindView(R.id.tv_vip_type)
    TextView tvVipType;
    @BindView(R.id.tv_invite_by)
    TextView tvInviteBy;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.iv_right_arrow)
    ImageView ivRightArrow;

    private String userId;
    private boolean isJump = true;
    private AuthenticateDialog authenticateDialog;
    public static final int FLAG_CHOOSE_IMG = 1;
    public static final int FLAG_CHOOSE_PHONE = 2;
    private static String localTempImageFileName = StringUtil.EMPTY;
    public static final File FILE_SDCARD = Environment.getExternalStorageDirectory();
    public static final File FILE_LOCAL = new File(FILE_SDCARD, "user_icon");
    public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL, "images/screenshots");
    private Bitmap tempBitmap;
    private File iocnFile;

    public MyFragment() {
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (iocnFile != null) {
                        Glide.with(getActivity()).asBitmap().load(tempBitmap).apply(RequestOptions.circleCropTransform()).into(ivUserIcon);
                        upLoad(tempBitmap);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        this.userId = SJApplication.getInstance().getUserId();
        MyInfoResponse.DataBean user = SJApplication.getInstance().getUser();
        vipType(user.getVipType());
        initView(user);
        srlMy.setRefreshHeader(new MaterialHeader(getContext()));
        srlMy.setEnableLoadMore(false);
        srlMy.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isJump = false;
                getMyInfo(MyFragment.this.userId, false);
            }
        });
        return view;
    }

    private void initView(MyInfoResponse.DataBean user) {
        if (user == null) {
            return;
        }
        String realName = user.getRealName();
        String mobile = user.getMobile();
        int isRealState = user.getIsRealState();
        String vipTypeTxt = user.getVipTypeTxt();
        String baseUserName = user.getBaseUserName();
        tvUserName.setText(realName);
        tvUserPhone.setText(mobile);
        tvVipType.setText(vipTypeTxt);
        tvInviteBy.setText("邀请人：" + baseUserName);
        Glide.with(getActivity()).asBitmap().load(user.getAvatarImg()).apply(RequestOptions.circleCropTransform()).into(ivUserIcon);
        Drawable certified = getResources().getDrawable(R.drawable.icon_certified);
        certified.setBounds(0, 0, certified.getMinimumWidth(), certified.getMinimumHeight());
        Drawable noCertified = getResources().getDrawable(R.drawable.icon_no_certified);
        noCertified.setBounds(0, 0, noCertified.getMinimumWidth(), noCertified.getMinimumHeight());

        if (isRealState == 1) {
            tvUserName.setCompoundDrawables(null, null, certified, null);
        } else {
            tvUserName.setCompoundDrawables(null, null, noCertified, null);
        }

    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_my_secret, R.id.tv_about_us, R.id.tv_my_credit_card,
            R.id.tv_fee_info, R.id.tv_invite_income, R.id.tv_invite, R.id.tv_get_vip, R.id.tv_my_account,
            R.id.iv_user_icon, R.id.iv_right_arrow, R.id.tv_user_phone, R.id.ll_fee_info, R.id.tv_pay_list,
            R.id.tv_guide_user, R.id.ll_contact_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                showChosePhoto();
                break;
            case R.id.iv_right_arrow:
                isJump = true;
                getMyInfo(userId, true);
                break;
            case R.id.tv_user_phone:
                isJump = true;
                getMyInfo(userId, true);
                break;
            case R.id.tv_user_name:
                isJump = true;
                getMyInfo(userId, true);
                break;
            case R.id.tv_my_credit_card:
                Intent intent = new Intent(getActivity(), ChoseBankCardActivity.class);
                intent.putExtra("title", "支付卡管理");
                startActivity(intent);
                break;
            case R.id.tv_my_secret:
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                break;
            case R.id.tv_pay_list:
                startActivity(new Intent(getActivity(), PayListActivity.class));
                break;
            case R.id.tv_about_us:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.tv_fee_info:
                startActivity(new Intent(getActivity(), FeeInfoActivity.class));
                break;
            case R.id.tv_invite_income:
                startActivity(new Intent(getActivity(), InviteIncomeActivity.class));
                break;
            case R.id.tv_invite:
                startActivity(new Intent(getActivity(), InviteActivity.class));
                break;
            case R.id.tv_get_vip:
                startActivity(new Intent(getActivity(), GetVipActivity.class));
                break;
            case R.id.tv_my_account:
                startActivity(new Intent(getActivity(), MyAccountActivity.class));
                break;
            case R.id.tv_guide_user:
                Intent userGuide = new Intent(getActivity(), WebActivity.class);
                userGuide.putExtra("payUrl", "https://mp.weixin.qq.com/s/wPYnEFtQZOuWYnERcsQfGQ");
                userGuide.putExtra("title", "新手指引");
                startActivity(userGuide);
                break;
            case R.id.ll_contact_us:
                ClipboardManager copy = (ClipboardManager) getActivity()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText("SJpay-op");
                ToastUtil.show("已复制微信客服号");
                break;
        }
    }

    private void showChosePhoto() {
        authenticateDialog = new AuthenticateDialog(getActivity());
        authenticateDialog.setLiftBankCardListener(new AuthenticateDialog.LiftBankCardListener() {
            @Override
            public void pickPhoto() {
                AndPermission.with(getActivity())
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                goPickPhoto();
                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.show("拒绝访问将不能上传您的头像");
                    }
                }).start();
            }

            @Override
            public void takePhoto() {
                AndPermission.with(getActivity())
                        .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                goTakePhoto();
                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.show("拒绝访问将不能上传您的头像");
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
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(filePath,
                            localTempImageFileName);
                    Uri u;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        u = FileProvider.getUriForFile(getActivity(),
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
                        Cursor cursor = getActivity().getContentResolver().query(uri,
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
                //涉及I/O操作，子线程运行
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        iocnFile = BitmapUtil.saveBitmap(tempBitmap, getActivity().getCacheDir().getAbsolutePath(), "userIcon.png");
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();
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
        request(WHAT_UPLOAD, request, httpListener, md5, false, true);
        com.lidroid.xutils.util.LogUtils.d("UserId=" + userId);
    }

    private void SetUserIcon(String userId, String id) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.setAvatarImg, RequestMethod.GET);
        char[] chars = ("UserId=" + userId + "&ImgId=" + id).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.AvatarImg, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        request.add("ImgId", id);
        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---" + md5);
        request(WHAT_SET_ICON, request, httpListener, md5, true, true);
    }

    private void getMyInfo(String userId, boolean isLoading) {
        Request<String> request = NoHttp.createStringRequest(ApiConstants.getSingleInfo, RequestMethod.GET);
        char[] chars = ("UserId=" + userId).toCharArray();
        String s = StringUtil.sort(chars);
        String md5 = StringUtil.MD5(ApiConstants.Single, s, ApiConstants.API_USERS);
        request.add("UserId", userId);
        com.lidroid.xutils.util.LogUtils.d(userId + "---" + s + "---" + md5);
        request(WHAT_MY_INFO, request, httpListener, md5, true, isLoading);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case WHAT_MY_INFO:
                    String infoJson = response.get();
                    MyInfoResponse myInfoResponse = getGson().fromJson(infoJson, MyInfoResponse.class);
                    LogUtils.d("SJHttp", myInfoResponse.getBackStatus() + "");
                    if (myInfoResponse.getBackStatus() == 0) {
                        MyInfoResponse.DataBean data = myInfoResponse.getData();
                        int isRealState = data.getIsRealState();
                        int vipType = data.getVipType();
                        vipType(vipType);
                        if (isJump) {
                            if (isRealState == 0) {
                                // 去认证
                                Intent intent = new Intent(getActivity(), AuthenticateActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                                intent.putExtra("myInfo", myInfoResponse);
                                startActivity(intent);
                            }
                        }
                        SharedPreferences.Editor spUserInfo = getActivity().getSharedPreferences(AppConstants.SP_NAME_USER_INFO, MODE_PRIVATE).edit();
                        spUserInfo.putInt(AppConstants.SP_DATA_IS_REAL_STATE, isRealState);
                        spUserInfo.commit();
                    } else {
                        ToastUtil.show(myInfoResponse.getMessage());
                    }
                    srlMy.finishRefresh(2000, true);
                    break;
                case WHAT_UPLOAD:
                    String uploadJson = response.get();
                    UploadImgResponse uploadImgResponse = getGson().fromJson(uploadJson, UploadImgResponse.class);
                    LogUtils.d("SJHttp", uploadImgResponse.getBackStatus());
                    if (TextUtils.equals(uploadImgResponse.getBackStatus(), "0")) {
                        UploadImgResponse.UploadImg data = uploadImgResponse.getData();
                        int id = data.getID();
                        SetUserIcon(userId, id + "");
                    } else {
                        ToastUtil.show(uploadImgResponse.getMessage());
                    }
                    DialogUtil.dismissLoading();
                    break;
                case WHAT_SET_ICON:
                    String registerJson = response.get();
                    RegisterResponse registerResponse = getGson().fromJson(registerJson, RegisterResponse.class);
                    LogUtils.d("SJHttp", registerResponse.getBackStatus());
                    if (TextUtils.equals(registerResponse.getBackStatus(), "0")) {
                        ToastUtil.show(registerResponse.getMessage());
                    } else {
                        ToastUtil.show(registerResponse.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            String json = response.get();
            srlMy.finishRefresh(2000, false);
            LogUtils.d("SJHttp", json);
        }
    };

    private void vipType(int vipType) {
        if (vipType == 0 || vipType == 1) {
            setGetVip("", false);
        } else if (vipType == 2) {
            setGetVip("我要做顶级代理", false);
        } else if (vipType == 3) {
            setGetVip("点击升级会员", true);
        } else if (vipType == 4) {
            setGetVip(getResources().getString(R.string.get_svip), true);
        }
    }

    /**
     * 出来获取会员的显示
     *
     * @param isShowGetVip
     */
    private void setGetVip(String tip, boolean isShowGetVip) {
        tvGetVip.setText(tip);
        if (!isShowGetVip) {
            tvGetVip.setVisibility(View.GONE);
        }
    }
}
