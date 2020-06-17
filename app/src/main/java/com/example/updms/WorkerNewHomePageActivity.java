package com.example.updms;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.updms.fragment.HomeFragment;
import com.example.updms.fragment.LatestUpdateFragment;
import com.example.updms.fragment.SongsFragment;
import com.example.updms.rest.Response.ResponseModel;
import com.example.updms.rest.Response.VerifyWorker;
import com.example.updms.rest.Response.WorkerCount;
import com.example.updms.rest.Response.WorkerLoginData;
import com.example.updms.rest.RestCallBack;
import com.example.updms.rest.RestServiceFactory;
import com.example.updms.util.DialogFactory;
import com.example.updms.util.DialogUtils;
import com.example.updms.util.TempStorage;
import com.example.updms.util.ToastUtils;
import com.google.android.material.navigation.NavigationView;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.shawnlin.preferencesmanager.PreferencesManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class WorkerNewHomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WorkerLoginData loginData;
    private ProgressDialog progressDialog;
    private Context context;
    public static final int MY_PERMISSIONS_REQUEST = 101;

    private static Font catFont = FontFactory.getFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);// Set of font family alrady present with itextPdf library.
    private static Font redFont = FontFactory.getFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);
    private static Font subFont = FontFactory.getFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);
    private static Font smallBold = FontFactory.getFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);

    public static final String DEST = "/updms_Memebership";
    public static final String DESTSLIP = "/updms_payslip";
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = DialogFactory.createProgressDialog(context, context.getString(R.string.please_wait));
        loginData = TempStorage.getWorkerLoginData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.getMenu().performIdentifierAction(R.id.nav_home, 0);

        View header = navigationView.getHeaderView(0);
        TextView textAdmin = (TextView) header.findViewById(R.id.textViewAdmin);
        TextView textDate = (TextView) header.findViewById(R.id.textViewDate);
        ImageView imageView = (ImageView) header.findViewById(R.id.imageViewuser);

        Glide.with(context).load(TempStorage.getWorkerLoginData().getPhotoFileName()).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.mipmap.ic_launcher_round)).into(imageView);


        textAdmin.setText(loginData.getName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
        try {
            date = dateFormat.parse(TempStorage.getWorkerLoginData().getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
        String dateStr = formatter.format(date);
        textDate.setText(dateStr);
        Menu menuNav = navigationView.getMenu();


        menuNav.findItem(R.id.nav_nodal).setVisible(false);
        menuNav.findItem(R.id.nav_feestatus).setVisible(false);
        menuNav.findItem(R.id.nav_addads).setVisible(true);
        menuNav.findItem(R.id.nav_nirman).setVisible(false);
        menuNav.findItem(R.id.nav_domestic).setVisible(false);
        menuNav.findItem(R.id.nav_verify).setVisible(false);
        menuNav.findItem(R.id.nav_totalregistration).setVisible(false);
        menuNav.findItem(R.id.nav_cardvalidity).setVisible(false);
        menuNav.findItem(R.id.nav_registrationcard).setVisible(true);
        menuNav.findItem(R.id.nav_registrationcard).setVisible(true);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedFragment(item.getItemId());
        return true;
    }

    private void displaySelectedFragment(int menuItemId) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (menuItemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_latestupdate:
                fragment = new LatestUpdateFragment();
                break;
            case R.id.nav_songs:
                fragment = new SongsFragment();
                break;
            case R.id.nav_membershipcard:
                new DownloadImageTask(loginData).execute(loginData.getPhotoFileName());

                break;
            case R.id.nav_registrationcard:
                createPdfForSlip(loginData);
                break;

            case R.id.nav_helplineno:
                final List<String> items = new ArrayList<>();
                items.add("Police Control Room");
                items.add("Ambulance Helpline");
                items.add("Fire Control Room");
                items.add("Women's Helpline");
                items.add("Women's Helpline Uttar Pradesh");
                items.add("Adhar Helpline");
                items.add("Labour Helpline");


                DialogUtils.showBasicList(
                        context,
                        context.getString(R.string.please_choose_option),
                        items,
                        new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(final MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                String selectedItem = items.get(position);
                                if ("Police Control Room".equals(selectedItem)) {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + "112"));
                                    if (ContextCompat.checkSelfPermission(WorkerNewHomePageActivity.this,
                                            Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(WorkerNewHomePageActivity.this,
                                                new String[]{Manifest.permission.CALL_PHONE},
                                                101);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            startActivity(intent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                } else if("Ambulance Helpline".equals(selectedItem)){
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + "102"));
                                    if (ContextCompat.checkSelfPermission(WorkerNewHomePageActivity.this,
                                            Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(WorkerNewHomePageActivity.this,
                                                new String[]{Manifest.permission.CALL_PHONE},
                                                101);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            startActivity(intent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if("Fire Control Room".equals(selectedItem)){
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + "101"));
                                    if (ContextCompat.checkSelfPermission(WorkerNewHomePageActivity.this,
                                            Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(WorkerNewHomePageActivity.this,
                                                new String[]{Manifest.permission.CALL_PHONE},
                                                101);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            startActivity(intent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if("Women's Helpline".equals(selectedItem)){
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + "181"));
                                    if (ContextCompat.checkSelfPermission(WorkerNewHomePageActivity.this,
                                            Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(WorkerNewHomePageActivity.this,
                                                new String[]{Manifest.permission.CALL_PHONE},
                                                101);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            startActivity(intent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if("Women's Helpline Uttar Pradesh".equals(selectedItem)){
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + "1090"));
                                    if (ContextCompat.checkSelfPermission(WorkerNewHomePageActivity.this,
                                            Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(WorkerNewHomePageActivity.this,
                                                new String[]{Manifest.permission.CALL_PHONE},
                                                101);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            startActivity(intent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if("Adhar Helpline".equals(selectedItem)){
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + "18003001947"));
                                    if (ContextCompat.checkSelfPermission(WorkerNewHomePageActivity.this,
                                            Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(WorkerNewHomePageActivity.this,
                                                new String[]{Manifest.permission.CALL_PHONE},
                                                101);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            startActivity(intent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if("Labour Helpline".equals(selectedItem)){
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setData(Uri.parse("tel:" + "1800-180-5412"));
                                    if (ContextCompat.checkSelfPermission(WorkerNewHomePageActivity.this,
                                            Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(WorkerNewHomePageActivity.this,
                                                new String[]{Manifest.permission.CALL_PHONE},
                                                101);

                                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    } else {
                                        //You already have permission
                                        try {
                                            startActivity(intent);
                                        } catch(SecurityException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        });
                break;
            case R.id.nav_logout:
                TempStorage.setLoginData(null);
                TempStorage.loginData = null;
                PreferencesManager.clear();
                Intent intent = new Intent(WorkerNewHomePageActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            default:
                break;
        }

        //replace the current fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void hitApiTogetCount() {
        Call<ResponseModel<WorkerCount>> responseModelCall = RestServiceFactory.createService().getWorkerCount(TempStorage.getLoginData().getId());

        responseModelCall.enqueue(new RestCallBack<ResponseModel<WorkerCount>>() {
            @Override
            public void onFailure(Call<ResponseModel<WorkerCount>> call, String message) {
                progressDialog.dismiss();
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<WorkerCount>> call, Response<ResponseModel<WorkerCount>> restResponse, ResponseModel<WorkerCount> response) {
                if (RestCallBack.isSuccessFull(response)) {
                    progressDialog.dismiss();
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.add_service, null);
                    final TextView textViewTotalRegistration = (TextView) alertLayout.findViewById(R.id.textViewTotalRegistration);
                    final TextView textViewTotalMember = (TextView) alertLayout.findViewById(R.id.textViewTotalMember);

                    textViewTotalRegistration.setText(response.data.getTotal() + "");
                    textViewTotalMember.setText(response.data.getUnverified() + "");
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(false);
                    alert.setPositiveButton(context.getString(R.string.str_ok), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            }
        });

    }

    public void createPdfForSlip(WorkerLoginData likeViewUserModel) {
        if (!checkPermissions(true)) {
            return;
        }
        String root = Environment.getExternalStorageDirectory().toString();
        final File dir = new File(root);
        if (!dir.exists())
            dir.mkdirs();
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            PrintDocument(dir.getPath() + DEST + timestamp + ".pdf", likeViewUserModel, 1);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ProgressDialog mDialog;
        private WorkerLoginData likeViewUserModel;

        public DownloadImageTask(WorkerLoginData likeViewUserModel) {
            this.likeViewUserModel = likeViewUserModel;

        }

        protected void onPreExecute() {

            mDialog  = DialogFactory.createProgressDialog(context, context.getString(R.string.please_wait));
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", "image download error");
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            //set image of your imageview

            bitmap = result;
            //close
            mDialog.dismiss();
            createPdf(loginData);
        }
    }

    private boolean checkPermissions(boolean checkStorage) {
        ArrayList<String> arrPerm = new ArrayList<>();

        if (checkStorage && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            arrPerm.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!arrPerm.isEmpty()) {
            String[] permissions = new String[arrPerm.size()];
            permissions = arrPerm.toArray(permissions);

//            boolean shouldShowRationale = true;
//
//            for (String permission : arrPerm) {
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
//                    shouldShowRationale = false;
//                    break;
//                }
//            }
//
//            if (shouldShowRationale) {
//                final String[] finalPermissions = permissions;
//                DialogUtils.showBasic(context,
//                        context.getString(R.string.permission_get_images),
//                        context.getString(R.string.ok),
//                        new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                ActivityCompat.requestPermissions(activity, finalPermissions, MY_PERMISSIONS_REQUEST);
//                            }
//                        });
//            } else {
//                DialogUtils.showBasic(context,
//                        context.getString(R.string.permission_rational_get_images),
//                        context.getString(R.string.ok),
//                        new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                Utility.openAppSettings(context);
//                            }
//                        });
//            }
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST);
            return false;
        }
        return true;
    }


    public void PrintDocument(String dest, WorkerLoginData likeViewUserModel, int i) throws IOException, java.io.IOException {
        try {

//            Document document = new Document();
            Document document = new Document(PageSize.PENGUIN_SMALL_PAPERBACK, 10f, 10f, 30f, 0f);//PENGUIN_SMALL_PAPERBACK used to set the paper size
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
            addTitlePage(document, likeViewUserModel, i);
            document.close();

            File file = new File(dest);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setDataAndType(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file), "application/pdf");
            } else {
                Uri tempFileUri = Uri.fromFile(file);
                intent.setData(tempFileUri);
            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addTitlePage(Document document, WorkerLoginData likeViewUserModel, int i)
            throws DocumentException {
        if (i == 2) {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                image.setAlignment(Image.LEFT);
                image.scaleToFit(200,50);
                document.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Paragraph preface = new Paragraph();
            // We add one empty line
            addEmptyLine(preface, 1);
            // Lets write a big header

            Paragraph subPara = new Paragraph(context.getString(R.string.membership_card), catFont);
            // subCatPart = catPart.addSection(subPara);
            subPara.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(subPara);


            Paragraph subPara1 = new Paragraph(context.getString(R.string.str_dms_dmsl), catFont);
            // subCatPart = catPart.addSection(subPara);
            subPara1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(subPara1);


            // Will create: Report generated by: _name, _date
            Paragraph subPara2 = new Paragraph(context.getString(R.string.str_trade_union) + likeViewUserModel.getMembershipId(), smallBold);
            // subCatPart = catPart.addSection(subPara);
            subPara2.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(subPara2);


            preface.add(new Paragraph(
                    context.getString(R.string.validity) + likeViewUserModel.getRegFeeValidity(),
                    redFont));
            preface.add(new Paragraph(
                    context.getString(R.string.seriol_no) + likeViewUserModel.getMembershipId(),
                    redFont));
            preface.add(new Paragraph(
                    context.getString(R.string.str_dms_name) + " :" + likeViewUserModel.getName(),
                    redFont));
            preface.add(new Paragraph(
                    context.getString(R.string.father_husband) + likeViewUserModel.getFatherName(),
                    redFont));
            preface.add(new Paragraph(
                    context.getString(R.string.permanent_address) + likeViewUserModel.getPermanentAddress(),
                    redFont));
            preface.add(new Paragraph(
                    context.getString(R.string.local_address) + likeViewUserModel.getPermanentAddress(),
                    redFont));
            preface.add(new Paragraph(
                    context.getString(R.string.vyvsay) + likeViewUserModel.getOccupation(),
                    redFont));
            preface.add(new Paragraph(
                    context.getString(R.string.mobile_no) + " :" + likeViewUserModel.getPermanentAddress(),
                    redFont));
            addEmptyLine(preface, 1);
            document.add(preface);


            try {
                Drawable d = getResources().getDrawable(R.drawable.sign);
                BitmapDrawable bitDw = ((BitmapDrawable) d);
                Bitmap bmp = bitDw.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                image.setAlignment(Image.ALIGN_RIGHT);
                document.add(image);
            } catch (Exception e) {

            }
            Paragraph subPara3 = new Paragraph(context.getString(R.string.president), smallBold);
            // subCatPart = catPart.addSection(subPara);
            subPara3.setAlignment(Paragraph.ALIGN_RIGHT);
            subPara3.setIndentationRight(10);
            document.add(subPara3);
            // document.newPage();


            Paragraph subPara4 = new Paragraph(context.getString(R.string.rule_niyam), catFont);
            // subCatPart = catPart.addSection(subPara);
            subPara4.setAlignment(Paragraph.ALIGN_CENTER);
            addEmptyLine(subPara4, 1);
            document.add(subPara4);

            Paragraph subPara5 = new Paragraph(context.getString(R.string.card_back_1), smallBold);
            // subCatPart = catPart.addSection(subPara);
            document.add(subPara5);

            Paragraph subPara6 = new Paragraph(context.getString(R.string.card_back_2), smallBold);
            // subCatPart = catPart.addSection(subPara);
            document.add(subPara6);

            Paragraph subPara7 = new Paragraph(context.getString(R.string.card_back_3), smallBold);
            // subCatPart = catPart.addSection(subPara);
            document.add(subPara7);

            Paragraph subPara8 = new Paragraph(context.getString(R.string.card_back_4), smallBold);
            // subCatPart = catPart.addSection(subPara);
            document.add(subPara8);

            Paragraph subPara9 = new Paragraph(context.getString(R.string.card_back_5), smallBold);
            // subCatPart = catPart.addSection(subPara);
            document.add(subPara9);

            Paragraph subPara10 = new Paragraph(context.getString(R.string.card_back_6), smallBold);
            // subCatPart = catPart.addSection(subPara);
            addEmptyLine(subPara10, 1);
            document.add(subPara10);

//        Paragraph  subPara11 = new Paragraph("संतोष यादव", redFont);
//        // subCatPart = catPart.addSection(subPara);
//        document.add(subPara11);
//        Paragraph subPara12 = new Paragraph(context.getString(R.string.president), smallBold);
//        // subCatPart = catPart.addSection(subPara);
//        document.add(subPara12);
//        Paragraph subPara13 = new Paragraph("(9889175381)", redFont);
//        // subCatPart = catPart.addSection(subPara);
//        document.add(subPara13);


            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph p = new Paragraph("संतोष यादव", redFont);
            p.add(new Chunk(glue));
            p.add("राम नाथ");
            document.add(p);

            Chunk glue1 = new Chunk(new VerticalPositionMark());
            Paragraph p2 = new Paragraph(context.getString(R.string.president), smallBold);
            p2.add(new Chunk(glue1));
            p2.add(context.getString(R.string.secetry));
            document.add(p2);

            Chunk glue3 = new Chunk(new VerticalPositionMark());
            Paragraph p3 = new Paragraph("(9889175381)", smallBold);
            p3.add(new Chunk(glue3));
            p3.add("(9956956628)");
            document.add(p3);
        } else {

            // We add one empty line

            // Lets write a big header

            Paragraph subPara = new Paragraph(context.getString(R.string.str_slip), catFont);
            // subCatPart = catPart.addSection(subPara);
            subPara.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(subPara);


            Paragraph subPara1 = new Paragraph(context.getString(R.string.str_dms_dmsl), catFont);
            // subCatPart = catPart.addSection(subPara);
            subPara1.setAlignment(Paragraph.ALIGN_CENTER);
            addEmptyLine(subPara1, 1);
            document.add(subPara1);


            // Will create: Report generated by: _name, _date
            Paragraph subPara2 = new Paragraph(String.format(getString(R.string.str_note), likeViewUserModel.getName()), smallBold);
            // subCatPart = catPart.addSection(subPara);
            subPara2.setAlignment(Paragraph.ALIGN_CENTER);
            addEmptyLine(subPara2, 1);
            document.add(subPara2);

            Paragraph subPara3 = new Paragraph(context.getString(R.string.str_getter), smallBold);
            // subCatPart = catPart.addSection(subPara);
            subPara3.setAlignment(Paragraph.ALIGN_RIGHT);
            addEmptyLine(subPara3, 3);
            subPara3.setIndentationRight(10);
            document.add(subPara3);

            Paragraph subPara4 = new Paragraph(context.getString(R.string.str_signature) + "..............", smallBold);
            // subCatPart = catPart.addSection(subPara);
            subPara4.setAlignment(Paragraph.ALIGN_RIGHT);
            subPara4.setIndentationRight(10);
            addEmptyLine(subPara4, 1);
            document.add(subPara4);

            Paragraph subPara5 = new Paragraph(context.getString(R.string.str_post) + "..............", smallBold);
            // subCatPart = catPart.addSection(subPara);
            subPara5.setAlignment(Paragraph.ALIGN_RIGHT);
            subPara5.setIndentationRight(10);
            addEmptyLine(subPara5, 1);
            document.add(subPara5);

            Paragraph subPara6 = new Paragraph(context.getString(R.string.str_dinak) + likeViewUserModel.getRegFeeValidity(), smallBold);
            // subCatPart = catPart.addSection(subPara);
            subPara6.setAlignment(Paragraph.ALIGN_RIGHT);
            subPara6.setIndentationRight(10);
            addEmptyLine(subPara6, 1);
            document.add(subPara6);

            Paragraph subPara7 = new Paragraph(context.getString(R.string.str_memeersipid) + likeViewUserModel.getMembershipId(), smallBold);
            // subCatPart = catPart.addSection(subPara);
            addEmptyLine(subPara7, 1);
            document.add(subPara7);
        }

        // Start a new page
        // document.newPage();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void createPdf(WorkerLoginData likeViewUserModel) {
        if (!checkPermissions(true)) {
            return;
        }
        String root = Environment.getExternalStorageDirectory().toString();
        final File dir = new File(root);
        if (!dir.exists())
            dir.mkdirs();
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            PrintDocument(dir.getPath() + DEST + timestamp + ".pdf", likeViewUserModel, 2);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
