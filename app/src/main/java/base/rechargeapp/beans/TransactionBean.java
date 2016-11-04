package base.rechargeapp.beans;

/**
 * Created by lin on 17/10/16.
 */

public class TransactionBean {


    private String mTransactionId ;
    private String mOperatorName ;
    private String mMobileNumber ;
    private String mDate;
    private String mTime;

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    private String mAmount ;
    private String mStatus ;


    public String getmTransactionId() {
        return mTransactionId;
    }

    public void setmTransactionId(String mTransactionId) {
        this.mTransactionId = mTransactionId;
    }

    public String getmOperatorName() {
        return mOperatorName;
    }

    public void setmOperatorName(String mOperatorName) {
        this.mOperatorName = mOperatorName;
    }

    public String getmMobileNumber() {
        return mMobileNumber;
    }

    public void setmMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }

    public String getmAmount() {
        return mAmount;
    }

    public void setmAmount(String mAmount) {
        this.mAmount = mAmount;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
