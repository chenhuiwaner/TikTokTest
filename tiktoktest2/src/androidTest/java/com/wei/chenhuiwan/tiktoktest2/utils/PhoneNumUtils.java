package com.wei.chenhuiwan.tiktoktest2.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.wei.chenhuiwan.tiktoktest2.TikTokTest;

import androidx.annotation.Nullable;
import androidx.test.InstrumentationRegistry;

/**
 * 工具类 获取手机本机电话号码
 *
 * @author chenhuiwan
 * @since 2020-04-17
 */
public class PhoneNumUtils {

    /** 获取手机号码 */
    @Nullable
    public static String getPhoneNumber() {
        Context context = InstrumentationRegistry.getContext();
        String tel = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            // 手机号码
            tel = tm.getLine1Number();
            // 先暂时这样去除中国的区号（+86）
            tel = tel.substring(3);
        } else {
            // tel 为空，提示用户插卡
            Log.e(TikTokTest.TAG, "请插入手机卡");
        }
        return tel;
    }
}
