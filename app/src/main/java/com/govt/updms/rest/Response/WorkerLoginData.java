
package com.govt.updms.rest.Response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkerLoginData implements Serializable {

    @SerializedName("WorkerId")
    @Expose
    private int workerId;
    @SerializedName("MembershipId")
    @Expose
    private String membershipId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Caste")
    @Expose
    private String caste;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("Age")
    @Expose
    private int age;
    @SerializedName("FatherName")
    @Expose
    private String fatherName;
    @SerializedName("HusbandName")
    @Expose
    private String husbandName;
    @SerializedName("MotherName")
    @Expose
    private String motherName;
    @SerializedName("WorkerType")
    @Expose
    private String workerType;
    @SerializedName("PermanentAddress")
    @Expose
    private String permanentAddress;
    @SerializedName("Gram")
    @Expose
    private String gram;
    @SerializedName("Post")
    @Expose
    private String post;
    @SerializedName("Thana")
    @Expose
    private String thana;
    @SerializedName("Pincode")
    @Expose
    private int pincode;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("EmergencyMobileNo")
    @Expose
    private String emergencyMobileNo;
    @SerializedName("EmergencyRelation")
    @Expose
    private String emergencyRelation;
    @SerializedName("LabourStoppageName")
    @Expose
    private String labourStoppageName;
    @SerializedName("LabourRegStatus")
    @Expose
    private boolean labourRegStatus;
    @SerializedName("LabourRegNo")
    @Expose
    private String labourRegNo;
    @SerializedName("RegStatus")
    @Expose
    private String regStatus;
    @SerializedName("RegAge")
    @Expose
    private int regAge;
    @SerializedName("RegFeeStatus")
    @Expose
    private boolean regFeeStatus;
    @SerializedName("Occupation")
    @Expose
    private String occupation;
    @SerializedName("UrbanIdentityType")
    @Expose
    private String urbanIdentityType;
    @SerializedName("UrbanIdentityNo")
    @Expose
    private String urbanIdentityNo;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("PhotoFileName")
    @Expose
    private String photoFileName;
    @SerializedName("Signature")
    @Expose
    private String signature;
    @SerializedName("Education")
    @Expose
    private String education;
    @SerializedName("ApprovedByNodal")
    @Expose
    private boolean approvedByNodal;
    @SerializedName("ApprovedByAdmin")
    @Expose
    private boolean approvedByAdmin;
    @SerializedName("RegistrationStatus")
    @Expose
    private String registrationStatus;
    @SerializedName("InsertedBy")
    @Expose
    private String insertedBy;
    @SerializedName("UpdatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("ApprovedByNodalDate")
    @Expose
    private Object approvedByNodalDate;
    @SerializedName("ApprovedByAdminDate")
    @Expose
    private Object approvedByAdminDate;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getRegFeeValidity() {
        return RegFeeValidity;
    }

    public void setRegFeeValidity(String regFeeValidity) {
        RegFeeValidity = regFeeValidity;
    }

    @SerializedName("RegFeeValidity")
    @Expose
    private String RegFeeValidity;
    @SerializedName("adminReason")
    @Expose
    private String adminReason;
    @SerializedName("StateCode")
    @Expose
    private int stateCode;
    @SerializedName("DivisionCode")
    @Expose
    private int divisionCode;
    @SerializedName("DistrictCode")
    @Expose
    private int districtCode;
    @SerializedName("TehsileCode")
    @Expose
    private Object tehsileCode;
    @SerializedName("RegDistrictCode")
    @Expose
    private int regDistrictCode;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("tehsile")
    @Expose
    private Object tehsile;
    @SerializedName("division")
    @Expose
    private Division division;
    @SerializedName("families")
    @Expose
    private List<Family> families = null;

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getGram() {
        return gram;
    }

    public void setGram(String gram) {
        this.gram = gram;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getThana() {
        return thana;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmergencyMobileNo() {
        return emergencyMobileNo;
    }

    public void setEmergencyMobileNo(String emergencyMobileNo) {
        this.emergencyMobileNo = emergencyMobileNo;
    }

    public String getEmergencyRelation() {
        return emergencyRelation;
    }

    public void setEmergencyRelation(String emergencyRelation) {
        this.emergencyRelation = emergencyRelation;
    }

    public String getLabourStoppageName() {
        return labourStoppageName;
    }

    public void setLabourStoppageName(String labourStoppageName) {
        this.labourStoppageName = labourStoppageName;
    }

    public boolean isLabourRegStatus() {
        return labourRegStatus;
    }

    public void setLabourRegStatus(boolean labourRegStatus) {
        this.labourRegStatus = labourRegStatus;
    }

    public String getLabourRegNo() {
        return labourRegNo;
    }

    public void setLabourRegNo(String labourRegNo) {
        this.labourRegNo = labourRegNo;
    }

    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    public int getRegAge() {
        return regAge;
    }

    public void setRegAge(int regAge) {
        this.regAge = regAge;
    }

    public boolean isRegFeeStatus() {
        return regFeeStatus;
    }

    public void setRegFeeStatus(boolean regFeeStatus) {
        this.regFeeStatus = regFeeStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUrbanIdentityType() {
        return urbanIdentityType;
    }

    public void setUrbanIdentityType(String urbanIdentityType) {
        this.urbanIdentityType = urbanIdentityType;
    }

    public String getUrbanIdentityNo() {
        return urbanIdentityNo;
    }

    public void setUrbanIdentityNo(String urbanIdentityNo) {
        this.urbanIdentityNo = urbanIdentityNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public boolean isApprovedByNodal() {
        return approvedByNodal;
    }

    public void setApprovedByNodal(boolean approvedByNodal) {
        this.approvedByNodal = approvedByNodal;
    }

    public boolean isApprovedByAdmin() {
        return approvedByAdmin;
    }

    public void setApprovedByAdmin(boolean approvedByAdmin) {
        this.approvedByAdmin = approvedByAdmin;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getApprovedByNodalDate() {
        return approvedByNodalDate;
    }

    public void setApprovedByNodalDate(Object approvedByNodalDate) {
        this.approvedByNodalDate = approvedByNodalDate;
    }

    public Object getApprovedByAdminDate() {
        return approvedByAdminDate;
    }

    public void setApprovedByAdminDate(Object approvedByAdminDate) {
        this.approvedByAdminDate = approvedByAdminDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public int getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(int divisionCode) {
        this.divisionCode = divisionCode;
    }

    public int getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(int districtCode) {
        this.districtCode = districtCode;
    }

    public Object getTehsileCode() {
        return tehsileCode;
    }

    public void setTehsileCode(Object tehsileCode) {
        this.tehsileCode = tehsileCode;
    }

    public int getRegDistrictCode() {
        return regDistrictCode;
    }

    public void setRegDistrictCode(int regDistrictCode) {
        this.regDistrictCode = regDistrictCode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Object getTehsile() {
        return tehsile;
    }

    public void setTehsile(Object tehsile) {
        this.tehsile = tehsile;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    public String getAdminReason() {
        return adminReason;
    }

    public void setAdminReason(String adminReason) {
        this.adminReason = adminReason;
    }
}
