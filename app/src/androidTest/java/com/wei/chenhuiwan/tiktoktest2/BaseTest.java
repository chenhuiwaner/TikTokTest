package com.wei.chenhuiwan.tiktoktest2;

import android.app.Instrumentation;
import android.app.UiAutomation;

import org.junit.Before;

import androidx.test.uiautomator.UiDevice;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * 测试基类，放一些基础操作
 *
 * @author chenhuiwan
 * @since 2020-04-18
 */
public class BaseTest {
    /** Instrumentation 实例 */
    protected Instrumentation mInstrumentation;
    /** UiDevice 实例-用于获取和设置设备信息 */
    protected UiDevice mUiDevice;

    /** 实例化 */
    @Before
    public void setUp() {
        mInstrumentation = getInstrumentation();
        mUiDevice = UiDevice.getInstance(mInstrumentation);
        grantPermission();
    }

    /** 授权 */
    private void grantPermission() {
        UiAutomation uiAutomation = mInstrumentation.getUiAutomation();
        uiAutomation.executeShellCommand(CommandConstant.COMMAND_GRANT_PHONE_NUMBER);
        uiAutomation.executeShellCommand(CommandConstant.COMMAND_GRANT_SMS);
    }
}
