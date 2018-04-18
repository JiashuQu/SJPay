package com.sujin.sjpay.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class CheckNetWorkUtil {

	private static CheckNetWorkUtil instance = null;
	private Handler mHandler;
	private boolean isRun = true;
	public static boolean isNotConnetion = false;
	// private int i = 0;
	private int flagToast = 0;

	private CheckNetWorkUtil() {
	}

	public static CheckNetWorkUtil getInstance() {
		if (instance == null) {
			instance = new CheckNetWorkUtil();
		}
		return instance;
	}

	public void checkNetWork(final Context context) {
		new NetWorkThread(context).start();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 200) {
					isNotConnetion = true;
					if (flagToast == 0) {
						flagToast = 1;
						Toast.makeText(context, "请确认网络已连接", Toast.LENGTH_LONG)
								.show();
					}
				} else {
					isNotConnetion = false;
				}
				super.handleMessage(msg);
			}
		};
	}

	class NetWorkThread extends Thread {

		Context context;

		public NetWorkThread(Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			super.run();
			while (isRun) {
				// i ++ ;
				try {
					Message msg = new Message();
					if (checkWeatherNetworkOk(context)) {
						msg.what = 100;
					} else {
						msg.what = 200;
					}
					mHandler.sendMessage(msg);

					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void notRun() {
		isRun = false;
	}

	private static boolean checkWeatherNetworkOk(Context context) {
		ConnectivityManager ctm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo infos = ctm.getActiveNetworkInfo();
		if (infos != null && infos.isAvailable()) {
			return true;
		}
		return false;
	}
}
