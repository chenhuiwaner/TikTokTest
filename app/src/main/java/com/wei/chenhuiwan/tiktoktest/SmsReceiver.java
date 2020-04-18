package com.wei.chenhuiwan.tiktoktest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个类的描述
 *
 * @author chenhuiwan
 * @since 2020-04-15
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            Log.i("text", "收到短信了");
            Bundle bundle = intent.getExtras();
            Object object[] = (Object[]) bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[object.length];
            for (int i = 0; i < object.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) object[i]);
            }
            List<SmsMessage> tiktokSmsList = new ArrayList<>();
            for (SmsMessage message : smsMessage) {
                String address = message.getOriginatingAddress();
                String body = message.getDisplayMessageBody();
                long timeMills = message.getTimestampMillis();
                Log.i("text", address + ":" + body);
                if (body.contains("抖音")) {
                    tiktokSmsList.add(message);
                }
            }

            // 取出最近的抖音短信
            long lastTime = 0;
            SmsMessage lastMsg = null;
            for (SmsMessage message : tiktokSmsList) {
                if (message.getTimestampMillis() > lastTime) {
                    lastTime = message.getTimestampMillis();
                    lastMsg = message;
                }
            }

            // 存储最近的抖音短信


        }
    }

    private void saveTikTokSmsIfNeed() {

    }
}
