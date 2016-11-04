package base.rechargeapp.beans.commission;

import base.rechargeapp.utils.AppConstant;

/**
 * Created by lin on 2/11/16.
 */

public class RequestCommission {

    private String user_id;
    private String token_id;
    private String secret_key = AppConstant.SECRET_KEY_VALUE;
    private String api_key = AppConstant.API_KEY_VALUE;
    private String date;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
