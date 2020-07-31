package com.govt.updms.rest.RequestModel;

public class FamilyRequest {
    String FamilyName;
    String IdentityNo;
    String GenderFamily;
    String DateOfBirthFamily;
    String Relation;


    public FamilyRequest(String familyName, String identityNo, String genderFamiliy, String dateOfBirthFamily, String relation) {
        FamilyName = familyName;
        IdentityNo = identityNo;
        GenderFamily = genderFamiliy;
        DateOfBirthFamily = dateOfBirthFamily;
        Relation = relation;
    }
}
