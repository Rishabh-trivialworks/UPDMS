package com.govt.updms.rest.services;


import com.govt.updms.constants.AppConstants;
import com.govt.updms.rest.RequestModel.AddAddressRequest;
import com.govt.updms.rest.RequestModel.ChangepasswordRequest;
import com.govt.updms.rest.RequestModel.EditProfileUpdateRequest;
import com.govt.updms.rest.RequestModel.FamilyRequest;
import com.govt.updms.rest.RequestModel.FirebaseRequest;
import com.govt.updms.rest.RequestModel.HelpCenterRequestModel;
import com.govt.updms.rest.RequestModel.LoginRequest;
import com.govt.updms.rest.RequestModel.NodalRequest;
import com.govt.updms.rest.RequestModel.PaymentRSARequest;
import com.govt.updms.rest.RequestModel.PaymentRequest;
import com.govt.updms.rest.RequestModel.RegisterRequest;
import com.govt.updms.rest.RequestModel.RenewWorker;
import com.govt.updms.rest.RequestModel.SaveBookingModel;
import com.govt.updms.rest.RequestModel.VerifyAdminRequest;
import com.govt.updms.rest.RequestModel.VerifyNodalRequest;
import com.govt.updms.rest.Response.AddressList;
import com.govt.updms.rest.Response.BookNowResponse;
import com.govt.updms.rest.Response.CatagoryList;
import com.govt.updms.rest.Response.District;
import com.govt.updms.rest.Response.Division;
import com.govt.updms.rest.Response.LatestUpdate;
import com.govt.updms.rest.Response.LoginData;
import com.govt.updms.rest.Response.MapItem;
import com.govt.updms.rest.Response.MyBooking;
import com.govt.updms.rest.Response.NodalList;
import com.govt.updms.rest.Response.RSAList;
import com.govt.updms.rest.Response.ResponseModel;
import com.govt.updms.rest.Response.ServiceproviderList;
import com.govt.updms.rest.Response.States;
import com.govt.updms.rest.Response.SubCategoryList;
import com.govt.updms.rest.Response.Tehsil;
import com.govt.updms.rest.Response.UserData;
import com.govt.updms.rest.Response.VerifyWorker;
import com.govt.updms.rest.Response.WorkerCount;
import com.govt.updms.rest.Response.WorkerLoginData;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {


    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.LOGIN)
    Call<ResponseModel<LoginData>> login(@Body LoginRequest loginRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.LOGIN)
    Call<ResponseModel<WorkerLoginData>> workerLogin(@Body LoginRequest loginRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.SIGNUP)
    Call<ResponseModel<UserData>> signup(@Body RegisterRequest registerRequest);

    //
    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.CATEGORYLIST)
    Call<ResponseModel<List<CatagoryList>>> getCategoryList();

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.SUBCATEGORYLIST + "/{CategoryId}")
    Call<ResponseModel<SubCategoryList>> getSubCategoryList(@Path(ServiceConstants.CATEGORYID) int categoryid);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.ADDADRESS)
    Call<ResponseModel<AddressList>> addAddress(@Body AddAddressRequest addAddressRequest);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.ADDADRESS)
    Call<ResponseModel<List<AddressList>>> getAddress();

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.PROVIDERLIST)
    Call<ResponseModel<List<ServiceproviderList>>> getProviderList(@Query(ServiceConstants.OBJECTID) int objectId
            , @Query("serviceType") String serviceType);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.SAVEBOOKING)
    Call<ResponseModel<BookNowResponse>> savebooking(@Body SaveBookingModel saveBookingModel);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.SAVEBOOKING)
    Call<ResponseModel<List<MyBooking>>> myBookings();

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.GETUSER)
    Call<ResponseModel<UserData>> getUserDetails();


    @Headers("Content-type: application/json")
    @PUT(AppConstants.Url.GETUSER)
    Call<ResponseModel<UserData>> setUserDetails(@Body EditProfileUpdateRequest editProfileUpdateRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.SENDFEEDBACK)
    Call<ResponseModel<Map<String, String>>> sendHelpCenter(@Body HelpCenterRequestModel helpCenterRequestModel);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.LOGOUT)
    Call<ResponseModel<Map<String, String>>> logOut();

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.CHANGEPASSWORD)
    Call<ResponseModel<Map<String, String>>> ChangePassword(@Body ChangepasswordRequest changepasswordRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.INITIATEPAYMENT + "/{id}")
    Call<ResponseModel<Map<String, String>>> initiatePayment(@Path("id") int bookingid);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.FINALPAYMENT + "/{id}")
    Call<ResponseModel<MyBooking>> paymentToServer(@Path("id") String bookingid, @Body PaymentRequest paymentRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.INITIATE)
    Call<ResponseModel<Map<String, String>>> initiatePaymentSubscribe();

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.RSA)
    Call<ResponseModel<List<RSAList>>> rsaList();

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.RSAPayment)
    Call<ResponseModel<UserData>> paymentToRsaServer(@Body PaymentRSARequest paymentRequest);


    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.RSAMAPAPI + "/{id}")
    Call<ResponseModel<MapItem>> rsaMapApi(@Path("id") int rsaServiceKey);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.UPDATEFCMTOKEN)
    Call<ResponseModel<Map<String, String>>> UpdateFCMTen(@Body FirebaseRequest firebaseRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.INITIALPAYMENT + "/{id}")
    Call<ResponseModel<MyBooking>> paymentToInitialServer(@Path("id") int bookingid, @Body PaymentRequest paymentRequest);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.STATES)
    Call<ResponseModel<List<States>>> getStates();

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.DIVISION + "/{stateCode}")
    Call<ResponseModel<List<Division>>> getDivision(@Path("stateCode") int stateCode);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.DISTRICTBYSTATE + "/{stateCode}")
    Call<ResponseModel<List<District>>> getDistrictByState(@Path("stateCode") int stateCode);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.DISTRICT + "/{divisionCode}")
    Call<ResponseModel<List<District>>> getDistrict(@Path("divisionCode") int divisionCode);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.TEHSIL + "/{districtCode}")
    Call<ResponseModel<List<Tehsil>>> getTehsil(@Path("districtCode") int divisionCode);


    @Multipart
    @POST(AppConstants.Url.UPLOADDATA)
    Call<ResponseModel<WorkerLoginData>> uploadMediaImage(@Part MultipartBody.Part media,
                                                          @Part MultipartBody.Part media1,
                                                          @Part("Name") RequestBody name,
                                                          @Part("Gender") RequestBody gender,
                                                          @Part("DateOfBirth") RequestBody dob,
                                                          @Part("Caste") RequestBody caste,
                                                          @Part("Age") RequestBody age,
                                                          @Part("FatherName") RequestBody fathername,
                                                          @Part("HusbandName") RequestBody husbandname,
                                                          @Part("MotherName") RequestBody mothername,
                                                          @Part("WorkerType") RequestBody workertype,
                                                          @Part("PermanentAddress") RequestBody permanentaddress,
                                                          @Part("MobileNo") RequestBody mobileno,
                                                          @Part("EmergencyMobileNo") RequestBody emergencymobileno,
                                                          @Part("EmergencyRelation") RequestBody emergencyrelation,
                                                          @Part("LabourStoppageName") RequestBody labourstopage,
                                                          @Part("LabourRegStatus") RequestBody LabourRegStatus,
                                                          @Part("LabourRegNo") RequestBody LabourRegNo,
                                                          @Part("BOCW") RequestBody RegBOCW,
                                                          @Part("Pincode") RequestBody Pincode,
                                                          @Part("RegStatus") RequestBody RegStatus,
                                                          @Part("RegAge") RequestBody RegAge,
                                                          @Part("RegFeeStatus") RequestBody RegFeeStatus,
                                                          @Part("Occupation") RequestBody Occupation,
                                                          @Part("UrbanIdentityType") RequestBody UrbanIdentityType,
                                                          @Part("UrbanIdentityNo") RequestBody UrbanIdentityNo,
                                                          @Part("Status") RequestBody Status,
                                                          @Part("Education") RequestBody Education,
                                                          @Part("RegistrationStatus") RequestBody RegistrationStatus,
                                                          @Part("InsertedBy") RequestBody InsertedBy,
                                                          @Part("StateCode") RequestBody StateCode,
                                                          @Part("DistrictCode") RequestBody DistrictCode,
                                                          @Part("RegDistrictCode") RequestBody RegDistrictCode,
                                                          @Part("TemporaryAddress") RequestBody TemporaryAddress,
                                                          @Part("Family") List<FamilyRequest> faimly);


    @Multipart
    @PUT(AppConstants.Url.UPLOADDATA)
    Call<ResponseModel<VerifyWorker>> editWorkerdata(@Part MultipartBody.Part media,
                                                     @Part MultipartBody.Part media1,
                                                     @Part("Name") RequestBody name,
                                                     @Part("Gender") RequestBody gender,
                                                     @Part("DateOfBirth") RequestBody dob,
                                                     @Part("Caste") RequestBody caste,
                                                     @Part("Age") RequestBody age,
                                                     @Part("FatherName") RequestBody fathername,
                                                     @Part("HusbandName") RequestBody husbandname,
                                                     @Part("MotherName") RequestBody mothername,
                                                     @Part("WorkerType") RequestBody workertype,
                                                     @Part("PermanentAddress") RequestBody permanentaddress,
                                                     @Part("MobileNo") RequestBody mobileno,
                                                     @Part("EmergencyMobileNo") RequestBody emergencymobileno,
                                                     @Part("EmergencyRelation") RequestBody emergencyrelation,
                                                     @Part("LabourStoppageName") RequestBody labourstopage,
                                                     @Part("LabourRegStatus") RequestBody LabourRegStatus,
                                                     @Part("LabourRegNo") RequestBody LabourRegNo,
                                                     @Part("BOCW") RequestBody RegBOCW,
                                                     @Part("Pincode") RequestBody Pincode,
                                                     @Part("RegStatus") RequestBody RegStatus,
                                                     @Part("RegAge") RequestBody RegAge,
                                                     @Part("RegFeeStatus") RequestBody RegFeeStatus,
                                                     @Part("Occupation") RequestBody Occupation,
                                                     @Part("UrbanIdentityType") RequestBody UrbanIdentityType,
                                                     @Part("UrbanIdentityNo") RequestBody UrbanIdentityNo,
                                                     @Part("Status") RequestBody Status,
                                                     @Part("Education") RequestBody Education,
                                                     @Part("RegistrationStatus") RequestBody RegistrationStatus,
                                                     @Part("InsertedBy") RequestBody InsertedBy,
                                                     @Part("StateCode") RequestBody StateCode,
                                                     @Part("DistrictCode") RequestBody DistrictCode,
                                                     @Part("RegDistrictCode") RequestBody RegDistrictCode,
                                                     @Part("TemporaryAddress") RequestBody TemporaryAddress,
                                                     @Part("WorkerId") RequestBody wokerID
    );

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.WORKERLIST + "/{userId}")
    Call<ResponseModel<List<VerifyWorker>>> getWorkerList(@Path("userId") int userId);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.WORKERSATYAPAN)
    Call<ResponseModel<VerifyWorker>> verifyByNodal(@Body VerifyNodalRequest verifyNodalRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.WORKERRENEW)
    Call<ResponseModel<VerifyWorker>> verifyByRenew(@Body RenewWorker renewWorker);


    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.ADMINAPPROVAL)
    Call<ResponseModel<VerifyWorker>> verifyByAdmin(@Body VerifyAdminRequest verifyAdminRequest);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.WORKERCOUNT + "/{userId}")
    Call<ResponseModel<WorkerCount>> getWorkerCount(@Path("userId") int userId);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.NODALLIST)
    Call<ResponseModel<List<NodalList>>> getNodalList();

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.APPROVENODAL)
    Call<ResponseModel<NodalList>> approvenodal(@Body NodalRequest nodalRequest);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.LATESTUPDATE)
    Call<ResponseModel<List<LatestUpdate>>> getLatestUpdate();

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.EXPIRYWORKERLIST + "/{districtID}")
    Call<ResponseModel<List<VerifyWorker>>> getExpiryWorkerList(@Path("districtID") int userId);
}
