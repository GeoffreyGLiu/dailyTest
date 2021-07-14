/*
 * 深圳市有信网络技术有限公司
 * Copyright (c) 2016 All Rights Reserved.
 */

package utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.log.Logger;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Description 项目通用工具类 Author Ray.Guo Date 16/2/15 15:52
 */
public class CommonUtils {

    /**
     * 手机网络状态
     * Unknown network class
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;

    /**
     * wifi net work
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * "2G" networks
     */
    public static final int NETWORK_CLASS_2_G = 2;

    /**
     * "3G" networks
     */
    public static final int NETWORK_CLASS_3_G = 3;

    /**
     * "4G" networks
     */
    public static final int NETWORK_CLASS_4_G = 4;

    /**
     * 获取应用包名
     *
     * @param context
     * @return
     */
    public static String getAppPackageName(Context context) {
        if (context == null) {
            return null;
        }
        return context.getPackageName();
    }

    /**
     * 获取应用版本名称方法
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 获取手机屏幕宽度通用方法
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (null == context) {
            return 0;
        }
        Point sizePoint = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            Display display = manager.getDefaultDisplay();
            display.getSize(sizePoint);
        }
        return sizePoint.x;
    }

    /**
     * 获取手机屏幕高度通用方法
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (null == context) {
            return 0;
        }
        Point sizePoint = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            Display display = manager.getDefaultDisplay();
            display.getSize(sizePoint);
        }
        return sizePoint.y;
    }

    /**
     * 获取手机屏幕高度
     * 为了解决getScreenHeight（）方法，全面屏手机高度减去了StatusBar的高度
     *
     * @param context
     * @return
     */
    public static int getRealScreenHeight(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getRealMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 屏幕尺寸dip转px换算方法
     *
     * @param context
     * @param dipValue dip值
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if (null == context) {
            return 0;
        }
        final float scaleValue = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scaleValue + 0.5f);
    }

    public static float getDensity(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 屏幕尺寸px转dip换算方法
     *
     * @param context
     * @param pxValue px值
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        if (null == context) {
            return 0;
        }
        final float scaleValue = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scaleValue + 0.5f);
    }

    /**
     * 获取设备标识码，读取IMEI，如果IMEI读取失败读取IMSI, 如果IMSI读取失败读取mac地址
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        if (null == context) {
            return null;
        }
        final String imeiStr = getImeiString(context);
        if (!TextUtils.isEmpty(imeiStr)) {
            return imeiStr;
        } else {
            final String imsiStr = getImsiString(context);
            if (!TextUtils.isEmpty(imsiStr)) {
                return imsiStr;
            } else {
                return getMacString(context);
            }
        }
    }

    /**
     * 判断当前手机网络的连接状态
     *
     * @param context
     * @return
     */
    public static boolean isNetConnected(Context context) {
        boolean isConnected = false;
        try {
            final ConnectivityManager connManager = getConnectServicve(context);
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()
                    && info.getState() == NetworkInfo.State.CONNECTED) {
                isConnected = true;
            }
        } catch (Exception e) {
            isConnected = false;
        }
        return isConnected;
    }

    /**
     * 判断当前手机wifi网络的连接状态
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        boolean isConnected = false;
        try {
            final ConnectivityManager connManager = getConnectServicve(context);
            NetworkInfo.State wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            //判断wifi已连接的条件
            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
                isConnected = true;
        } catch (Exception e) {
            isConnected = false;
        }
        return isConnected;
    }

    /**
     * 获取当前手机网络状态
     *
     * @param context
     * @return
     */
    public static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NETWORK_CLASS_2_G;

                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NETWORK_CLASS_3_G;

                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NETWORK_CLASS_4_G;

                default:
                    return NETWORK_CLASS_UNKNOWN;
            }
        }
        return NETWORK_CLASS_UNKNOWN;
    }

    public static int getNetWorkStatus(Context context) {
        int netWorkType = NETWORK_CLASS_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }

        return netWorkType;
    }

    /**
     * 封装findViewById方法，不需要强制转型
     *
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings({
            "unchecked", "UnusedDeclaration"
    })
    public static <T extends View> T findViewById(View view, int id) {
        if (view.findViewById(id) != null) {
            view.findViewById(id).setSoundEffectsEnabled(false);
        }
        return (T) view.findViewById(id);
    }

    /**
     * 封装findViewById方法，不需要强制转型
     *
     * @param activity
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings({
            "unchecked", "UnusedDeclaration"
    })
    public static <T extends View> T findViewById(Activity activity, int id) {
        if (activity.findViewById(id) != null) {
            activity.findViewById(id).setSoundEffectsEnabled(false);
        }
        return (T) activity.findViewById(id);
    }

    /**
     * 根据指定的文件路径，删除对应文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        final File file = new File(filePath);
        if (null != file && file.exists()) {
            file.delete();
        }
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String textToDBC(String input) {
        String backup = input;
        try {
            char[] c = input.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375)
                    c[i] = (char) (c[i] - 65248);
            }
            return new String(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return backup;
    }

    /**
     * 获取设备的IMEI字符串
     *
     * @param context
     * @return
     */
    public static String getImeiString(Context context) {
        if (null == context) {
            return "";
        }
        try {//大于等于Q 会有SecurityException异常 故try catch处理
            final String imei = getTelpephonyService(context).getDeviceId();
            return imei;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取设备的IMSI的字符串
     *
     * @param context
     * @return
     */
    public static String getImsiString(Context context) {
        if (null == context) {
            return "";
        }
        try {
            final String imsi = getTelpephonyService(context).getSubscriberId();
            Log.e("imsiNormal:", getTelpephonyService(context).getSubscriberId() + "");
            return imsi;
        } catch (Exception e) {
            Log.e("imsiError:", e.getMessage() + "");
            return "";
        }
    }

    /**
     * 获取MAC地址的字符串
     *
     * @param context
     * @returnUtilsToast
     */
    private static String getMacString(Context context) {
        WifiInfo info = null;
        try {
            info = getWifiServicve(context).getConnectionInfo();
        } catch (Exception e) {
            info = null;
        }
        String mac = "";
        if (info != null) {
            mac = info.getMacAddress();
            if (!TextUtils.isEmpty(mac)) {
                mac = mac.replaceAll(":", "");
            }
        }
        return mac;
    }

    /**
     * 获取ActivityManager管理类
     */
    private static ActivityManager getActivityService(Context context) {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
    }

    /**
     * 获取ConnectivityManager管理类
     *
     * @param context
     * @return
     */
    public static ConnectivityManager getConnectServicve(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取WifiManager类
     *
     * @param context
     * @return
     */
    public static WifiManager getWifiServicve(Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取手机TelephonyManager类
     *
     * @param context
     * @return
     */
    public static TelephonyManager getTelpephonyService(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static void sendSMS(Context context, String msg) {
        try {
            Uri smsToUri = Uri.parse("smsto:");
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
            sendIntent.putExtra("sms_body", msg);
            sendIntent.setType("vnd.android-dir/mms-sms");
            context.startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
        } catch (Exception e) {
        }
    }

    /**
     * 根据资源id对应的字符串
     */
    public static String getResourceString(Context mContext, int resId) {
        if (mContext == null) {
            return "";
        }
        return mContext.getString(resId);
    }

    /**
     * 根据资源ID获取颜色值
     *
     * @author: amos
     * @date: 16/4/5 15:37
     */
    public static int getColorByResId(Context mContext, int id) {
        return mContext.getResources().getColor(id);
    }


    /**
     * 获取聊天表情大小
     *
     * @param context
     * @return
     */
    public static int getfaceSize(Context context) {
        if (FACE_SIZE == -1 || FACE_IMAGE_SIZE == -1) {
            getFaceAndImageSize(context);
        }
        return FACE_SIZE;
    }

    /**
     * 获取聊天表情原始文件大小
     *
     * @param context
     * @return
     */
    public static float getFaceImageSize(Context context) {
        if (FACE_SIZE == -1 || FACE_IMAGE_SIZE == -1) {
            getFaceAndImageSize(context);
        }
        return FACE_IMAGE_SIZE;
    }

    /*
     *表情显示大小
     */
    private static int FACE_SIZE = -1;
    /**
     * 表情存储大小
     */
    private static float FACE_IMAGE_SIZE = -1;

    private static void getFaceAndImageSize(Context context) {
        int faceSize = -1;
        float imageSize = -1;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int width = displayMetrics.widthPixels;// 屏幕宽度（像素）
        if (width <= 240) {
            faceSize = 22;
            imageSize = 80.0f;
        } else if (width <= 320) {
            faceSize = 28;
            imageSize = 100.0f;
        } else if (width <= 480) {
            faceSize = 33;
            imageSize = 120.0f;
        } else if (width <= 640) {
            faceSize = 36;
            imageSize = 150.0f;
        } else if (width <= 720) {
            faceSize = 40;
            imageSize = 180.0f;
        } else if (width < 1080) {/* modify by qianda,为解决大屏幕手机表情太小问题*/
            imageSize = 270.0f;
            faceSize = 60;
        } else {
            imageSize = 360.0f;
            faceSize = 80;
        }
        FACE_SIZE = faceSize;
        FACE_IMAGE_SIZE = imageSize;
    }

    public static void gotoWeiboProfile(Context context, String uid) {
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("sinaweibo://userinfo?uid=" + uid);
        intent.setData(content_url);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            content_url = Uri.parse("http://m.weibo.cn/u/" + uid);
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }

    public static void gotoSystemWebView(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            //ToastUtils.showToastShort("打开地址失败！");
        }
    }


    public static boolean isTouchInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        Rect mChangeImageBackgroundRect = new Rect();
        view.getDrawingRect(mChangeImageBackgroundRect);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mChangeImageBackgroundRect.left = location[0];
        mChangeImageBackgroundRect.top = location[1];
        mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right + location[0];
        mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom + location[1];
        return mChangeImageBackgroundRect.contains(x, y);
    }

    private static int sStatusBarHeight = 0;

    /**
     * 是否点击了view内部，这个方法忽略了status bar的高度
     * 当你的坐标是从match parent的父容器里取得就要减去status bar的高度
     * add by zhangkun 2018/11
     *
     * @param view view
     * @param x    坐标
     * @param y    坐标
     * @return 是否在内部
     */
    public static boolean isTouchInViewIgnoreStatusBar(View view, int x, int y) {
        //statusBar不会随便变，取一次就行了
        if (sStatusBarHeight == 0) {
            Resources resources = view.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            sStatusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        Rect mChangeImageBackgroundRect = new Rect();
        view.getDrawingRect(mChangeImageBackgroundRect);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mChangeImageBackgroundRect.left = location[0];
        mChangeImageBackgroundRect.top = location[1] - sStatusBarHeight;
        mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right + location[0];
        mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom + location[1] - sStatusBarHeight;
        return mChangeImageBackgroundRect.contains(x, y);
    }

    public static boolean isViewOnWindowVisiable(View view, Context context) {
        int screenWidth = getScreenWidth(context);
        int screenHeight = getScreenHeight(context);
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        if (view.getLocalVisibleRect(rect)) {
            return true;
        }
        return false;
    }

    /**
     * 格式化数字为财务数字:100,000,000.00
     * 为了适应服务器端返回金额单位为"分",将数字作为字符串处理成100,000,000.00格式
     *
     * @param num 数值
     * @return 财务数值
     */
    public static String formatNum(long num) {
        try {
            if (num == 0)
                return "0.00";
            if (num < 10 && num > 0) {
                //9 --> 0.09
                return "0.0" + num;
            } else if (num < 100) {
                //99 --> 0.99
                return "0." + num;
            } else {
                //199 --> 1.99
                String result = String.valueOf(num);
                DecimalFormat format = new DecimalFormat("###,###,###");
                String beforDot = result.substring(0, result.length() - 2);
                String behindDot = result.substring(result.length() - 2);
                return format.format(Long.valueOf(beforDot)) + "." + behindDot;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * 截取字符串如:4 我是很长很长的名字 ——> 我是很长...
     *
     * @param keepLength 保留文字的长度
     * @param content    文字内容
     * @return 格式化文字
     */
    public static String buildString(int keepLength, String content) {
        if (content == null)
            return "";
        if (content.length() <= keepLength)
            return content;
        content = content.substring(0, keepLength) + "...";
        return content;
    }

    /**
     * 获取Manifest Application节点下指定key的meta-data的value值
     *
     * @param context Context
     * @param key     meta-data key
     * @return meta-data value
     */
    public static String getApplicationMetaValue(Context context, String key) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 根据传入的文本内容和图片的宽高，获取图文混排的SpannableString对象
     * 用MixtureTextView实现图文混排
     *
     * @param tv          TextView
     * @param content     文本内容
     * @param imageWidth  图片宽
     * @param imageHeight 图片高
     * @param margin      距离图片的间距
     * @return SpannableString
     */
    @Deprecated
    public static SpannableString makeRoundSpan(Context context, TextView tv, String content, int imageWidth, int imageHeight, int margin, int speacExtra) {
        if (TextUtils.isEmpty(content) || tv == null)
            return null;

        Spanned htmlText = Html.fromHtml(content);
        SpannableString mSpannableString = new SpannableString(htmlText);

        int allTextEnd = htmlText.length() - 1;

        int lines;
        Paint.FontMetrics fm = tv.getPaint().getFontMetrics();

        float textHeight = fm.bottom - fm.top + speacExtra;
//        int textHeight = tv.getMeasuredHeight() + speacExtra;

        lines = (int) Math.ceil(imageHeight / textHeight);
        //查找原因
        lines--;

        TextLeadingMarginSpan span = new TextLeadingMarginSpan(lines, imageWidth + margin);
        mSpannableString.setSpan(span, 0, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return mSpannableString;
    }

    /**
     * SP 转 PX
     *
     * @param context Context
     * @param spValue sp值
     * @return
     */
    public static float sp2px(Context context, float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取顶部状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取低部导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 判断app是否处于前台
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        if (context == null) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否是标准的URL格式
     *
     * @param urlContent 链接字符串
     * @return true 是url， false 不是url
     */
    public static boolean isUrlType(String urlContent) {
        boolean isUrl;
        try {
            URL url = new URL(urlContent);
            isUrl = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            isUrl = false;
        }
        return isUrl;
    }

    /**
     * 对字符串做加 ***** 操作
     *
     * @param keepStar 保留开始的几位
     * @param keepEnd  保留结束的几位
     * @param content  文本
     * @return 加*后的字符串
     */
    public static String formatStarStr(String content, int keepStar, int keepEnd, int starNum) {
        if (content.length() <= 1)
            return content;

        if (content.length() < keepStar + keepEnd) {
            //文本内容小于要保留的位数且保留位不为0，首位只保留一位
            if (keepStar != 0) {
                keepStar = 1;
            }
            if (keepEnd != 0) {
                keepEnd = 1;
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append(content.substring(0, keepStar));
        for (int i = 0; i < starNum; i++) {
            sb.append("*");
        }
        if (keepEnd > 0) {
            sb.append(content.substring(content.length() - keepEnd, content.length()));
        }
        return sb.toString();
    }

    /**
     * 判断媒体流音量是否低于20%
     *
     * @return true:音量低 false：音量不低
     */
    public static boolean isVolumeTolow(Context context) {
        if (context == null) {
            return false;
        }

        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);

        return currentVolume == 0 ? true : maxVolume / currentVolume > 5;
    }

    /**
     * 扫描文件夹
     *
     * @param context  上下文
     * @param filePath 文件夹全路径
     */
    public static void scanFile(Context context, String filePath) {
        if (context == null || filePath == null) {
            return;
        }
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    /**
     * 判断设备是否接入耳机
     *
     * @param context Context
     * @return 是否接入耳机 true:接入耳机 false：没有接入耳机
     */
    public static boolean isHeadsetConnected(Context context) {
        boolean isHeadsetConnected = false;
        if (context == null) {
            return isHeadsetConnected;
        }
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// sdk 23以上才能用该方法
            AudioDeviceInfo[] audioDeviceInfos = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
            for (AudioDeviceInfo deviceInfo : audioDeviceInfos) {
                if (AudioDeviceInfo.TYPE_WIRED_HEADSET == deviceInfo.getType()) {
                    isHeadsetConnected = true;
                }
            }
        } else {
            isHeadsetConnected = audioManager.isWiredHeadsetOn();//sdk 14以后过时
        }
        return isHeadsetConnected;
    }

    /**
     * 判断图片地址是否是gif图
     *
     * @param url 图片地址
     * @return true：是gif图；false：不是gif图
     */
    public static boolean isGifUrl(String url) {
        if (TextUtils.isEmpty(url))
            return false;
        Uri uri = Uri.parse(url);
        String path = uri.getPath();
        if (!TextUtils.isEmpty(path)) {
            return path.toLowerCase().endsWith(".gif");
        }
        return false;
    }

    public static int getScreenCenterPosition(Context context, boolean includeStatusBar) {
        int position = 0;
        int screenHeight = getScreenHeight(context);
        int statusBarHeight = getStatusBarHeight(context);
        if (!includeStatusBar) {
            screenHeight -= statusBarHeight;
        }
        position = screenHeight / 2;
        return position;
    }

    /**
     * 手机是否开启了旋转屏幕
     *
     * @param context Context
     * @return true：开启了屏幕旋转， false：未开启屏幕旋转
     */
    public static boolean isRotationOpened(Context context) {
        int rotation = 0;
        try {
            rotation = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return rotation == 1 ? true : false;
    }

    /**
     * 为rootView添加蒙层
     */
    public static void addLayer(Activity context, View layerView) {
        if (context == null || layerView == null)
            return;
        ViewGroup contentView = (ViewGroup) context.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        contentView.addView(layerView);
    }

    /**
     * 为rootView移除蒙层
     */
    public static void removeLayer(Activity context, View layerView) {
        if (context == null || layerView == null)
            return;
        ViewGroup contentView = (ViewGroup) context.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        contentView.removeView(layerView);
    }


    /**
     * 获取uri中相应key值对应参数
     * 仅支持参数encode后url也encode一次的情况
     */
    public static String getDecodedQueryParameter(String key, Uri data) {
        if (TextUtils.isEmpty(key)) {
            throw new NullPointerException("key is null");
        }

        final String query = getQuery(data);
        if (query == null) {
            return null;
        }

        final String queryKey = key;
        final int length = query.length();
        int start = 0;
        do {
            int nextAmpersand = query.indexOf('&', start);
            int end = nextAmpersand != -1 ? nextAmpersand : length;

            int separator = query.indexOf('=', start);
            if (separator > end || separator == -1) {
                separator = end;
            }

            if (separator - start == queryKey.length()
                    && query.regionMatches(start, queryKey, 0, queryKey.length())) {
                if (separator == end) {
                    return "";
                } else {
                    String encodedValue = query.substring(separator + 1, end);
                    //将参数decode，仅支持参数encode后url也encode一次的情况
                    try {
                        return URLDecoder.decode(encodedValue, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Move start to end of name.
            if (nextAmpersand != -1) {
                start = nextAmpersand + 1;
            } else {
                break;
            }
        } while (true);
        return null;
    }

    private static String getQuery(Uri data) {
        if (data == null) {
            return "";
        }
        String content = data.toString();
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        if (content.length() <= 0) {
            return "";
        }
        String str = content.substring(content.indexOf("?") + 1, content.length());
        if (!TextUtils.isEmpty(str)) {
            return str;
        } else {
            return "";
        }
    }


    /**
     * 根据传递的类型格式化时间
     *
     * @return
     */
    public static String getDateTimeByMillisecond(long time) {
        long currrentTime = time * 1000;
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(currrentTime);
    }

    /**
     * byte转MB,GB,KB
     *
     * @param size
     * @return
     */
    public static String getFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

    /**
     * （为了兼容老版本的<元>新版本红豆必须是100的整数倍）
     */
    public static boolean compatibleIsPriceAvailable(long goldPrice) {
        return goldPrice % 100 == 0;
    }

    /**
     * 不使用系统的UrlDecode直接解析，需要过滤特殊字符，不然会抛异常
     *
     * @param content
     */
    public static String urlDecode(String content) {
        String contentDecode = "";
        try {
            //防止解析时，带+、%报错
            content = content.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            content = content.replaceAll("\\+", "%2B");
            contentDecode = URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return contentDecode;
    }

    /**
     * 将 EditText 光标逻动到最后
     *
     * @param editText
     */
    public static void cursorToEnd(EditText editText) {
        if (editText == null) {
            return;
        }
        editText.setSelection(editText.getText().length());
    }

    /**
     * 根据服务端返回的特殊标识对字符串染色
     *
     * @param content
     * @param color
     */
    public static SpannableString coloringString(String content, @ColorInt int color) {
        if (content != null) {
            //不包含染色标识，直接返回
            if (!content.contains("<$") && !content.contains("$>")) {
                return new SpannableString(content);
            }
            int startIndex = content.indexOf("<$");
            int endIndex = content.indexOf("$>") - 2;
            //防止数组越界
            if (endIndex <= 0) {
                return new SpannableString(content);
            }
            String realContent = content.replace("<$", "").replace("$>", "");
            SpannableString spannableString = new SpannableString(realContent);
            spannableString.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
        return null;
    }

    /**
     * 获取剪切板内容
     */
    public static String getClipboardContent(Context context) {
        if (context == null) {
            return "";
        }
        ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        String content = "";
        if (cm != null) {
            ClipData data = cm.getPrimaryClip();
            if (data != null) {
                ClipData.Item item = data.getItemAt(0);
                if (item != null) {
                    if (!TextUtils.isEmpty(item.getText())) {
                        content = item.getText().toString();
                    }
                }
            }
        }
        return content;
    }

    /**
     * 判断字符串是否为纯数字
     */
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断坐标是否在rect区域内
     *
     * @param rect
     * @param x
     * @param y
     * @return
     */
    public static boolean isInRect(Rect rect, int x, int y) {
        if (x > rect.left && x < rect.right) {
            if (y > rect.top && y < rect.bottom) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打印调用堆栈信息
     */
    public static void printStackTrace(String tag) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement e : stackTrace) {
            Logger.d(tag, e.toString());
        }
    }
}
