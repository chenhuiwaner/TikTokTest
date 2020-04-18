package com.wei.chenhuiwan.tiktoktest2;

import android.os.RemoteException;
import android.util.Log;

import com.wei.chenhuiwan.tiktoktest2.utils.PhoneNumUtils;
import com.wei.chenhuiwan.tiktoktest2.utils.UiUtils;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import static java.lang.Thread.sleep;

/**
 * 该类用于抖音点赞。
 * 主要流程是：
 * 0. 点亮屏幕，进入抖音
 * 1. 跳过开屏广告
 * 2. 跳过青少年模式
 * 3. 暂停视频自动播放
 * 4. 点赞，如果未登录进入第5步
 * 5. 进入登录界面
 * 6. 自动填充手机号，并发送获取验证码请求
 * 7. 进入短信APP，解析出短信验证码
 * 8. 进入抖音，自动输入验证码，跳转
 * 9. 跳过查看联系人界面
 * 10.判断，如未点赞则点赞
 *
 * @author chenhuiwan
 * @since 2020-04-17
 */
public class TikTokTest extends BaseTest {

    /** 控件id: EditText 电话号码输入框 */
    private static final String LOGIN_PAGE_PHONE_NUM_EDIT_ID = "com.ss.android.ugc.aweme:id/dcy";
    /** 控件id: 跳过查看通讯录好友页面的跳过 id */
    private static final String SKIP_CONTACT_ID = "com.ss.android.ugc.aweme:id/ejn";
    /** 控件id: 收藏按钮的 id */
    private static final String FAVOR_ID = "com.ss.android.ugc.aweme:id/akc";
    /** 控件id: 跳过广告按钮的 id */
    private static final String SKIP_AD_ID = "com.ss.android.ugc.aweme:id/eb";
    /** 控件id: 关闭登录弹窗 id */
    private static final String UDDATE_DIALOG_CANCEL_ID = "com.ss.android.ugc.aweme:id/c90";
    /** 控件id: 短信Item TextView ID */
    private static final String SMS_CODE_ID = "com.oneplus.mms:id/value";
    /** 控件id: 抖音界面输入验证码的 EditText */
    private static final String TIK_TOK_INPUT_SMS_CODE_ID = "com.ss.android.ugc.aweme:id/eks";

    /** 类名: RelativeLayout */
    private static final String RELATIVE_CLASS_NAME = "android.widget.RelativeLayout";
    /** 类名: 最近一条验证码父View */
    private static final String LAST_SMS_CODE_PARENT_CLASS_NAME = "android.widget.FrameLayout";

    /** 控件文字：获取短信验证码 */
    private static final String GET_MSM_CODE_BTN_TEXT = "获取短信验证码";
    /** 控件文字：我知道了 */
    private static final String KNOWN_BTN_TEXT = "我知道了";
    /** 控件文字：请输入验证码-输入框 */
    private static final String INPUT_SMS_CODE_TEXT = "验证码";

    /** 控件文字：闪屏界面跳过广告 */
    private static final String SKIP_AD_TEXT = "跳过广告";
    /** 控件文字：跳过 */
    private static final String SKIP_TEXT = "跳过";
    /** 控件文字：小视频界面-选中（已点赞） */
    private static final String SELECTED_TEXT = "选中";
    /** 控件文字：小视频界面-未选中（未点赞） */
    private static final String UN_SELECTED_TEXT = "未选中";
    /** 控件文字：手机号输入框 */
    private static final String INPUT_PHONE_NUMBER_TEXT = "请输入手机号";
    /** 控件文字：验证码输入框 */
    private static final String INPUT_AUTH_SMS_CODE_TEXT = "请输入验证码";

    /** 查询控件等待耗时：2s */
    private static final int QUERY_WAITE_TIME = 2 * 1000;
    /** 短信等待时间 */
    private static final int SMS_WAITE_TIME = 10 * 1000;

    /** log tag */
    public static String TAG = "TestTicTok";

    @Test
    public void touchLike() throws UiObjectNotFoundException, InterruptedException, IOException, RemoteException {

        // 屏幕未唤醒需要唤醒
        if (!mUiDevice.isScreenOn()) {
            mUiDevice.wakeUp();
        }
        // 启动抖音
        startTikTok();
        // 如果有广告页面-跳过
        skipAdIffNeed();
        // 如果有提醒儿童/青少年模式-跳过(直接点击"我知道了"）
        skipYoungModeIfNeed();

        // 青少年模式消失需要一定时间，暂停3秒
        // sleep(3000);

        // 抖音打开会自动播放，点击屏幕中间，暂停播放
        clickCenter();
        // 未收藏，点击收藏
        clickFavorIfNeed();
        // 如果点收藏跳转到登录界面，就登录后再收藏
        loginAndFavorIfNeed();
    }

    /**
     * 登录相关操作
     *
     * @throws InterruptedException
     * @throws IOException
     * @throws UiObjectNotFoundException
     */
    private void login() throws InterruptedException, IOException, UiObjectNotFoundException {
        // 发送短信验证码
        sendAuthCodeRequest();
        // 获取短信验证码
        String smsAuthCode = getSmsAuthCode();
        // 再次启动抖音：回到短信验证码界面
        startTikTok();
        // 输入验证码
        inputSmsAuthCode(smsAuthCode);
        // 出现查看通讯录好友弹窗就跳过
        skipContactPageIfNeed();
        // 点赞
        clickFavorIfNeed();
    }

