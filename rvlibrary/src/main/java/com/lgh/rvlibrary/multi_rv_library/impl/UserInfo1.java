package com.lgh.rvlibrary.multi_rv_library.impl;

public class UserInfo1 {

    private String account;
    private String password;
    private int type;

    public UserInfo1() {
    }

    public UserInfo1(String account, String password, int type) {
        this.account = account;
        this.password = password;
        this.type = type;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
