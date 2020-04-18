package com.wei.chenhuiwan.tiktoktest2;

import static androidx.test.InstrumentationRegistry.getTargetContext;

/**
 * 命令集合
 *
 * @author chenhuiwan
 * @since 2020-04-18
 */
public class CommandConstant {

    /** 短信权限 */
    private static String PERMISSION_SMS = "android.permission.RECEIVE_SMS";
    /** 手机号权限 */
    private static String PERMISSION_PHONE_STATE = "android.permission.READ_PHONE_STATE";

    /** 命令：启动抖音 */
    public static final String START_TIK_TOK = "am start -n com.ss.android.ugc.aweme/com.ss" +
            ".android.ugc.aweme.splash.SplashActivity";
    /** 命令：启动短信 */
    public static final String START_SMS = "am start -n com.oneplus.mms/com.android.mms.ui" +
            ".ConversationList";
    /** 命令：短信权限授权命令 */
    public static final String COMMAND_GRANT_SMS = "pm grant "
            + getTargetContext().getPackageName() + " "
            + PERMISSION_SMS;
    /** 命令：手机号授权命令 */
    public static final String COMMAND_GRANT_PHONE_NUMBER = "pm grant "
            + getTargetContext().getPackageName() + " "
            + PERMISSION_PHONE_STATE;


}
