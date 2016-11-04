
package base.rechargeapp.beans.Recharge.RechargeResponse;

import java.util.HashMap;
import java.util.Map;
public class Response {

    private String updated_balance;
    private String transction_id;
    private String mobile_no;
    private String status;
    private String date;
    private String time;
    private String amount;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The updatedBalance
     */
    public String getUpdatedBalance() {
        return updated_balance;
    }

    /**
     * 
     * @param updatedBalance
     *     The updated_balance
     */
    public void setUpdatedBalance(String updatedBalance) {
        this.updated_balance = updatedBalance;
    }

    /**
     * 
     * @return
     *     The transctionId
     */
    public String getTransctionId() {
        return transction_id;
    }

    /**
     * 
     * @param transctionId
     *     The transction_id
     */
    public void setTransctionId(String transctionId) {
        this.transction_id = transctionId;
    }

    /**
     * 
     * @return
     *     The mobileNo
     */
    public String getMobileNo() {
        return mobile_no;
    }

    /**
     * 
     * @param mobileNo
     *     The mobile_no
     */
    public void setMobileNo(String mobileNo) {
        this.mobile_no = mobileNo;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
