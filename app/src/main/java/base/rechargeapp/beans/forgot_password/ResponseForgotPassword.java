package base.rechargeapp.beans.forgot_password;

/**
 * Created by lin on 28/10/16.
 */

public class ResponseForgotPassword {

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
