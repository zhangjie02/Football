/****************************************************************************
Copyright (c) 2010-2012 cocos2d-x.org

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ****************************************************************************/
package com.wade.football;

import java.util.ArrayList;
import java.util.List;

import net.umipay.android.GameParamInfo;
import net.umipay.android.UmiPaySDKManager;
import net.umipay.android.UmiPaymentInfo;
import net.umipay.android.UmipayOrderInfo;
import net.umipay.android.UmipaySDKStatusCode;
import net.umipay.android.interfaces.InitCallbackListener;
import net.umipay.android.interfaces.OrderReceiverListener;
import net.umipay.android.interfaces.PayProcessListener;
import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SpotManager;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxLuaJavaBridge;
import org.cocos2dx.utils.PSNetwork;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

public class Football extends Cocos2dxActivity {

	public static Football instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		PSNetwork.init(instance);
//		AdManager.getInstance(this).init("758fbb9b1d0c34ee",
//				"7fbd95e41892a95c", false);
//		SpotManager.getInstance(this).loadSpotAds();
//
//		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//				FrameLayout.LayoutParams.MATCH_PARENT,
//				FrameLayout.LayoutParams.WRAP_CONTENT);
//		layoutParams.gravity = Gravity.TOP;
//		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
//		this.addContentView(adView, layoutParams);

