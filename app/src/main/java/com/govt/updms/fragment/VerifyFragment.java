package com.govt.updms.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.govt.updms.BuildConfig;
import com.govt.updms.R;
import com.govt.updms.SignUpActivity;
import com.govt.updms.adapter.VerifyListAdapter;
import com.govt.updms.rest.RequestModel.VerifyAdminRequest;
import com.govt.updms.rest.RequestModel.VerifyNodalRequest;
import com.govt.updms.rest.Response.ResponseModel;
import com.govt.updms.rest.Response.VerifyWorker;
import com.govt.updms.rest.RestCallBack;
import com.govt.updms.rest.RestServiceFactory;
import com.govt.updms.util.DialogFactory;
import com.govt.updms.util.DialogUtils;
import com.govt.updms.util.TempStorage;
import com.govt.updms.util.ToastUtils;

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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyFragment extends Fragment implements VerifyListAdapter.OnItemClickListener {


    private Activity activity;
    private Context context;
    private View viewRoot;
    @BindView(R.id.recyclerViewVerify)
    public RecyclerView recyclerViewVerify;
    @BindView(R.id.progressBarContent)
    public ProgressBar progressBarContent;

    private List<VerifyWorker> verifyWorkers = new ArrayList<>();
    private VerifyListAdapter verifyListAdapter;

    public static final int MY_PERMISSIONS_REQUEST = 101;

    private static Font catFont = FontFactory.getFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);// Set of font family alrady present with itextPdf library.
    private static Font redFont = FontFactory.getFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);
    private static Font subFont = FontFactory.getFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);
    private static Font smallBold = FontFactory.getFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);

    public static final String DEST = "/updms_Memebership";
    public static final String DESTSLIP = "/updms_payslip";
    private Bitmap bitmap;


    public VerifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewRoot = inflater.inflate(R.layout.fragment_verify, container, false);
        ButterKnife.bind(this, viewRoot);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        context = getActivity();
        getActivity().setTitle(R.string.str_list_of_workers_for_verify);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewVerify.setLayoutManager(mLayoutManager);
        recyclerViewVerify.setHasFixedSize(false);
        recyclerViewVerify.setNestedScrollingEnabled(true);
        recyclerViewVerify.setItemAnimator(new DefaultItemAnimator());
        verifyListAdapter = new VerifyListAdapter(context, verifyWorkers, 2, this);
        recyclerViewVerify.setAdapter(verifyListAdapter);
        progressBarContent.setVisibility(View.VISIBLE);
        hitApiTogetWorkerList();
    }

    private void hitApiTogetWorkerList() {

        Call<ResponseModel<List<VerifyWorker>>> responseModelCall = RestServiceFactory.createService().getWorkerList(TempStorage.getLoginData().getId());

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<VerifyWorker>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<VerifyWorker>>> call, String message) {
                progressBarContent.setVisibility(View.GONE);
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<VerifyWorker>>> call, Response<ResponseModel<List<VerifyWorker>>> restResponse, ResponseModel<List<VerifyWorker>> response) {
                progressBarContent.setVisibility(View.GONE);
                if (RestCallBack.isSuccessFull(response) && response.data != null && response.data.size() > 0) {

                    verifyListAdapter.addItems(response.data);
                    verifyListAdapter.notifyDataSetChanged();


                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                verifyListAdapter.replaceItem((VerifyWorker) data.getSerializableExtra("workerdata"));
                verifyListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(View viewRoot, View view, VerifyWorker likeViewUserModel, int position) {
        switch (view.getId()) {
            case R.id.imageViewEdit:
                Intent in = new Intent(context, SignUpActivity.class);
                in.putExtra("workerData", likeViewUserModel);
                if (likeViewUserModel.getWorkerType().equalsIgnoreCase("Domestic Worker")) {
                    in.putExtra("type", context.getString(R.string.str_domestic_worker));
                } else {
                    in.putExtra("type", context.getString(R.string.str_construction_worker));
                }
                startActivityForResult(in, 101);
                break;
            case R.id.layoutContent:
                if (!likeViewUserModel.isApprovedByNodal()) {
                    final List<String> items = new ArrayList<>();
                    items.add(getString(R.string.str_verify_fees_by_nodal));
                    items.add(getString(R.string.rejectvia_nodal));

                    DialogUtils.showBasicList(
                            context,
                            context.getString(R.string.please_choose_option),
                            items,
                            new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(final MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    String selectedItem = items.get(position);
                                    if (context.getString(R.string.str_verify_fees_by_nodal).equals(selectedItem)) {
                                        VerifyNodalRequest objVeriFy = new VerifyNodalRequest(TempStorage.getLoginData().getId(), false, likeViewUserModel.getWorkerId(), "");
                                        progressBarContent.setVisibility(View.VISIBLE);
                                        hitApiToVerifySatyapan(objVeriFy, 1, null);
                                    } else {
                                        DialogUtils.showEditInfoDialog(
                                                context,
                                                getString(R.string.enter_your_reason),
                                                context.getString(R.string.submit),
                                                new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        EditText editText = (EditText) dialog.findViewById(R.id.editText);

                                                        if (editText.getText().toString().trim().isEmpty()) {
                                                            ToastUtils.show(context, getString(R.string.enter_your_reason));
                                                            return;
                                                        }
                                                        VerifyNodalRequest objVeriFy = new VerifyNodalRequest(TempStorage.getLoginData().getId(), true, likeViewUserModel.getWorkerId(), editText.getText().toString().trim());
                                                        progressBarContent.setVisibility(View.VISIBLE);
                                                        hitApiToVerifySatyapan(objVeriFy, 1, null);

                                                    }
                                                });
                                    }
                                }
                            });
                } else if (!TempStorage.getLoginData().getUserType().equalsIgnoreCase("NODAL") && !likeViewUserModel.isApprovedByAdmin()) {
                    final List<String> items = new ArrayList<>();
                    items.add(getString(R.string.str_verify_fees_by_admin));
                    items.add(getString(R.string.rejectvia_admin));

                    DialogUtils.showBasicList(
                            context,
                            context.getString(R.string.please_choose_option),
                            items,
                            new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(final MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    String selectedItem = items.get(position);
                                    if (context.getString(R.string.str_verify_fees_by_admin).equals(selectedItem)) {
                                        VerifyAdminRequest verifyAdminRequest = new VerifyAdminRequest(TempStorage.getLoginData().getId(), likeViewUserModel.getWorkerId(), false, "");
                                        progressBarContent.setVisibility(View.VISIBLE);
                                        hitApiToVerifySatyapan(null, 2, verifyAdminRequest);
                                    } else {
                                        DialogUtils.showEditInfoDialog(
                                                context,
                                                getString(R.string.enter_your_reason),
                                                context.getString(R.string.submit),
                                                new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        EditText editText = (EditText) dialog.findViewById(R.id.editText);

                                                        if (editText.getText().toString().trim().isEmpty()) {
                                                            ToastUtils.show(context, getString(R.string.enter_your_reason));
                                                            return;
                                                        }
                                                        VerifyAdminRequest verifyAdminRequest = new VerifyAdminRequest(TempStorage.getLoginData().getId(), likeViewUserModel.getWorkerId(), true, editText.getText().toString());
                                                        progressBarContent.setVisibility(View.VISIBLE);
                                                        hitApiToVerifySatyapan(null, 2, verifyAdminRequest);

                                                    }
                                                });
                                    }
                                }
                            });
                } else if (likeViewUserModel.isRegFeeStatus()) {
                    final List<String> items = new ArrayList<>();
                    items.add(getString(R.string.str_print_fees_slip));
                    items.add(getString(R.string.str_print_membershsip_card));

                    DialogUtils.showBasicList(
                            context,
                            context.getString(R.string.please_choose_option),
                            items,
                            new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(final MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    String selectedItem = items.get(position);
                                    if (context.getString(R.string.str_print_fees_slip).equals(selectedItem)) {
                                        if (!likeViewUserModel.isApprovedByAdmin()) {
                                            DialogUtils.showBasicWarning(context, context.getString(R.string.str_not_verified_by_admin), new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                        } else {
                                            createPdfForSlip(likeViewUserModel);
                                        }
                                    } else if (context.getString(R.string.str_print_membershsip_card).equals(selectedItem)) {
                                        if (!likeViewUserModel.isApprovedByAdmin()) {
                                            DialogUtils.showBasicWarning(context, context.getString(R.string.str_not_verified_by_admin), new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                        } else {
                                            new DownloadImageTask(likeViewUserModel).execute(likeViewUserModel.getPhotoFileName());

                                        }
                                    }
                                }
                            });
                }
                break;
            default:
                break;
        }

    }

    private void hitApiToVerifySatyapan(VerifyNodalRequest objVeriFy, int fromWhere, VerifyAdminRequest objverifyAdmin) {
        Call<ResponseModel<VerifyWorker>> responseModelCall;
        if (fromWhere == 1) {
            responseModelCall = RestServiceFactory.createService().verifyByNodal(objVeriFy);
        } else {
            responseModelCall = RestServiceFactory.createService().verifyByAdmin(objverifyAdmin);
        }

        responseModelCall.enqueue(new RestCallBack<ResponseModel<VerifyWorker>>() {
            @Override
            public void onFailure(Call<ResponseModel<VerifyWorker>> call, String message) {
                ToastUtils.show(context, message);
                progressBarContent.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call<ResponseModel<VerifyWorker>> call, Response<ResponseModel<VerifyWorker>> restResponse, ResponseModel<VerifyWorker> response) {
                progressBarContent.setVisibility(View.GONE);
                if (RestCallBack.isSuccessFull(response)) {
                    verifyListAdapter.replaceItem(response.data);
                    verifyListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onItemLongClick(View viewRoot, View view, VerifyWorker likeViewUserModel, int position) {

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
            ActivityCompat.requestPermissions(getActivity(), permissions, MY_PERMISSIONS_REQUEST);
            return false;
        }
        return true;
    }

    public void PrintDocument(String dest, VerifyWorker likeViewUserModel, int i) throws IOException, java.io.IOException {
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


    private void addTitlePage(Document document, VerifyWorker likeViewUserModel, int i)
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

    public void createPdf(VerifyWorker likeViewUserModel) {
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

    public void createPdfForSlip(VerifyWorker likeViewUserModel) {
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
        private  VerifyWorker likeViewUserModel;

        public DownloadImageTask(VerifyWorker likeViewUserModel) {
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
            createPdf(likeViewUserModel);
        }
    }
}
