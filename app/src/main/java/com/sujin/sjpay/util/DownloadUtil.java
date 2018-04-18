package com.sujin.sjpay.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.sujin.sjpay.R;
import com.sujin.sjpay.android.SJApplication;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;

import java.io.File;
import java.util.Locale;

/**
 * Created by QJS on 2016/8/1.
 */
public class DownloadUtil {

    /**
     * 下载进度条.
     */
    private String APP_PATH_ROOT = FileUtil.getRootPath(SJApplication.getInstance()).getAbsolutePath();

    private Context context;
    private ProgressDialog mypDialog;
    private int progress;
    private boolean canCancel;
    /**
     * 下载请求.
     */
    private DownloadRequest mDownloadRequest;
    /**
     * 开始下载。
     */
    public void download(Context context, String downloadUrl, String fileName, boolean canCancel) {
        this.context = context;
        this.canCancel = canCancel;
        // 开始下载了，但是任务没有完成，代表正在下载，那么暂停下载。
        if (mDownloadRequest != null && mDownloadRequest.isStarted() && !mDownloadRequest.isFinished()) {
            // 暂停下载。
            mDownloadRequest.cancel();
        } else if (mDownloadRequest == null || mDownloadRequest.isFinished()) {// 没有开始或者下载完成了，就重新下载。

            mDownloadRequest = new DownloadRequest(downloadUrl, RequestMethod.GET,
                    APP_PATH_ROOT,
                    true, true);

            // what 区分下载。
            // downloadRequest 下载请求对象。
            // downloadListener 下载监听。
            DownloadQueue downloadQueue = NoHttp.newDownloadQueue(3);
            downloadQueue.add(0, mDownloadRequest, downloadListener);
        }
    }

    /**
     * 下载监听
     */
    private DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            mypDialog = new ProgressDialog(context);
            // 实例化
            mypDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // 设置进度条风格，风格为长形，有刻度的
            mypDialog.setTitle("下载");
            // 设置ProgressDialog 标题
            mypDialog.setMessage("下载中...");
            // 设置ProgressDialog 进度条进度
            mypDialog.setProgress(0);
            // 设置ProgressDialog 的进度条是否不明确
            mypDialog.setIndeterminate(false);
            // 设置ProgressDialog 是否可以按退回按键取消
            mypDialog.setCancelable(false);
            // 设置ProgressDialog 的一个Button
            if (canCancel) {
                mypDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mDownloadRequest != null) {
                            mDownloadRequest.cancel();
                        }
                        dialog.dismiss();
                    }
                });
            }
            // 让ProgressDialog显示
            mypDialog.show();

            if (allCount != 0) {
                progress = (int) (beforeLength * 100 / allCount);
                mypDialog.setProgress(progress);
            }

        }

        @Override
        public void onDownloadError(int what, Exception exception) {
            Logger.e(exception);
            Resources resources = context.getResources();
            String message = resources.getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = resources.getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = resources.getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = resources.getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = resources.getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = resources.getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = resources.getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = resources.getString(R.string.download_error_url);
            } else {
                messageContent = resources.getString(R.string.download_error_un);
            }
            message = String.format(Locale.getDefault(), message, messageContent);
            ToastUtil.show(message);
        }

        @Override
        public void onProgress(int what, int progress, long fileCount, long speed) {
            mypDialog.setProgress(progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
            Logger.d("Download finish, file path: " + filePath);
            mypDialog.dismiss();
            ToastUtil.show(context.getResources().getString(R.string.download_status_finish));
            openFile(filePath);
        }

        @Override
        public void onCancel(int what) {
            ToastUtil.show(context.getResources().getString(R.string.download_status_be_pause));
        }
    };

    /**
     * 打开下载文件
     *
     * @param filePath
     */
    private void openFile(String filePath) {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 适配 N 7.0
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(filePath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(filePath)),
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 保留打开程序的界面
        }

        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        context.startActivity(intent);
    }

}
