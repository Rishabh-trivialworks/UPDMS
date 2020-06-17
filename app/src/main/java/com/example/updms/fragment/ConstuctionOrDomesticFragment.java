package com.example.updms.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.updms.BuildConfig;
import com.example.updms.HomeActivity;
import com.example.updms.LoginActivity;
import com.example.updms.MainActivity;
import com.example.updms.R;
import com.example.updms.SignUpActivity;
import com.example.updms.WorkerHomePageActivity;
import com.example.updms.WorkerNewHomePageActivity;
import com.example.updms.adapter.SpinAdapter;
import com.example.updms.constants.AppConstants;
import com.example.updms.rest.RequestModel.FamilyRequest;
import com.example.updms.rest.Response.District;
import com.example.updms.rest.Response.Division;
import com.example.updms.rest.Response.ResponseModel;
import com.example.updms.rest.Response.States;
import com.example.updms.rest.Response.Tehsil;
import com.example.updms.rest.Response.WorkerLoginData;
import com.example.updms.rest.RestCallBack;
import com.example.updms.rest.RestServiceFactory;
import com.example.updms.util.DialogFactory;
import com.example.updms.util.FileCompressor;
import com.example.updms.util.TempStorage;
import com.example.updms.util.ToastUtils;
import com.example.updms.util.Utils;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConstuctionOrDomesticFragment extends Fragment {


    private String workerType;
    private View view;

    static final int REQUEST_TAKE_PHOTO_user = 1;
    static final int REQUEST_TAKE_PHOTO_signature = 2;
    static final int REQUEST_GALLERY_PHOTO_user = 3;
    static final int REQUEST_GALLERY_PHOTO_signature = 4;
    private String[] array_type_ling, array_type_membership, array_type_idproof, array_type_employement, array_type_division, array_type_zila;
    @BindView(R.id.spinnerDistrict)
    Spinner spinnerDistrict;


    @BindView(R.id.spinneridentity)
    Spinner spinneridentity;
    @BindView(R.id.spinnerLing)
    Spinner spinnerLing;
    @BindView(R.id.spinnerMembership)
    Spinner spinnerMembership;
    @BindView(R.id.spinner_type)
    Spinner spinner_type;
    @BindView(R.id.spinnerState)
    Spinner spinnerState;
    @BindView(R.id.spinnerTypeOfEmploment)
    Spinner spinnerTypeOfEmploment;
    @BindView(R.id._ll_container)
    LinearLayout _ll_container;

    @BindView(R.id.plusButton)
    Button plusButton;

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.editTextFatherName)
    EditText editTextFatherName;
    @BindView(R.id.editTextMotherName)
    EditText editTextMotherName;
    @BindView(R.id.editTextHusbandName)
    EditText editTextHusbandName;
    @BindView(R.id.editTextTypeOfLabour)
    EditText editTextTypeOfLabour;
    @BindView(R.id.editTextPermanentAddress)
    EditText editTextPermanentAddress;
    @BindView(R.id.editTextTempAddress)
    EditText editTextTempAddress;

    @BindView(R.id.editTextPinCode)
    EditText editTextPinCode;
    @BindView(R.id.editTextMobileNo)
    EditText editTextMobileNo;
    @BindView(R.id.editTextEmergencyNo)
    EditText editTextEmergencyNo;
    @BindView(R.id.editTextRelation)
    EditText editTextRelation;
    @BindView(R.id.editTextAdda)
    EditText editTextAdda;
    @BindView(R.id.checkBoxRegistered)
    CheckBox checkBoxRegistered;
    @BindView(R.id.editTextRegistrationNo)
    EditText editTextRegistrationNo;
    @BindView(R.id.editTextBOCW)
    EditText editTextBOCW;
    @BindView(R.id.editTextMembershipOld)
    EditText editTextMembershipOld;
    @BindView(R.id.editTextPehchanNo)
    EditText editTextPehchanNo;
    @BindView(R.id.editTextCaste)
    EditText editTextCaste;
    @BindView(R.id.editTextDOB)
    EditText editTextDOB;
    @BindView(R.id.editTextAge)
    EditText editTextAge;
    @BindView(R.id.editTextQualtification)
    EditText editTextQualtification;

    @BindView(R.id.editTextBelowName)
    EditText editTextBelowName;
    @BindView(R.id.editTextBelowAdhar)
    EditText editTextBelowAdhar;
    @BindView(R.id.editTextBelowYear)
    EditText editTextBelowYear;
    @BindView(R.id.editTextBelowMonth)
    EditText editTextBelowMonth;
    @BindView(R.id.editTextBelowRelation)
    EditText editTextBelowRelation;
    @BindView(R.id.imageViewuser)
    ImageView imageViewuser;
    @BindView(R.id.imageViewSignature)
    ImageView imageViewSignature;

    @BindView(R.id.textViewRegistrationNo)
    TextView textViewRegistrationNo;

    @BindView(R.id.button_signup)
    Button buttonSignUp;


    @BindView(R.id.inputLayoutName)
    TextInputLayout inputLayoutName;
    @BindView(R.id.inputLayoutfatherName)
    TextInputLayout inputLayoutfatherName;
    @BindView(R.id.inputLayoutMotherName)
    TextInputLayout inputLayoutMotherName;
    @BindView(R.id.inputLayoutCaste)
    TextInputLayout inputLayoutCaste;

    @BindView(R.id.inputLayoutDOB)
    TextInputLayout inputLayoutDOB;
    @BindView(R.id.inputLayoutEmergencyNo)
    TextInputLayout inputLayoutEmergencyNo;
    @BindView(R.id.inputLayoutIdentityNo)
    TextInputLayout inputLayoutIdentityNo;
    @BindView(R.id.inputLayoutLabourAdda)
    TextInputLayout inputLayoutLabourAdda;
    @BindView(R.id.inputLayoutBOCW)
    TextInputLayout inputLayoutBOCW;
    @BindView(R.id.inputLayoutMembershipOld)
    TextInputLayout inputLayoutMembershipOld;
    @BindView(R.id.inputLayoutMobileNo)
    TextInputLayout inputLayoutMobileNo;
    @BindView(R.id.inputLayoutPermanent)
    TextInputLayout inputLayoutPermanent;
    @BindView(R.id.inputLayoutTemp)
    TextInputLayout inputLayoutTemp;
    @BindView(R.id.inputLayoutPincode)
    TextInputLayout inputLayoutPincode;


    @BindView(R.id.inputLayoutQualification)
    TextInputLayout inputLayoutQualification;


    @BindView(R.id.inputLayouttypeOfEmp)
    TextInputLayout inputLayouttypeOfEmp;
    @BindView(R.id.inputLayoutRelationWith)
    TextInputLayout inputLayoutRelationWith;
    @BindView(R.id.inputLayoutRegistrationNo)
    TextInputLayout inputLayoutRegistrationNo;


    List<TextView> allTtDOB = new ArrayList();
    List<TextView> allTtGender = new ArrayList();
    List<TextView> allTtRelation = new ArrayList();
    List<TextView> allTvAadharno = new ArrayList();
    List<TextView> allTvName = new ArrayList();

    Context context;
    File mPhotoFile;
    File mPhotoFileSignature;
    FileCompressor mCompressor;
    int districtID;
    private SpinAdapter adapterState, adapterDivision, adapterDistrict, adapterTehsil;
    private int selectedState, selectedDistrict, selectedDivision;
    private ProgressDialog progressDialog;

    boolean isFirstState,isFirstDistrict,isFirstDivision;
    public ConstuctionOrDomesticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_register, container, false);
        ButterKnife.bind(this, view);
        workerType = getArguments().getString("workerType");
        districtID = getArguments().getInt("district");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        editTextRegistrationNo.setVisibility(View.GONE);
        editTextBOCW.setVisibility(View.GONE);

        mCompressor = new FileCompressor(context);
        progressDialog = DialogFactory.createProgressDialog(context, context.getString(R.string.please_wait));
        String currentDateAndTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String updatedDate = currentDateAndTime.replaceAll("/", " ").replaceAll(":", "").replaceAll(" ", "");

        if (workerType.equalsIgnoreCase("construction")) {
            workerType = "Construction Worker";
            textViewRegistrationNo.setText("CON" + updatedDate);
            spinnerTypeOfEmploment.setVisibility(View.VISIBLE);
            editTextTypeOfLabour.setVisibility(View.GONE);
            getActivity().setTitle(R.string.str_construction_worker);

        } else {
            getActivity().setTitle(R.string.str_domestic_worker);
            workerType = "Domestic Worker";
            textViewRegistrationNo.setText("DOM" + updatedDate);
            spinnerTypeOfEmploment.setVisibility(View.GONE);
            editTextTypeOfLabour.setVisibility(View.VISIBLE);
            inputLayoutLabourAdda.setVisibility(View.GONE);
        }
        hitApiTogetState();


        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextFatherName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutfatherName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextMotherName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutMotherName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextTypeOfLabour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayouttypeOfEmp.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextPermanentAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutPermanent.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextTempAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutTemp.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextPinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutPincode.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutMobileNo.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextEmergencyNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutEmergencyNo.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextRelation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutRelationWith.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextRegistrationNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutRegistrationNo.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextBOCW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutBOCW.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextPehchanNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutIdentityNo.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextCaste.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutCaste.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextDOB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutDOB.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextQualtification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    inputLayoutQualification.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        checkBoxRegistered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editTextRegistrationNo.setVisibility(View.VISIBLE);
                    editTextBOCW.setVisibility(View.VISIBLE);
                } else {
                    editTextRegistrationNo.setVisibility(View.GONE);
                    editTextBOCW.setVisibility(View.GONE);
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(editTextBelowName.getText().toString().equals((Object) "")  || editTextBelowYear.getText().toString().equals((Object) "") || editTextBelowRelation.getText().toString().equals((Object) "")|| editTextBelowMonth.getText().toString().equals((Object) ""))) {
                    methodAddElement();
                    return;
                }
                if (editTextBelowName.getText().toString().equals((Object) "")) {
                    editTextBelowName.setError((CharSequence) "Empty!");
                    return;
                }
                if (editTextBelowAdhar.getText().toString().equals((Object) "")) {
                    editTextBelowAdhar.setError((CharSequence) "Empty!");
                    return;
                }
                if (editTextBelowYear.getText().toString().equals((Object) "")) {
                    editTextBelowYear.setError((CharSequence) "Empty!");
                    return;
                }
                if (editTextBelowMonth.getText().toString().equals((Object) "")) {
                    editTextBelowMonth.setError((CharSequence) "Empty!");
                    return;
                }
                if (editTextBelowRelation.getText().toString().equals((Object) ""))
                    editTextBelowRelation.setError((CharSequence) "Empty!");
            }
        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkvalidation()) {
                    progressDialog.show();
                    RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), mPhotoFile);
                    RequestBody mFileSignature = RequestBody.create(MediaType.parse("image/*"), mPhotoFileSignature);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("PhotoFileName", mPhotoFile.getName(), mFile);
                    MultipartBody.Part filePart2 = MultipartBody.Part.createFormData("Signature", mPhotoFileSignature.getName(), mFileSignature);

                    RequestBody requestName =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextName.getText().toString());
                    RequestBody requestGender =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), spinnerLing.getSelectedItem().toString());
                    RequestBody requestDob =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextDOB.getText().toString());
                    RequestBody requestCaste =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextCaste.getText().toString());
                    RequestBody requestAge =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextAge.getText().toString());
                    RequestBody requestfather =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextFatherName.getText().toString());
                    RequestBody requestHusband =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextHusbandName.getText().toString());
                    RequestBody requestMother =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextMotherName.getText().toString());
                    RequestBody requestWorkerType =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), workerType);
                    RequestBody requestPermanentAddress =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextPermanentAddress.getText().toString());
                    RequestBody requestTempAddress =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextTempAddress.getText().toString());
