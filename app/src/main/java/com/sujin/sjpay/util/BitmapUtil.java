package com.sujin.sjpay.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.android.sohu.sdk.common.toolbox.LogUtils;
import com.bumptech.glide.Glide;
import com.sujin.sjpay.R;
import com.sujin.sjpay.android.ApiConstants;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by czb on 2016/7/26.
 */
public class BitmapUtil {
    /**
     * 显示图片，先显示默认图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showImage(Context context, String url, ImageView imageView) {
        showImageCustom(context, url, R.drawable.icon_default_white, imageView);
    }

    /**
     * 显示图片，自己设定默认图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void showImageCustom(Context context, String url, @DrawableRes int placeholder, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
        LogUtils.d("SJPIC", "图片url = " + (!TextUtils.isEmpty(url) ? url : "url为空"));
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(url, options);
            //计算采样率
            options.inSampleSize = calculateInSampleSize(options, 768, 1024);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(url, options);
            //旋转图片
            int d = getPictureDegree(url);
            if (d != 0) {
                bitmap = rotateBitmap(bitmap, d);
            }
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将文件生成Drawable
     *
     * @param path
     * @return
     */
    public static Drawable getDrawable(String path) {
        Drawable bd = BitmapDrawable.createFromPath(path);
        return bd;
    }

    /**
     * 返回Bitmap显示像素所占位数
     *
     * @param config
     * @return
     */
    public static int getBytesPerPixel(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        } else if (config == Bitmap.Config.RGB_565) {
            return 2;
        } else if (config == Bitmap.Config.ARGB_4444) {
            return 2;
        } else if (config == Bitmap.Config.ALPHA_8) {
            return 1;
        }
        return 1;
    }

    /**
     * 获取图片旋转角度
     *
     * @param path
     * @return
     */
    public static int getPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 保存Bitmap为文件
     *
     * @param bitmap
     * @param path
     * @param fileName
     * @return
     */
    public static File saveBitmap(Bitmap bitmap, String path, String fileName) {
        FileOutputStream outputStream = null;
        try {
            String filePath = path + File.separator + fileName;
            File file = new File(filePath);
            file.createNewFile();
            if (file.exists()) {
                file.delete();
            }
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    public static String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos;
//        bit.compress(Bitmap.CompressFormat.JPEG, 50, bos);//参数100表示不压缩
        bos = compressImage(bit);
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 质量压缩方法
     * @param image
     * @return
     */
    public static ByteArrayOutputStream compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 64) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        com.lidroid.xutils.util.LogUtils.d("baos=" + baos.toByteArray().length);
        return baos;
    }

}
