package base.rechargeapp.beans.Login;

import base.rechargeapp.utils.AppConstant;

/**
 * Created by lin on 18/10/16.
 */

public class LoginRequest {

    private String secret_key= AppConstant.SECRET_KEY_VALUE;
    private String api_key=AppConstant.API_KEY_VALUE;
    private String email ;
    private String password ;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
