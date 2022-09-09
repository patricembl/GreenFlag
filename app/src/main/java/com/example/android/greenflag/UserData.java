package com.example.android.greenflag;

public class UserData {

    private String u_email;
    private String u_pass;


    public UserData(String email, String pass) {
        this.u_email = email;
        this.u_pass = pass;
    }


    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_pass() {
        return u_pass;
    }

    public void setU_pass(String u_pass) {
        this.u_pass = u_pass;
    }
}
