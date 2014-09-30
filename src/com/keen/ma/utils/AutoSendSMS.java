package com.keen.ma.utils;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * 自动发送短信类
 * @author KEEN
 * @lastChangeTime 2014年8月19日 上午10:40:13
 */
public class AutoSendSMS {

	private static SmsManager manager = null;
	private static String SENT_SMS_ACTION = "SENT_SMS_ACTION";
	private static String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	
	/**
	 * 自动发送验证码
	 * @author KEEN
	 * @lastChangeTime 2014年8月19日 上午10:42:01
	 * @return
	 */
	public static void autoSendCode(Context context, String phoneNumber,
			String code) {
		if(manager == null)
			manager = SmsManager.getDefault();
		String msg = "【世界触手可及】用户账户密码找回验证码："+code+"，请您及时完成验证，如非本人操作，请忽略本短信。";
		if (msg.length() > 70) {
			List<String> divideContents = manager.divideMessage(msg);
			for (String text : divideContents) {
				manager.sendTextMessage(phoneNumber, null, text, autoSendCallBack(context, SENT_SMS_ACTION),
						autoReceiveCallback(context, DELIVERED_SMS_ACTION));
			}
		} else {
			manager.sendTextMessage(phoneNumber, null, msg, autoSendCallBack(context, SENT_SMS_ACTION),
					autoReceiveCallback(context, DELIVERED_SMS_ACTION));
		}

	}

	/**
	 * 自动发送普通信息
	 * @author KEEN
	 * @lastChangeTime 2014年8月19日 上午10:42:34
	 * @return
	 */
	public static void autoSendMessage(Context context, String phoneNumber,
			String msg) {
		if(manager == null)
			manager = SmsManager.getDefault();
		if (msg.length() > 70) {
			List<String> divideContents = manager.divideMessage(msg);
			for (String text : divideContents) {
				manager.sendTextMessage(phoneNumber, null, text, autoSendCallBack(context, SENT_SMS_ACTION),
						autoReceiveCallback(context, DELIVERED_SMS_ACTION));
			}
		} else {
			manager.sendTextMessage(phoneNumber, null, msg, autoSendCallBack(context, SENT_SMS_ACTION),
					autoReceiveCallback(context, DELIVERED_SMS_ACTION));
		}
	}

	/**
	 * 处理返回的发送状态
	 * 短信发送成功的回调函数
	 * @author KEEN
	 * @lastChangeTime 2014年8月19日 上午11:09:12
	 */
	private static PendingIntent autoSendCallBack(final Context context,String send_sms_action) {

		Intent sentIntent = new Intent(send_sms_action);
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,sentIntent, 0);
		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(context, "短信发送失败", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(context, "短信发送失败", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(context, "短信发送失败", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(send_sms_action));
		return sentPI;
	}
	
	/**
	 * 处理返回的接收状态
	 * @author KEEN
	 * @lastChangeTime 2014年8月19日 上午11:16:57
	 * @return
	 */
	private static PendingIntent autoReceiveCallback(final Context context,String receive_sms_action){
		
		Intent deliverIntent = new Intent(receive_sms_action);
		PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0,
		       deliverIntent, 0);
		context.registerReceiver(new BroadcastReceiver() {
		   @Override
		   public void onReceive(Context _context, Intent _intent) {
			   switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(context,"收信人已经成功接收", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(context,"收信人未成功接收", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(context,"收信人未成功接收", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(context,"收信人未成功接收", Toast.LENGTH_SHORT).show();
					break;
				}
		       
		   }
		}, new IntentFilter(receive_sms_action));
		return deliverPI;
	}

}
