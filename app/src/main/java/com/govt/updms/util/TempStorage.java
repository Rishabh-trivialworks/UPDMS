package com.govt.updms.util;

;
import com.govt.updms.constants.AppConstants;
import com.govt.updms.rest.Response.LoginData;
import com.govt.updms.rest.Response.UserData;
import com.govt.updms.rest.Response.WorkerLoginData;
import com.shawnlin.preferencesmanager.PreferencesManager;

/**
 * Created by MyU10 on 1/4/2017.
 */

public class TempStorage {

    private static int userId;
    public static String authToken = "";
    public static int otpStore;


    public static String version = "not available";

    public static LoginData loginData;
    public static WorkerLoginData workerLoginData;
    public  static UserData userData;


    public static LoginData getLoginData() {
        return loginData;
    }


    public static WorkerLoginData getWorkerLoginData() {
        return workerLoginData;
    }
    public static void setLoginData(LoginData loginData) {
        PreferencesManager.putObject(AppConstants.Pref.LOGIN_MODEL_OBJECT, loginData);
        TempStorage.loginData = loginData;


    }
    public static void setWorkerLoginData(WorkerLoginData loginData) {
        PreferencesManager.putObject(AppConstants.Pref.WORKER_LOGIN_MODEL_OBJECT, loginData);
        TempStorage.workerLoginData = loginData;


    }

    public static UserData getUserData() {
        return userData;
    }

    public static void setUserData(UserData userData) {
        PreferencesManager.putObject(AppConstants.Pref.USER_MODEL_OBJECT, userData);
        TempStorage.userData=userData;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        PreferencesManager.putString(AppConstants.Pref.AUTH_TOKEN,authToken);
        TempStorage.authToken = authToken;
    }

    public static int getOtpStore() {
        return otpStore;
    }

    public static void setOtpStore(int otpStore) {
        PreferencesManager.putInt(AppConstants.Pref.OTP_STORE,otpStore);
        TempStorage.otpStore = otpStore;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        TempStorage.userId = userId;
    }


}
