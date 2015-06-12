package com.lime.watchassembly.vo;

import java.util.Date;

/**
 * Created by Administrator on 2015-06-09.
 */
public class Member {
    private String memberId;
    private int logonTypeId;
    private String memberNickname;
    private String address;
    private Date birthDate;
    private char gender;

    public Member(String memberId, int logonTypeId, String memberNickname) {
        this.memberId = memberId;
        this.logonTypeId = logonTypeId;
        this.memberNickname = memberNickname;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getLogonTypeId() {
        return logonTypeId;
    }

    public void setLogonTypeId(int logonTypeId) {
        this.logonTypeId = logonTypeId;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}
