package com.example.dianjinwebreward;

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
import com.bodong.dianjinweb.banner.DianJinBanner;
import com.bodong.dianjinweb.banner.DianJinMiniBanner;
import com.bodong.dianjinweb.listener.AppActiveListener;
import com.bodong.dianjinweb.listener.ChannelListener;
import com.bodong.dianjinweb.listener.ConsumeListener;

public class MainActivity extends Activity {

	private TextView mBalanceTextView;
	private TextView mChannelTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**调用点金方法前需要先初始化*/
//		DianJinPlatform.initialize(this, 1010,
//				"eb3de875023ff1db2077c13888a637a6");
		DianJinPlatform.initialize(this, 1010,
				"eb3de875023ff1db2077c13888a637a6", 1001);
		setContentView(R.layout.activity_main);
		initView();
		DianJinPlatform.requestChannelEnable(getApplicationContext(),
				new ChannelListener() {

					@Override
					public void onSuccess(boolean enable) {
						mChannelTextView.setVisibility(View.VISIBLE);
						mChannelTextView.setText("在线参数，是否在该渠道显示广告=" + enable);
					}

					@Override
					public void onError(int errorCode, String errorMessage) {
						mChannelTextView.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(),
								"在线参数获取失败，请检查网络！", Toast.LENGTH_SHORT).show();
					}
				});

		DianJinPlatform.setAppActivedListener(new AppActiveListener() {

			@Override
			public void onSuccess(long reward) {
				Toast.makeText(MainActivity.this, "激活成功，奖励金额为：" + reward,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(int errorCode, String errorMessage) {
				switch (errorCode) {
				case DianJinPlatform.DIANJIN_NET_ERROR:// 网络不稳定
					Toast.makeText(MainActivity.this, errorMessage,
							Toast.LENGTH_SHORT).show();
					break;
				case DianJinPlatform.DIANJIN_DUPLICATE_ACTIVATION:// 重复激活
					Toast.makeText(MainActivity.this, errorMessage,
							Toast.LENGTH_SHORT).show();
					break;

				case DianJinPlatform.DIANJIN_ADVERTSING_EXPIRED:// 应用已下架
					Toast.makeText(MainActivity.this, errorMessage,
							Toast.LENGTH_SHORT).show();
					break;

				case DianJinPlatform.DIANJIN_ACTIVATION_FAILURE:// 激活失败
					Toast.makeText(MainActivity.this, errorMessage,
							Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}
			}
		});
	}

	@Override
	protected void onResume() {
		mBalanceTextView.setText("当前余额："
				+ DianJinPlatform.getBalance(MainActivity.this));
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("点金Demo")
					.setMessage("是否确认退出点金有积分DEMO?")
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
						/**打开广告墙*/
						DianJinPlatform.showOfferWall(MainActivity.this);
					}
				});

		mBalanceTextView = (TextView) findViewById(R.id.balance);
		mBalanceTextView.setText("当前余额："
				+ DianJinPlatform.getBalance(MainActivity.this));
		findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/**增加余额*/
				DianJinPlatform.addReward(MainActivity.this, 10);
				mBalanceTextView.setText("当前余额："
						+ DianJinPlatform.getBalance(MainActivity.this));
			}
		});

		findViewById(R.id.reduce).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						/**消费余额*/
						DianJinPlatform.consume(MainActivity.this, 10,
								new ConsumeListener() {

									@Override
									public void onSuccess() {
										mBalanceTextView.setText("当前余额："
												+ DianJinPlatform
														.getBalance(MainActivity.this));
									}

									@Override
									public void onError(int errorCode,
											String errorMessage) {
										switch (errorCode) {
										case DianJinPlatform.DIANJIN_ERROR_ILLEGAL_CONSUNE://非法的消费金额
											Toast.makeText(MainActivity.this,
													errorMessage,
													Toast.LENGTH_SHORT).show();
											break;
										case DianJinPlatform.DIANJIN_ERROR_BALANCE_NO_ENOUGH://余额不足
											Toast.makeText(MainActivity.this,
													errorMessage,
													Toast.LENGTH_SHORT).show();
										default:
											break;
										}
									}
								});

					}
				});

		findViewById(R.id.btn_show_float).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						/**显示悬浮框*/
						DianJinPlatform.showFloatView(MainActivity.this);
					}
				});

		findViewById(R.id.btn_hide_float).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						/**隐藏悬浮框*/
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