//                    RequestBody requestGram =
//                            RequestBody.create(
//                                    MediaType.parse("multipart/form-data"), editTextVillage.getText().toString());
//                    RequestBody requestpost =
//                            RequestBody.create(
//                                    MediaType.parse("multipart/form-data"), editTextPostOffce.getText().toString());
                    RequestBody requestMobileNo =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextMobileNo.getText().toString());
                    RequestBody requestEmergencyMobileno =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextEmergencyNo.getText().toString());
                    RequestBody requestEmergencyRelation =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextRelation.getText().toString());
                    RequestBody requestLabourStopage =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextAdda.getText().toString());
                    RequestBody requestLabourStatus =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), checkBoxRegistered.isSelected() ? "true" : "false");
                    RequestBody requestLabourRegNo = RequestBody.create(
                            MediaType.parse("multipart/form-data"), "");
                    RequestBody requestLabourBOCW = RequestBody.create(
                            MediaType.parse("multipart/form-data"), "");
                    if (checkBoxRegistered.isSelected()) {
                        requestLabourRegNo =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"), editTextRegistrationNo.getText().toString());
                        requestLabourBOCW =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"), editTextBOCW.getText().toString());
                    }
//                    RequestBody requestThana =
//                            RequestBody.create(
//                                    MediaType.parse("multipart/form-data"), editTextPoliceStation.getText().toString());
                    RequestBody requestpincode =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextPinCode.getText().toString());
                    RequestBody requestRegStatus =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), spinnerMembership.getSelectedItem().toString());
                    RequestBody requestRegAge =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextMembershipOld.getText().toString());
                    RequestBody requestRegFeeStatus =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), "true");
                    RequestBody requestOccupation =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), "occupation");
                    RequestBody requesturbanIdentitytype =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), spinneridentity.getSelectedItem().toString());
                    RequestBody requesturbanIdentityNo =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextPehchanNo.getText().toString());
                    RequestBody requestStatus =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), "status");
                    RequestBody requestEducation =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), editTextQualtification.getText().toString());
                    RequestBody requestRegistrationStatus =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), "अपूर्ण");
                    RequestBody requestInsertedBy =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), "NODAL");
                    RequestBody requestState =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), selectedState + "");

                    RequestBody requestDistrict =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), selectedDistrict + "");

                    RequestBody requestRegdistrictCode =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), selectedDistrict + "");

                    HashMap<String, List<RequestBody>> map = new HashMap<>();

                    ArrayList<RequestBody> requestBodyArrayList = new ArrayList<>();

                    List<FamilyRequest> descriptionListTop = new ArrayList<>();

                    for (int i = 0; i < allTvName.size(); i++) {
                        List<FamilyRequest> descriptionList = new ArrayList<>();

                        FamilyRequest familyRequest = new FamilyRequest(allTvName.get(i).getText().toString(), allTvAadharno.get(i).getText().toString(), allTtGender.get(i).getText().toString(), allTtDOB.get(i).getText().toString(), allTtRelation.get(i).getText().toString());
                        descriptionListTop.add(familyRequest);
//
                    }

                    Call<ResponseModel<WorkerLoginData>> responseModelCall = RestServiceFactory.createService().uploadMediaImage(filePart, filePart2, requestName, requestGender, requestDob, requestCaste, requestAge, requestfather, requestHusband, requestMother, requestWorkerType, requestPermanentAddress, requestMobileNo, requestEmergencyMobileno, requestEmergencyRelation, requestLabourStopage,requestLabourStatus, requestLabourRegNo,requestLabourBOCW, requestpincode, requestRegStatus, requestRegAge, requestRegFeeStatus, requestOccupation, requesturbanIdentitytype, requesturbanIdentityNo, requestStatus, requestEducation, requestRegistrationStatus, requestInsertedBy, requestState, requestDistrict, requestRegdistrictCode,requestTempAddress, descriptionListTop);

                    responseModelCall.enqueue(new RestCallBack<ResponseModel<WorkerLoginData>>() {
                        @Override
                        public void onFailure(Call<ResponseModel<WorkerLoginData>> call, String message) {
                            ToastUtils.show(context, message);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onResponse(Call<ResponseModel<WorkerLoginData>> call, Response<ResponseModel<WorkerLoginData>> restResponse, ResponseModel<WorkerLoginData> response) {
                            progressDialog.dismiss();
                            if (RestCallBack.isSuccessFull(response)) {
                                ToastUtils.show(context, "success");
                                Utils.callFragment(new HomeFragment(), getActivity(), R.id.container_frame);
                                return;
                                /*TempStorage.setWorkerLoginData(response.data);
                                Intent in = new Intent(context, WorkerHomePageActivity.class);
                                startActivity(in);*/
                            }
                        }
                    });

                }
            }
        });



        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.setDateForAgeCal(context, editTextAge, editTextDOB, "dob");
            }
        });

        array_type_ling = getResources().getStringArray(R.array.array_gender);
        array_type_membership = getResources().getStringArray(R.array.array_new_old);
        array_type_idproof = getResources().getStringArray(R.array.array_identity);
        array_type_division = new String[]{"Select your type", "NODAL", "ADMIN", "WORKER"};
        array_type_zila = new String[]{"Select your type", "NODAL", "ADMIN", "WORKER"};
        array_type_employement = getResources().getStringArray(R.array.array_employment);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                States states = (States) adapterState.getItem(position);
                selectedState = states.getStateCode();
                hitApitogetDistrict(states.getStateCode());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array_type_employement);
        dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeOfEmploment.setAdapter(dataAdapter7);
        spinnerTypeOfEmploment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                District states = (District) adapterDistrict.getItem(position);
                selectedDistrict = states.getDistrictCode();

                // hitApitogetDivision(states.getStateCode());
                setPostData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array_type_idproof);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneridentity.setAdapter(dataAdapter3);
        spinneridentity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array_type_ling);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLing.setAdapter(dataAdapter4);
        spinnerLing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array_type_membership);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMembership.setAdapter(dataAdapter5);
        spinnerMembership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray

                if (dataAdapter5.getItem(position).equalsIgnoreCase("पुरानी")) {
                    editTextMembershipOld.setVisibility(View.VISIBLE);
                } else {
                    editTextMembershipOld.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array_type_ling);
        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(dataAdapter6);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        imageViewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Take Photo", "Choose from Library",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(items, (dialog, item) -> {
                    if (items[item].equals("Take Photo")) {
                        requestStoragePermission(true, 1);
                    } else if (items[item].equals("Choose from Library")) {
                        requestStoragePermission(false, 1);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        imageViewSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Take Photo", "Choose from Library",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(items, (dialog, item) -> {
                    if (items[item].equals("Take Photo")) {
                        requestStoragePermission(true, 2);
                    } else if (items[item].equals("Choose from Library")) {
                        requestStoragePermission(false, 2);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    private void setPostData() {
        for (int i = 0; i < adapterState.getCount(); i++) {
            if (((States) adapterState.getItem(i)).getStateCode() == TempStorage.getLoginData().getDistrict().getDivision().getState().getStateCode()) {
                if(!isFirstState) {
                    spinnerState.setSelection(i);
                    isFirstState = true;
                }

            }
        }

        for (int i = 0; i < adapterDistrict.getCount(); i++) {
            if (((District) adapterDistrict.getItem(i)).getDistrictCode() == TempStorage.getLoginData().getDistrict().getDistrictCode()) {
                if(!isFirstDistrict) {
                    spinnerDistrict.setSelection(i);
                    isFirstDistrict = true;
                }
            }
        }



    }

    private boolean checkvalidation() {

        if (editTextName.getText().toString().equalsIgnoreCase("")) {
            inputLayoutName.setError("अपना नाम भरे");
            editTextName.requestFocus();
            return false;
        } else if (editTextFatherName.getText().toString().equalsIgnoreCase("")) {
            inputLayoutfatherName.setError("पिता का नाम भरें");
            editTextFatherName.requestFocus();
            return false;
        } else if (editTextMotherName.getText().toString().equalsIgnoreCase("")) {
            inputLayoutMotherName.setError("माता का नाम भरें");
            editTextMotherName.requestFocus();
            return false;
        } else if (workerType.equalsIgnoreCase("domestic") && editTextTypeOfLabour.getText().toString().equalsIgnoreCase("")) {
            inputLayouttypeOfEmp.setError("श्रमिक के रोजगार का प्रकार भरें");
            editTextTypeOfLabour.requestFocus();
            return false;
        } else if (!workerType.equalsIgnoreCase("domestic") && spinnerTypeOfEmploment.getSelectedItem().toString().equalsIgnoreCase("")) {
            return false;
        } else if (editTextPermanentAddress.getText().toString().equalsIgnoreCase("")) {
            inputLayoutPermanent.setError("कृपया अपना स्थायी पता दर्ज करें");
            editTextPermanentAddress.requestFocus();
            return false;
        }
        else if (editTextTempAddress.getText().toString().equalsIgnoreCase("")) {
            inputLayoutTemp.setError("कृपया अपना अस्थायी पता दर्ज करें");
            editTextTempAddress.requestFocus();
            return false;
        }
//        else if (editTextVillage.getText().toString().equalsIgnoreCase("")) {
//            inputLayoutVillage.setError("गांव भरें ");
//            editTextVillage.requestFocus();
//            return false;
//        } else if (editTextPostOffce.getText().toString().equalsIgnoreCase("")) {
//            inputLayoutPost.setError("please enter your post office");
//            editTextPostOffce.requestFocus();
//            return false;
//        } else if (editTextPoliceStation.getText().toString().equalsIgnoreCase("")) {
//            inputLayoutThana.setError("थाना का नाम भरें ");
//            editTextPoliceStation.requestFocus();
//            return false;
//        }
        else if (editTextPinCode.getText().toString().equalsIgnoreCase("")) {
            inputLayoutPincode.setError("पिन कोड भरें ");
            editTextPinCode.requestFocus();
            return false;
        } else if (editTextMobileNo.getText().toString().equalsIgnoreCase("")) {
            inputLayoutMobileNo.setError("मोबाइल नंबर भरें");
            editTextMobileNo.requestFocus();
            return false;
        } else if (editTextEmergencyNo.getText().toString().equalsIgnoreCase("")) {
            inputLayoutEmergencyNo.setError("इमरजेंसी नंबर भरें ");
            editTextEmergencyNo.requestFocus();
            return false;
        } else if (editTextRelation.getText().toString().equalsIgnoreCase("")) {
            inputLayoutRelationWith.setError("व्यक्ति से सम्बन्ध भरें");
            editTextRelation.requestFocus();
            return false;
        } else if (workerType.equalsIgnoreCase("construction") && editTextAdda.getText().toString().equalsIgnoreCase("")) {
            inputLayoutLabourAdda.setError("लेबर अड्डा का नाम भरें");
            editTextAdda.requestFocus();
            return false;
        } else if (checkBoxRegistered.isSelected() && editTextRegistrationNo.getText().toString().equalsIgnoreCase("")&& editTextBOCW.getText().toString().equalsIgnoreCase("")) {
            inputLayoutRegistrationNo.setError("please enter your registration no. and registration date");
            editTextRegistrationNo.requestFocus();
            return false;
        } else if (spinnerMembership.getSelectedItem().toString().equalsIgnoreCase("पुरानी") && editTextMembershipOld.getText().toString().equalsIgnoreCase("")) {
            inputLayoutMembershipOld.setError("please enter your membership age");
            editTextMembershipOld.requestFocus();
            return false;
        } else if (editTextPehchanNo.getText().toString().equalsIgnoreCase("")) {
            inputLayoutIdentityNo.setError("please enter your identity no.");
            editTextPehchanNo.requestFocus();
            return false;
        } else if (editTextCaste.getText().toString().equalsIgnoreCase("")) {
            inputLayoutCaste.setError("जाती भरें ");
            editTextCaste.requestFocus();
            return false;
        } else if (editTextDOB.getText().toString().equalsIgnoreCase("")) {
            inputLayoutDOB.setError("जन्म तिथि भरें");
            editTextDOB.requestFocus();
            return false;
        } else if (editTextAge.getText().toString().equalsIgnoreCase("")) {
            ToastUtils.show(context, "please enter your age in years");
            return false;
        } else if (editTextQualtification.getText().toString().equalsIgnoreCase("")) {
            inputLayoutQualification.setError("शैक्षिक योग्यता भरें ");
            editTextQualtification.requestFocus();
            return false;
        } else if (mPhotoFile == null) {
            ToastUtils.show(context, "अपनी फोटो अपलोड करें ");
            return false;
        } else if (mPhotoFileSignature == null) {
            ToastUtils.show(context, "अपना हस्ताक्षर अपलोड करें ");
            return false;
        }

        return true;
    }




    private void hitApitogetDistrict(int stateCode) {
        Call<ResponseModel<List<District>>> responseModelCall = RestServiceFactory.createService().getDistrictByState(stateCode);

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<District>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<District>>> call, String message) {
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<District>>> call, Response<ResponseModel<List<District>>> restResponse, ResponseModel<List<District>> response) {
                if (RestCallBack.isSuccessFull(response)) {
                    adapterDistrict = new SpinAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item,
                            response.data, 3);
                    spinnerDistrict.setAdapter(adapterDistrict);

                }
            }
        });
    }

    private void hitApitogettehsil(int stateCode) {
        Call<ResponseModel<List<Tehsil>>> responseModelCall = RestServiceFactory.createService().getTehsil(stateCode);

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<Tehsil>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<Tehsil>>> call, String message) {
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<Tehsil>>> call, Response<ResponseModel<List<Tehsil>>> restResponse, ResponseModel<List<Tehsil>> response) {
                if (RestCallBack.isSuccessFull(response)) {
                    adapterTehsil = new SpinAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item,
                            response.data, 4);
                    spinnerDistrict.setAdapter(adapterTehsil);

                }
            }
        });
    }

    private void hitApiTogetState() {

        Call<ResponseModel<List<States>>> responseModelCall = RestServiceFactory.createService().getStates();

        responseModelCall.enqueue(new RestCallBack<ResponseModel<List<States>>>() {
            @Override
            public void onFailure(Call<ResponseModel<List<States>>> call, String message) {
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<List<States>>> call, Response<ResponseModel<List<States>>> restResponse, ResponseModel<List<States>> response) {
                if (RestCallBack.isSuccessFull(response) && response.data != null) {
                    for (int i = 0; i < response.data.size(); i++) {
                        if (response.data.get(i).getStateCode() == 9) {
                            response.data.set(0, response.data.get(i));
                        }
                    }
                    adapterState = new SpinAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item,
                            response.data, 1);
                    spinnerState.setAdapter(adapterState);

                }
            }
        });


    }

    private void requestStoragePermission(boolean isCamera, int from) {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent(from);
                            } else {
                                dispatchGalleryIntent(from);
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(context, "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();

    }

    /**
     * Capture image from camera
     */
    private void dispatchTakePictureIntent(int from) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                if (from == 1) {
                    mPhotoFile = photoFile;
                } else {
                    mPhotoFileSignature = photoFile;
                }

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (from == 1) {
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_user);
                } else {
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_signature);

                }

            }
        }
    }


    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent(int from) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (from == 1) {
            startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO_user);
        } else {
            startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO_signature);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO_user) {
                try {

                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(context).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.mipmap.ic_photo)).into(imageViewuser);

            } else if (requestCode == REQUEST_TAKE_PHOTO_signature) {
                try {

                    mPhotoFileSignature = mCompressor.compressToFile(mPhotoFileSignature);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(context).load(mPhotoFileSignature).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.mipmap.ic_photo)).into(imageViewSignature);

            } else if (requestCode == REQUEST_GALLERY_PHOTO_user) {
                Uri selectedImage = data.getData();
                try {

                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(context).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.mipmap.ic_photo)).into(imageViewuser);


            } else if (requestCode == REQUEST_GALLERY_PHOTO_signature) {
                Uri selectedImage = data.getData();
                try {

                    mPhotoFileSignature = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(context).load(mPhotoFileSignature).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.mipmap.ic_photo)).into(imageViewSignature);


            }
        }
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public void methodAddElement() {
        final View addView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row, null);
        TextView _tv_row_name = (TextView) addView.findViewById(R.id._tv_row_name);
        TextView _tv_row_aadharno = (TextView) addView.findViewById(R.id._tv_row_aadharno);
        TextView _tv_row_gender = (TextView) addView.findViewById(R.id._tv_row_gender);
        TextView _tv_row_year = (TextView) addView.findViewById(R.id._tv_row_year);
        TextView _tv_row_month = (TextView) addView.findViewById(R.id._tv_row_month);
        TextView _tv_row_relationship = (TextView) addView.findViewById(R.id._tv_row_relationship);
        TextView _tv_row_remove = (TextView) addView.findViewById(R.id._tv_row_remove);
        _tv_row_name.setText(editTextBelowName.getText().toString());
        _tv_row_aadharno.setText(editTextBelowAdhar.getText().toString());
        _tv_row_gender.setText(spinner_type.getSelectedItem().toString());
        _tv_row_year.setText(editTextBelowYear.getText().toString());
        _tv_row_month.setText(editTextBelowMonth.getText().toString());
        _tv_row_relationship.setText(editTextBelowRelation.getText().toString());
        _tv_row_remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((LinearLayout) addView.getParent()).removeView(addView);
            }
        });
        allTvName.add(_tv_row_name);
        allTvAadharno.add(_tv_row_aadharno);
        allTtGender.add(_tv_row_gender);
        allTtDOB.add(_tv_row_year);
        allTtRelation.add(_tv_row_relationship);
        _ll_container.addView(addView);
        String str = "";
        editTextBelowName.setText(str);
        editTextBelowAdhar.setText(str);
        editTextBelowRelation.setText(str);
        editTextBelowYear.setText(str);
        editTextBelowYear.setHint("Select Year");
        editTextBelowMonth.setText(str);
        editTextBelowMonth.setHint("Select Month");
    }


}
