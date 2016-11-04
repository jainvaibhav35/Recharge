package base.rechargeapp.beans.forgot_password;

import base.rechargeapp.utils.AppConstant;

/**
 * Created by lin on 28/10/16.
 */

public class RequestForgotPassword {

    private String email ;
    private String secret_key = AppConstant.SECRET_KEY_VALUE;
    private String api_key = AppConstant.API_KEY_VALUE ;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
}