		// 初始化sdk参数
		initSDK();
		// 初始化支付动作回调
		initPayProcessListener();
		
	}

	/**
	 * 初始化安全支付sdk
	 */
	private void initSDK() {
		// 初始化参数
		GameParamInfo gameParamInfo = new GameParamInfo();
		// 您的应用的AppId
		gameParamInfo.setAppId("758fbb9b1d0c34ee");
		// 您的应用的AppSecret
		gameParamInfo.setAppSecret("7fbd95e41892a95c");

		// false 订单充值成功后是使用服务器通知 true 订单充值成功后使用客户端回调
		gameParamInfo.setSDKCallBack(true);

		// 初始化结果回调接口
		final InitCallbackListener initCallbackListener = new InitCallbackListener() {
			@Override
			public void onInitCallback(int code, String msg) {
				if (code == UmipaySDKStatusCode.SUCCESS) {
					// 初始化成功后，即可正常调用充值

				} else if (code == UmipaySDKStatusCode.INIT_FAIL) {
					// 初始化失败，一般在这里提醒用户网络有问题，反馈，等等问题
					// System.out.println("UmipaySDKStatusCode.INIT_FAIL")
				}
			}
		};

		// 订单回调接口
		final OrderReceiverListener orderReceiverListener = new OrderReceiverListener() {
			/**
			 * 接收到服务器返回的订单信息 ！！！注意，该返回是在非ui线程中回调，如果需要更新界面，需要手动使用主线刷新
			 */
			@Override
			public List onReceiveOrders(List list) {
				System.out.println("onReceiveOrders");
				// Toast.makeText(getApplicationContext(), "充值完成！请耐心等候充值结果",
				// Toast.LENGTH_SHORT).show();
				List<UmipayOrderInfo> newOrderList = list;
				List<UmipayOrderInfo> doneOrderList = new ArrayList<UmipayOrderInfo>();
				// TODO 服务器返回的订单信息newOrderList，并将已经处理好充值的订单返回给sdk
				// TODO sdk将已经处理完的订单通知给服务器。服务器下次将不再返回游戏客户端已经处理过的订单
				for (UmipayOrderInfo newOrder : newOrderList) {
					System.out
							.println("UmipayOrderInfo newOrder : newOrderList");
					try {
						// TODO 对订单order进行结算
						if (newOrder.getStatus() == 1) {
							// 在主线程更新界面
							// ...
							// ...
							// ...
							doneOrderList.add(newOrder);
							System.out.println("newOrder.getStatus() == 1");
							int real_money = (int) newOrder.getRealMoney();
							System.out.println("newOrder.getRealMoney():"
									+ real_money);
							if (real_money == 1) {
								instance.runOnUiThread(new Runnable() {
									public void run() {
										instance.onResume();
									}
								});
								instance.runOnGLThread(new Runnable() {
									@Override
									public void run() {
										System.out.println("addLf one");
										Cocos2dxLuaJavaBridge
												.callLuaGlobalFunctionWithString(
														"addLf", "one");
									}
								});
							} else if (real_money == 5) {
								instance.runOnUiThread(new Runnable() {
									public void run() {
										instance.onResume();
									}
								});
								instance.runOnGLThread(new Runnable() {
									@Override
									public void run() {
										System.out.println("addLf five");
										Cocos2dxLuaJavaBridge
												.callLuaGlobalFunctionWithString(
														"addLf", "five");
									}
								});
							}
						}
					} catch (Exception e) {

					}
				}
				return doneOrderList; // 将已经处理过的订单返回给sdk，下次服务器不再返回这些订单
			}
		};

		// 调用SDK初始化接口
		UmiPaySDKManager.initSDK(this, gameParamInfo, initCallbackListener,
				orderReceiverListener);
	}

	public static void buyOne() {
		// 设置充值信息
		UmiPaymentInfo paymentInfo = new UmiPaymentInfo();
		// 业务类型，SERVICE_TYPE_QUOTA(固定额度模式，充值金额在支付页面不可修改)，SERVICE_TYPE_RATE(汇率模式，充值金额在支付页面可修改）
		paymentInfo.setServiceType(UmiPaymentInfo.SERVICE_TYPE_QUOTA);
		// 定额支付金额，单位RMB
		paymentInfo.setPayMoney(1);
		// 订单描述
		paymentInfo.setDesc("1个小手指");
		// 【可选】外部订单号
		// paymentInfo.setTradeno("TN2014022010071234");
		// 游戏开发商自定义数据，可选。该值将在用户充值成功后，在支付工具服务器回调给游戏开发商时携带该数据
		paymentInfo.setCustomInfo("1个小手指");
		// 调用支付接口
		UmiPaySDKManager.showPayView(instance, paymentInfo);
		System.out.println("wade buyOne");
		
//		instance.runOnGLThread(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("addLf one");
//				Cocos2dxLuaJavaBridge
//						.callLuaGlobalFunctionWithString(
//								"addLf", "one");
//			}
//		});
	}

	public static void buyFive() {
		// 设置充值信息
		UmiPaymentInfo paymentInfo = new UmiPaymentInfo();
		// 业务类型，SERVICE_TYPE_QUOTA(固定额度模式，充值金额在支付页面不可修改)，SERVICE_TYPE_RATE(汇率模式，充值金额在支付页面可修改）
		paymentInfo.setServiceType(UmiPaymentInfo.SERVICE_TYPE_QUOTA);
		// 定额支付金额，单位RMB
		paymentInfo.setPayMoney(5);
		// 订单描述
		paymentInfo.setDesc("6个小手指");
		// 【可选】外部订单号
		// paymentInfo.setTradeno("TN2014022010071234");
		// 游戏开发商自定义数据，可选。该值将在用户充值成功后，在支付工具服务器回调给游戏开发商时携带该数据
		paymentInfo.setCustomInfo("6个小手指");
		// 调用支付接口
		UmiPaySDKManager.showPayView(instance, paymentInfo);
		System.out.println("wade buyFive");
	}
	
	public static void showTip(final String str){
		instance.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(instance,str,
				Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	/**
	 * 初始化支付动作回调接口
	 */
	private void initPayProcessListener() {
		UmiPaySDKManager.setPayProcessListener(new PayProcessListener() {

			@Override
			public void OnPayProcess(int code) {
				switch (code) {
				case UmipaySDKStatusCode.PAY_PROCESS_SUCCESS:
					// 充值完成，不等于充值成功，实际充值结果以订单回调接口为准
					Toast.makeText(getApplicationContext(), "充值完成！请耐心等候充值结果",
							Toast.LENGTH_SHORT).show();
					break;
				case UmipaySDKStatusCode.PAY_PROCESS_FAIL:
					 Toast.makeText(getApplicationContext(), "请稍后...",
					 Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
	}

	static {
		System.loadLibrary("game");
	}

	protected void onDestroy() {
		SpotManager.getInstance(this).unregisterSceenReceiver();
		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			System.out.println("wade onKeyDown");
			new AlertDialog.Builder(this)
					.setMessage("你确定退出吗？")
					.setNegativeButton("再玩会",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									finish();
									System.exit(0);
								}
							}).show();
		}
		// SpotManager.getInstance(this).showSpotAds(this);
		return super.onKeyDown(keyCode, event);
	}
}
