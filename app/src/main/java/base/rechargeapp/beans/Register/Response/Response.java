
package base.rechargeapp.beans.Register.Response;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private String id;
    private String first_name;
    private String last_name;
    private String address;
    private String email;
    private String rfc;
    private String mob_no;
    private String password;
    private String business_type;
    private String user_type;
    private String created_date;
    private String price;
    private String commission;
    private String flag;
    private String token_id;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return first_name;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return last_name;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * 
     * @param rfc
     *     The rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * 
     * @return
     *     The mobNo
     */
    public String getMobNo() {
        return mob_no;
    }

    /**
     * 
     * @param mobNo
     *     The mob_no
     */
    public void setMobNo(String mobNo) {
        this.mob_no = mobNo;
    }

    /**
     * 
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     The businessType
     */
    public String getBusinessType() {
        return business_type;
    }

    /**
     * 
     * @param businessType
     *     The business_type
     */
    public void setBusinessType(String businessType) {
        this.business_type = businessType;
    }

    /**
     * 
     * @return
     *     The userType
     */
    public String getUserType() {
        return user_type;
    }

    /**
     * 
     * @param userType
     *     The user_type
     */
    public void setUserType(String userType) {
        this.user_type = userType;
    }

    /**
     * 
     * @return
     *     The createdDate
     */
    public String getCreatedDate() {
        return created_date;
    }

    /**
     * 
     * @param createdDate
     *     The created_date
     */
    public void setCreatedDate(String createdDate) {
        this.created_date = createdDate;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The commission
     */
    public String getCommission() {
        return commission;
    }

    /**
     * 
     * @param commission
     *     The commission
     */
    public void setCommission(String commission) {
        this.commission = commission;
    }

    /**
     * 
     * @return
     *     The flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 
     * @param flag
     *     The flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
}
