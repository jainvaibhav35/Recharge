package base.rechargeapp.beans.changePassword;

import base.rechargeapp.utils.AppConstant;

/**
 * Created by lin on 19/10/16.
 */

public class ChangePasswordRequest {

    private String user_id ;
    private String oldPassword ;
    private String newPassword ;
    private String token_id ;
    private String secret_key = AppConstant.SECRET_KEY_VALUE;
    private String api_key = AppConstant.API_KEY_VALUE;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
}
