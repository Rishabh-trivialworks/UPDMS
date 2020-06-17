package com.example.updms.constants;

import android.content.res.Resources;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;


public class AppConstants {

    public static final int GPS_REQUEST = 1001;
    public static final int LOCATION_REQUEST = 1000;
    public static String registeredDeviceTokenforGCM="";

    public static class Pref {
        public static final String NAME = "ustadJi";
        public static final String LOGIN_MODEL_OBJECT = "login_detail_model_object";
        public static final String WORKER_LOGIN_MODEL_OBJECT = "worker_login_detail_model_object";
        public static final String USER_MODEL_OBJECT = "ustad_user_model_object";
        public static final String AUTH_TOKEN = "ustad_auth_token";
        public static final String OTP_STORE = "ustad_otp_store";
    }

    public static class Limits {
        public static final int PAGINATION_LIMIT_COMMENTS = 25;


    }


    public static class RequestCode {
        public static final int MY_PERMISSIONS_REQUEST = 101;
        public static final int VIDEO_TRIMMER_REQUEST = 102;
        public static final int MEDIA_PICKER_REQUEST = 103;
        public static final int DOCUMENT_FILES_REQUEST = 104;
        public static final int DOCUMENT_FILES_REQUEST_EXIST_MYU = 105;
        public static final int DOCUMENT_FILES_REQUEST_EXIST_MYU_SEARCH = 106;
        public static final int SCAN_CODE_REQUEST = 107;
    }

    public static class ApiParamValue {
        public static final String OBJECT_TYPE_NEWS = "news";
        public static final String AUTHENTICATION_ERROR = "401";
        public static final String FORCE_UPDATE_ERROR = "426";
        public static final String RESPONSE_ERROR = "5XX";
        public static final String SUCCESS_RESPONSE_CODE = "2XX";

        /**
         * for mediaName value
         ***/

    }




    public static class ApiParamKey {
        public static String DEBUG = "debug";
        public static final String USTADJI_AUTH_TOKEN = "x-auth";
        public static final String APP_LANGUAGE = "Content-Language";
        public static final String APP_TIMEZONE = "timeZone";
        public static final String APP_VERSION = "appVersion";

    }



    public static final class Url {

        public static String BASE_SERVICE_LIVE = "http://149.28.143.113:8000/";
        public static String BASE_SERVICE_BETA = "https://beta-api.ustadji.co/";
        public static String BASE_SERVICE_ALPHA = "https://qa-api.ustadji.co/";

        public static final String LOGIN = "api/v1/login";
        public static final String SIGNUP = "api/v1/user";
        public static final String CATEGORYLIST = "api/v1/getCategoryList";
        public static final String SUBCATEGORYLIST = "api/v1/getServiceList";
        public static final String ADDADRESS = "api/v1/address";
        public static final String PROVIDERLIST = "api/v1/getProviderList";
        public static final String SAVEBOOKING = "api/v1/booking";
        public static final String GETUSER = "api/v1/user";
        public static final String SENDFEEDBACK = "api/v1/help";
        public static final String LOGOUT = "api/v1/logout";
        public static final String CHANGEPASSWORD = "api/v1/change-password";
        public static final String INITIATEPAYMENT = "api/v1/booking-service/initiate-payment";
        public static final String FINALPAYMENT =  "api/v1/booking-service/payment";
        public static final String INITIATE = "api/v1/rsa/initiate";
        public static final String RSA ="api/v1/rsa";
        public static  final String RSAPayment ="api/v1/rsa/payment";
        public static final String RSAMAPAPI = "api/v1/rsa/request";
        public static final String UPDATEFCMTOKEN = "api/v1/updateToken";
        public static final String INITIALPAYMENT =  "api/v1/booking-service/initiate-booking";
        public static final String STATES ="api/v1/states";
        public static final String DIVISION ="api/v1/divisions";
        public static final String DISTRICT ="api/v1/districts";
        public static final String DISTRICTBYSTATE ="api/v1/districtsByState";
        public static final String TEHSIL ="api/v1/tehsiles";
        public static final String UPLOADDATA ="/api/v1/worker";
        public static final String WORKERLIST ="api/v1/worker-list";
        public static final String WORKERSATYAPAN ="api/v1/approveByNodal";
        public static final String WORKERCOUNT ="api/v1/worker-count";
        public static final String NODALLIST ="api/v1/nodalList";
        public static final String ADMINAPPROVAL ="api/v1/approveByAdmin";
        public static final String APPROVENODAL ="api/v1/approve-nodal";
        public static final String LATESTUPDATE ="api/v1/getUpdates";
        public static final String EXPIRYWORKERLIST ="api/v1/expired-workers";
        public static final String WORKERRENEW ="api/v1/renew";

    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    private static final Pattern USERNAME_PATTERN = Pattern
            .compile("^[a-zA-Z0-9_.]+$");



    public static boolean isEmailValid(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static boolean isUsernameValid(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }


    public static String getDate(long milliSeconds, String REQUIRED_DATETIME_FORMAT) {
        // Create a DateFormatter object for displaying date in specified format.
        String date;
        String time;
        SimpleDateFormat formatter = new SimpleDateFormat(REQUIRED_DATETIME_FORMAT);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        return formatter.format(calendar.getTime());
    }



    public static String getDateOnlyFromMilli(long createddate) {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(createddate);
        SimpleDateFormat chatHeaderFormatter = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        String attendanceDate = chatHeaderFormatter.format(time.getTime());
//        int hour = time.get(Calendar.HOUR);
//        int min = time.get(Calendar.MINUTE);
        return attendanceDate;
    }


    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
