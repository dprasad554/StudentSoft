package com.geekhive.studentsoft.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class Prefs {
    static final String PREF_SPLASH_SCREEN = "splash_screen";
	static final String PREF_USER_FIRST_NAME = "user_first_name";
    static final String PREF_USER_LAST_NAME = "user_last_name";
    static final String PREF_USER_NAME = "user_name";
    static final String PREF_USER_PWD = "user_pwd";
	static final String PREF_USER_EMAIL = "user_email";
    static final String PREF_USER_MOBILE = "user_mobile";
    static final String PREF_USER_PIC_URL = "userPic_url";
    static final String PREF_USER_ID = "user_id";
    static final String PREF_REF_ID = "ref_id";
    static final String PREF_ORDER_ID = "order_id";
    static final String PREF_ADDRESS_ID = "address_id";
    static final String PREF_ADDRESS = "address";
    static final String PREF_TOTAL = "total";
    static final String PREF_ACC_EXISTS = "acc_exists";
    static final String PREF_GCM_TOKEN = "gcm_token";
    static final String PREF_OTP = "mob_otp";
    static final String PREF_WELCOME = "pref_welcome";
    static final String PREF_ADDRESSID = "pref_addressid";
    static final String PREF_USER_TYPE = "user_type";
    static final String PREF_USER_AUTHENTICATIONKEY = "auth_key";
    static final String PREF_USER_LOGIN = "login_guest";


    /* Android Info Generic Usecase*/

	static SharedPreferences getPrefs(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	public static void setUserFirstName(Context ctx, String s) {
		getPrefs(ctx).edit().putString(PREF_USER_FIRST_NAME, s).commit();
	}

    public static void setUserLastName(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_LAST_NAME, s).commit();
    }

    public static void setUserName(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_NAME, s).commit();
    }


	public static String getUserFirstName(Context ctx) {
		return getPrefs(ctx).getString(PREF_USER_FIRST_NAME, "");
	}

    public static String getUserLastName(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_LAST_NAME, "");
    }

    public static String getUserName(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setUserEmail(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_EMAIL, s).commit();
    }


    public static String getUserEmail(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static void setMobileNumber(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_MOBILE, s).commit();
    }


    public static String getMobileNumber(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_MOBILE, "");
    }


    public static void setUserProfileUrl(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_PIC_URL, s).commit();
    }

    public static String getUserProfileUrl(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_PIC_URL, "");
    }

    public static void setUserPwd(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_PWD, s).commit();
    }

    public static String getUserPwd(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_PWD, "");
    }
/* Global server user id */
    public static void setUserId(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_ID, s).commit();
    }

    public static String getUserId(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_ID, "");
    }
    public static void setPrefRefId(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_REF_ID, s).commit();
    }

    public static String getPrefRefId(Context ctx) {
        return getPrefs(ctx).getString(PREF_REF_ID, "");
    }

    public static void setUserType(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_TYPE, s).commit();
    }

    public static String getUserType(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_TYPE, "");
    }

    public static boolean hasUserIdGenerated(Context ctx){
        return !TextUtils.isEmpty(getUserId(ctx));
    }
    /* Android Info Generic Use case */


    public static void setGCMToken(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_GCM_TOKEN, s).commit();
    }

    public static String getGCMToken(Context ctx) {
        return getPrefs(ctx).getString(PREF_GCM_TOKEN, null);
    }

    public static void setAccExists(Context ctx, String username, boolean y) {
        if(username == null) return;
        getPrefs(ctx).edit().putBoolean(username, y).commit();
    }

    public static boolean getAccExists(Context ctx, String username) {
        if(username == null) return false;
        return getPrefs(ctx).getBoolean(username, false);
    }

    public static boolean getSplashScreenPref(Context ctx, boolean def) {
        return getPrefs(ctx).getBoolean(PREF_SPLASH_SCREEN, def);
    }

    public static void setSplashScreenPref(Context ctx, boolean value) {
        getPrefs(ctx).edit().putBoolean(PREF_SPLASH_SCREEN, value).commit();
    }

    public static boolean getUserNotificationPreference(final Context context) {
        return getPrefs(context).getBoolean("notification_alerts", true);
    }

    public static void saveDataWithKeyAndValue(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences("aina",context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDataFromKey(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences("aina",context.MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);
        return restoredText;

    }

    public static void setAddressId(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_ADDRESS_ID, s).commit();
    }


    public static String getAddressId(Context ctx) {
        return getPrefs(ctx).getString(PREF_ADDRESS_ID, "");
    }

    public static void setAddress(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_ADDRESS, s).commit();
    }


    public static String getAddress(Context ctx) {
        return getPrefs(ctx).getString(PREF_ADDRESS, "");
    }

    public static void setTotal(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_TOTAL, s).commit();
    }


    public static String getTotal(Context ctx) {
        return getPrefs(ctx).getString(PREF_TOTAL, "");
    }

    public static void setOrderId(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_ORDER_ID, s).commit();
    }


    public static String getOrderId(Context ctx) {
        return getPrefs(ctx).getString(PREF_ORDER_ID, "");
    }

    //static final String PREF_OTP = "mob_otp";

    public static void setPrefOtp(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_OTP, s).commit();
    }


    public static String getPrefOtp(Context ctx) {
        return getPrefs(ctx).getString(PREF_OTP, "");
    }

    public static void setPrefWelcome(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_WELCOME, s).commit();
    }


    public static String getPrefWelcome(Context ctx) {
        return getPrefs(ctx).getString(PREF_WELCOME, "");
    }


    public static void setPrefAddressId(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_ADDRESSID, s).commit();
    }


    public static String getPrefAddressId(Context ctx) {
        return getPrefs(ctx).getString(PREF_ADDRESSID, "");
    }
    public static void setPrefUserAuthenticationkey(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_AUTHENTICATIONKEY, s).commit();
    }
    public static String getPrefUserAuthenticationkey(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_AUTHENTICATIONKEY, "");
    }

    public static void setpreUser(Context ctx, String s) {
        getPrefs(ctx).edit().putString(PREF_USER_LOGIN, s).commit();
    }
    public static String getPrefUser(Context ctx) {
        return getPrefs(ctx).getString(PREF_USER_LOGIN, "");
    }

}