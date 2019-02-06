package com.example.rdsaleh.adpl_rs.javaClass;

public class Login {

    private String idLogin, passwordLogin;
    private int statusLogin;

    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }

    public int getStatusLogin() {
        return statusLogin;
    }

    public void setStatusLogin(int statusLogin) {
        this.statusLogin = statusLogin;
    }

    public void doLogin(String id, String password, int status){
        this.setIdLogin(id);
        this.setPasswordLogin(password);
        this.setStatusLogin(status);
    }

}
