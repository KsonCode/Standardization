package com.bwie.standardization.platform.common;

import android.os.Environment;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/04/28
 * Description:
 */
public class Constants {
    public static final String BASE_URL = "http://www.zhaoapi.cn/";
    public static final String IMAGES_PATH = BASE_URL+"images/unit/";
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/biewstandard/";
    public static final String SDCARD_FILE_PATH = SDCARD_PATH+"content.json";
}