    /** 发送获取短信验证码请求 */
    private void sendAuthCodeRequest() throws InterruptedException {
        String phoneNumber = PhoneNumUtils.getPhoneNumber();
        UiObject2 phoneNumberEdit = UiUtils.getUIObjectById(mUiDevice, LOGIN_PAGE_PHONE_NUM_EDIT_ID);
        phoneNumberEdit.setText(phoneNumber);
        sleep(1000);
        // 点击"获取短信验证码"
        UiUtils.getUiObjectByText(mUiDevice, GET_MSM_CODE_BTN_TEXT).click();
    }

    /** 获取短信验证码 */
    private String getSmsAuthCode() throws IOException, UiObjectNotFoundException, InterruptedException {
        // 启动短信
        mUiDevice.executeShellCommand(CommandConstant.START_SMS);
        UiObject childTextView = new UiObject(new UiSelector()
                .className(RELATIVE_CLASS_NAME)
                .childSelector(new UiSelector()
                        .text(INPUT_SMS_CODE_TEXT)));
        childTextView.click();

        // 防止短信未发过来，可延长，1分钟最宜
        sleep(SMS_WAITE_TIME);

        // 用正则表达式获取验证码
        UiObject childView = new UiObject(new UiSelector()
                .className(LAST_SMS_CODE_PARENT_CLASS_NAME).index(1)
                .childSelector(new UiSelector().resourceId(SMS_CODE_ID)));
        String tikTokSms = childView.getText();
        String pattern = "\\d{4}";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(tikTokSms);
        List<String> resultList = new ArrayList<>();
        while (matcher.find()) {
            String result = matcher.group();
            resultList.add(result);
        }

        String authCode = null;
        if (resultList.size() > 0) {
            // 取最后一个
            authCode = resultList.get(resultList.size() - 1);
            Log.d(TAG, "验证码是: " + authCode);
        }
        return authCode;
    }

    /** 输入验证码 */
    private void inputSmsAuthCode(String smsAuthCode) {
        UiObject2 inputSmsCode = UiUtils.getUIObjectById(mUiDevice, TIK_TOK_INPUT_SMS_CODE_ID,
                QUERY_WAITE_TIME);
        if (inputSmsCode != null) {
            inputSmsCode.setText(smsAuthCode);
        } else {
            Log.e(TAG, "验证码控件为 null");
        }
    }

    /** 跳过查看联系人界面 */
    private void skipContactPageIfNeed() {
        UiObject2 contact = UiUtils.getUIObjectById(mUiDevice, SKIP_CONTACT_ID, QUERY_WAITE_TIME);
        if (contact != null && contact.isEnabled()) {
            contact.click();
        } else {
            Log.e(TAG, "没有提醒查看通讯录好友的弹窗");
        }
    }

    /** 跳过广告 */
    private void skipAdIffNeed() {
        UiObject2 ad = UiUtils.getUIObjectById(mUiDevice, SKIP_AD_ID, QUERY_WAITE_TIME);
        if (ad != null && ad.isEnabled()) {
            Log.d(TAG, "有广告，跳过");
            ad.click();
        } else {
            Log.d(TAG, "无广告");
        }
    }

    /** 跳过青少年模式 */
    private void skipYoungModeIfNeed() {
        UiObject2 childMode = UiUtils.getUiObjectByText(mUiDevice, KNOWN_BTN_TEXT, QUERY_WAITE_TIME);
        if (childMode != null && childMode.isEnabled()) {
            childMode.click();
            Log.d(TAG, "有青少年模式， 跳过弹窗");
        } else {
            Log.d(TAG, "无青少年模式");
        }
    }

    /** 点赞 */
    private void clickFavorIfNeed() {
        // 判断 如果之前没有点赞成功，再点击
        UiObject2 likeObject = UiUtils.getUIObjectById(mUiDevice, FAVOR_ID, QUERY_WAITE_TIME);
        if (likeObject != null && likeObject.isEnabled()) {
            likeObject.click();
        }
    }

    /** 启动抖音 */
    private void startTikTok() throws IOException {
        mUiDevice.executeShellCommand(CommandConstant.START_TIK_TOK);
    }

    /** 点击屏幕中间 */
    private void clickCenter() {
        int centerX = mUiDevice.getDisplayWidth() / 2;
        int centerY = mUiDevice.getDisplayHeight() / 2;
        mUiDevice.click(centerX, centerY);
    }

    /** 如果当前在登录界面，就登录，再点赞 */
    private void loginAndFavorIfNeed() throws InterruptedException, UiObjectNotFoundException, IOException {
        // 界面判断是否有"获取短信验证码"
        boolean isLoginPage = UiUtils.judgmentText(mUiDevice, GET_MSM_CODE_BTN_TEXT, QUERY_WAITE_TIME);
        if (isLoginPage) {
            Log.d(TAG, "未登录");
            login();
        } else {
            Log.d(TAG, "已登录");
        }
    }
}
