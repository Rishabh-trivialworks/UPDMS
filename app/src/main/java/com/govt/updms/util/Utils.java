package com.govt.updms.util;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build.VERSION;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.govt.updms.R;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Utils {
    public static int check = 0;
    public static Dialog dialog;
    byte[] accImage;
    byte[] logoImage;
    byte[] photo;





    public static final boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static long milliseconds(String date) {
        try {
            long timeInMilliseconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("Date in milli :: ");
            sb.append(timeInMilliseconds);
            printStream.println(sb.toString());
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setDatePicker(Context context, final TextView tv_date, final String TAG) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder();
        sb.append(mYear);
        String str = "";
        sb.append(str);
        Log.d("@@@@SystemYEAR", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(mMonth + 1);
        sb2.append(str);
        Log.d("@@@@SystemmMonth", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(mDay);
        sb3.append(str);
        Log.d("@@@@System mDay", sb3.toString());
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                StringBuilder sb = new StringBuilder();
                sb.append(year);
                String str = "-";
                sb.append(str);
                sb.append(monthOfYear + 1);
                sb.append(str);
                sb.append(dayOfMonth);
                String selectedDate = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(TAG);
                sb2.append("@@@@selectedDate");
                Log.d(sb2.toString(), selectedDate);
                try {
                    Date newDate = format.parse(selectedDate);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(newDate);
                    sb3.append("");
                    Log.d("@@@@newDate", sb3.toString());
                    tv_date.setText(format.format(newDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                Log.d(">>>cancelDate", "LOL");
                tv_date.setHint("Select Date");
                tv_date.setText("");
            }
        });
        datePickerDialog.show();
    }

    public static void setDatePickerTemp(Context context, final TextView tv_date, final LinearLayout linearLayout, final String TAG) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder();
        sb.append(mYear);
        String str = "";
        sb.append(str);
        Log.d("@@@@SystemYEAR", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(mMonth + 1);
        sb2.append(str);
        Log.d("@@@@SystemmMonth", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(mDay);
        sb3.append(str);
        Log.d("@@@@System mDay", sb3.toString());
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                StringBuilder sb = new StringBuilder();
                sb.append(year);
                String str = "-";
                sb.append(str);
                sb.append(monthOfYear + 1);
                sb.append(str);
                sb.append(dayOfMonth);
                String selectedDate = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(TAG);
                sb2.append("@@@@selectedDate");
                Log.d(sb2.toString(), selectedDate);
                try {
                    Date newDate = format.parse(selectedDate);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(newDate);
                    sb3.append("");
                    Log.d("@@@@newDate", sb3.toString());
                    tv_date.setText(format.format(newDate));
                    linearLayout.setVisibility(View.VISIBLE);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                Log.d(">>>cancelDate", "LOL");
                tv_date.setHint("Select Date");
                tv_date.setText("");
                linearLayout.setVisibility(View.GONE);
            }
        });
        datePickerDialog.show();
    }

//    public static void setDatePickerTest(Context context, TextView tv_date_start, final TextView tv_date_end, String message, String TAG) {
//        Calendar c = Calendar.getInstance();
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
//        StringBuilder sb = new StringBuilder();
//        sb.append(mYear);
//        String str = "";
//        sb.append(str);
//        Log.d("@@@@SystemYEAR", sb.toString());
//        StringBuilder sb2 = new StringBuilder();
//        sb2.append(mMonth + 1);
//        sb2.append(str);
//        Log.d("@@@@SystemmMonth", sb2.toString());
//        StringBuilder sb3 = new StringBuilder();
//        sb3.append(mDay);
//        sb3.append(str);
//        Log.d("@@@@System mDay", sb3.toString());
//        final String str2 = TAG;
//        final TextView textView = tv_date_start;
//        final TextView textView2 = tv_date_end;
//        final String str3 = message;
//        final Context context2 = context;
//        DatePi r2 = new OnDateSetListener() {
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                String str = "";
//                String str2 = "Select Date";
//                String str3 = "LOL";
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//                StringBuilder sb = new StringBuilder();
//                sb.append(year);
//                String str4 = "-";
//                sb.append(str4);
//                sb.append(monthOfYear + 1);
//                sb.append(str4);
//                sb.append(dayOfMonth);
//                String selectedDate = sb.toString();
//                StringBuilder sb2 = new StringBuilder();
//                sb2.append(str2);
//                sb2.append("@@@@selectedDate");
//                Log.d(sb2.toString(), selectedDate);
//                try {
//                    String str5 = str2;
//                    char c = 65535;
//                    if (str5.hashCode() == -1147132446 && str5.equals("_tv_outcomedate")) {
//                        c = 0;
//                    }
//                    if (c == 0) {
//                        StringBuilder sb3 = new StringBuilder();
//                        sb3.append(textView.getText().toString());
//                        sb3.append(str3);
//                        Log.d(">>>@tv_date_start1", sb3.toString());
//                        StringBuilder sb4 = new StringBuilder();
//                        sb4.append(textView2.getText().toString());
//                        sb4.append(str3);
//                        Log.d(">>>tv_date_end2", sb4.toString());
//                        StringBuilder sb5 = new StringBuilder();
//                        sb5.append(textView.getText().toString());
//                        sb5.append("==");
//                        sb5.append(selectedDate);
//                        sb5.append(str3);
//                        Log.d(">>>tv_date_startNew3", sb5.toString());
//                        if (CommonMethods.compareBeforeDate(textView.getText().toString(), selectedDate)) {
//                            textView2.setText(format.format(format.parse(selectedDate)));
//                            return;
//                        }
//                        Utils.showToast(str3, context2);
//                        textView2.setHint(str2);
//                        textView2.setText(str);
//                        textView.setHint(str2);
//                        textView.setText(str);
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        DatePickerDialog datePickerDialog = new DatePickerDialog(context, r2, mYear, mMonth, mDay);
//        TextView textView3 = tv_date_end;
//        datePickerDialog.setOnCancelListener(new OnCancelListener() {
//            public void onCancel(DialogInterface dialogInterface) {
//                Log.d(">>>cancelDate", "LOL");
//                tv_date_end.setHint("Select Date");
//                tv_date_end.setText("");
//            }
//        });
//        datePickerDialog.show();
//    }

    public static String currentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String minusDayFromCurrentDate(int day) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.DAY_OF_MONTH, day);
        return s.format(new Date(cal.getTimeInMillis()));
    }


    public static void callFragment(Fragment fragment, Activity activity, int frame) {
        FragmentTransaction transaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left);
        transaction.replace(frame, fragment);
        transaction.commit();
    }

    public static void showToast(String msg, Context ctx) {
        try {
            new Builder(ctx).setMessage(msg).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String changeDateToMDY(String input) {
        String outputDateStr = "";
        try {
            outputDateStr = new SimpleDateFormat("MM-dd-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(input));
            Log.i("output", outputDateStr);
            return outputDateStr;
        } catch (ParseException p) {
            p.printStackTrace();
            return outputDateStr;
        }
    }

    public static String changeDateToY(String input) {
        String outputDateStr = "";
        try {
            outputDateStr = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM-dd-yyyy").parse(input));
            Log.i("output", outputDateStr);
            return outputDateStr;
        } catch (ParseException p) {
            p.printStackTrace();
            return outputDateStr;
        }
    }

    public static int findFrontFacingCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == 1) {
                return i;
            }
        }
        return -1;
    }


    public Bitmap getScaledBitmap(String picturePath, int width, int height) {
        Options sizeOptions = new Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, sizeOptions);
        int inSampleSize = calculateInSampleSize(sizeOptions, width, height);
        sizeOptions.inJustDecodeBounds = false;
        sizeOptions.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    private int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int heightRatio = Math.round(((float) height) / ((float) reqHeight));
        int widthRatio = Math.round(((float) width) / ((float) reqWidth));
        return heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    public static String generateUniqueName(String filename) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(filename);
        sb.append(timeStamp);
        return sb.toString();
    }



    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public static boolean isLangSupported(Context context, String text) {
        boolean res;
        String str = text;
        int sdk = VERSION.SDK_INT;
        float scale = context.getResources().getDisplayMetrics().density;
        Config conf = Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(200, 80, conf);
        Bitmap orig = bitmap.copy(conf, false);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(1);
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize((float) ((int) (14.0f * scale)));
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, text.length(), bounds);
        canvas.drawText(str, (float) ((bitmap.getWidth() - bounds.width()) / 2), (float) ((bitmap.getHeight() + bounds.height()) / 2), paint);
        if (sdk < 11) {
            res = orig != bitmap;
        } else {
            res = !orig.sameAs(bitmap);
        }
        orig.recycle();
        bitmap.recycle();
        return res;
    }

    public static void displayAlert(Activity activity, String titleText, String messageText) {
        new Builder(activity).setTitle(titleText).setMessage(messageText).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }

    public static void setDateForAgeCal(Context context, final EditText et_age, final EditText et_dob, final String TAG) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder();
        sb.append(mYear);
        String str = "";
        sb.append(str);
        Log.d("@@@@SystemYEAR", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(mMonth + 1);
        sb2.append(str);
        Log.d("@@@@SystemmMonth", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(mDay);
        sb3.append(str);
        Log.d("@@@@System mDay", sb3.toString());
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                StringBuilder sb = new StringBuilder();
                sb.append(year);
                String str = "-";
                sb.append(str);
                sb.append(monthOfYear + 1);
                sb.append(str);
                sb.append(dayOfMonth);
                String selectedDate = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(TAG);
                sb2.append("@@@@selectedDate");
                Log.d(sb2.toString(), selectedDate);
                try {
                    Date DateDOB = format.parse(selectedDate);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(DateDOB);
                    sb3.append("");
                    Log.d("@@@@newDate", sb3.toString());
                    String selectedDate2 = format.format(DateDOB);
                    et_dob.setText(selectedDate2);
                    Date DateCurrent = format.parse(Utils.currentDate());
                    int yearAge = Utils.monthsBetween(DateDOB, DateCurrent) / 12;
                    int monthsBetween = Utils.monthsBetween(DateDOB, DateCurrent) % 12;
                    if (Utils.currentDate().equals(selectedDate2)) {
                        et_age.setText("0");
                    } else {
                        et_age.setText(String.valueOf(yearAge));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                Log.d(">>>cancelDate", "LOL");
                et_dob.setHint("Select Date");
                String str = "";
                et_dob.setText(str);
                et_age.setText(str);
            }
        });
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    static int monthsBetween(Date a, Date b) {
        Calendar cal = Calendar.getInstance();
        if (a.before(b)) {
            cal.setTime(a);
        } else {
            cal.setTime(b);
            b = a;
        }
        int c = 0;
        while (cal.getTime().before(b)) {
            cal.add(Calendar.MONTH, 1);
            c++;
        }
        return c - 1;
    }

    public static String currentDay() {
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public static String currentMonth() {
        return String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    public static String currentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(0, 4);
    }

    public static String addDay(String oldDate, int numberOfDays) {
        String str = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(str);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(oldDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_YEAR, numberOfDays);
        return new SimpleDateFormat(str).format(new Date(c.getTimeInMillis()));
    }


    public static boolean compareBeforeDate(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date strDate1 = sdf.parse(date1);
            Date strDate2 = sdf.parse(date2);
            if (strDate1.before(strDate2) || strDate1.equals(strDate2)) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void hideSoftKeyboard(Activity activity,EditText editText) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
