package com.wei.chenhuiwan.tiktoktest2.utils;

import android.util.Log;

import com.wei.chenhuiwan.tiktoktest2.TikTokTest;

import androidx.annotation.Nullable;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

/**
 * 一些UI相关的工具类
 *
 * @author chenhuiwan
 * @since 2020-04-17
 */
public class UiUtils {


    /**
     * 判断text否存在
     *
     * @param device   UiDevice 实例
     * @param name     需要判断的字符串
     * @param waitTime 等待时间
     * @return 视图中是否存在
     */
    public static boolean judgmentText(UiDevice device, String name, int waitTime) {
        device.wait(Until.findObject(By.text(name)), waitTime);
        UiObject2 x = device.findObject(By.text(name));
        if (x != null) {
            Log.d(TikTokTest.TAG, "text“ " + name + " ”存在 ");
            return true;
        } else {
            Log.e(TikTokTest.TAG, "text“ " + name + " ”不存在 ");
            return false;
        }
    }

    /**
     * 判断id否存在
     *
     * @param device   UiDevice 实例
     * @param id       资源 id，通过 automatorviewer 获取
     * @param waitTime 等待时长
     * @return 该id在视图中是否存在
     */
    public static boolean judgmentId(UiDevice device, String id, int waitTime) {
        device.wait(Until.findObject(By.res(id)), waitTime);
        UiObject2 x = device.findObject(By.res(id));
        if (x != null) {
            Log.d(TikTokTest.TAG, "text“ " + id + " ”存在 ");
            return true;
        } else {
            Log.e(TikTokTest.TAG, "text“ " + id + " ”不存在 ");
            return false;
        }
    }


    /**
     * 根据描述文件判断控件是否存在
     *
     * @param uiDevice UiDevice 对象
     * @param desc     描述文字
     * @return 是否存在
     */
    public static boolean judgementDesc(UiDevice uiDevice, String desc) {
        UiObject2 uiObject2 = uiDevice.findObject(By.descContains(desc));
        if (uiObject2 != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据字符串获取 UI 对象
     *
     * @param uiDevice UiDevice 实例
     * @param text     字符串
     * @return ui对象
     */
    @Nullable
    public static UiObject2 getUiObjectByText(UiDevice uiDevice, String text) {
        return getUiObjectByText(uiDevice, text, 0);
    }

    /**
     * 根据字符串获取 UI 对象
     *
     * @param uiDevice UiDevice 实例
     * @param text     字符串
     * @return ui对象
     */
    @Nullable
    public static UiObject2 getUiObjectByText(UiDevice uiDevice, String text, int waitTime) {
        return uiDevice.wait(Until.findObject(By.text(text)), waitTime);
    }


    /**
     * 根据资源 id 获取 UI 对象
     *
     * @param uiDevice UiDevice 对象
     * @param id       资源 id
     * @return ui 对象
     */
    @Nullable
    public static UiObject2 getUIObjectById(UiDevice uiDevice, String id) {
        return uiDevice.findObject(By.res(id));
    }

    @Nullable
    public static UiObject2 getUIObjectById(UiDevice uiDevice, String id, int waitTime) {
        return uiDevice.wait(Until.findObject(By.res(id)), waitTime);
    }

    /**
     * 根据字符串获取 UI 对象
     *
     * @param uiDevice UiDevice 实例
     * @param desc     描述符
     * @return ui对象
     */
    @Nullable
    public static UiObject2 getUiObjectByDesc(UiDevice uiDevice, String desc, int waitTime) {
        return uiDevice.wait(Until.findObject(By.descContains(desc)), waitTime);
    }

}
