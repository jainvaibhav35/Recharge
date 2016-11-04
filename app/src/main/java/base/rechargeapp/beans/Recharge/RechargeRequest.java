package base.rechargeapp.beans.Recharge;

import base.rechargeapp.utils.AppConstant;

/**
 * Created by lin on 25/10/16.
 */

public class RechargeRequest {

    private String mobile_no ;
    private String user_id ;
    private String email_id ;
    private String service_id ;
    private String amount ;
    private String secret_key = AppConstant.SECRET_KEY_VALUE ;
    private String api_key = AppConstant.API_KEY_VALUE;
    private String token_id ;


    public String getMobile_number() {
        return mobile_no;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_no = mobile_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email_id;
    }

    public void setEmail(String email) {
        this.email_id = email;
    }

    public String getProvider_id() {
        return service_id;
    }

    public void setProvider_id(String provider_id) {
        this.service_id = provider_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
}
