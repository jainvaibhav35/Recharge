package base.rechargeapp.beans.changePassword;

/**
 * Created by lin on 19/10/16.
 */

public class ChangePasswordResponse {

    private String errorCode ;
    private String result ;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
