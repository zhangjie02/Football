package com.example.dianjinwebnoreward;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bodong.dianjinweb.DianJinPlatform;
import com.bodong.dianjinweb.a.ba;
import com.bodong.dianjinweb.banner.DianJinBanner;
import com.bodong.dianjinweb.banner.DianJinMiniBanner;
import com.bodong.dianjinweb.listener.ChannelListener;

public class MainActivity extends Activity {

	private TextView mChannelTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		DianJinPlatform.initialize(this, 27757,
//				"d22e2399cf41ced05fef177de5e443e7");
		DianJinPlatform.initialize(this, 27757,
				"d22e2399cf41ced05fef177de5e443e7", 1001);
		setContentView(R.layout.activity_main);
		initView();
		DianJinPlatform.requestChannelEnable(getApplicationContext(),
				new ChannelListener() {

					@Override
					public void onSuccess(boolean enable) {
						mChannelTextView.setVisibility(View.VISIBLE);
						mChannelTextView.setText("在线参数，是否在该渠道获取广告=" + enable);
					}

					@Override
					public void onError(int arg0, String arg1) {
						mChannelTextView.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(),
								"在线参数获取失败，请检查网络！", Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("点金Demo")
					.setMessage("是否确认退出点金无积分DEMO?")
					.setCancelable(false)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									/* * 销毁广告 */
									DianJinPlatform.destory(MainActivity.this);
									MainActivity.this.finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}).show();
		}
		return true;
	}
	
	private void initView() {
//		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.main);
		/**java代码方式添加DianJinBanner*/
//		mainLayout.addView(new DianJinBanner(MainActivity.this));
		
		/**java代码方式添加DianJinBanner*/
//		mainLayout.addView(new DianJinMiniBanner(MainActivity.this));
		
		findViewById(R.id.btn_web).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						DianJinPlatform.showOfferWall(MainActivity.this);
					}
				});

		findViewById(R.id.btn_show_float).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						DianJinPlatform.showFloatView(MainActivity.this);
					}
				});

		findViewById(R.id.btn_hide_float).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						DianJinPlatform.hideFloatView(MainActivity.this);
					}
				});

		final DianJinBanner banner = (DianJinBanner) findViewById(R.id.dianJinBaaner1);
		findViewById(R.id.btn_show_banner).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						banner.startBanner();
					}
				});
		mChannelTextView = (TextView) findViewById(R.id.channel_shielded);
	}

}
