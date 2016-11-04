package base.rechargeapp.beans.commission.Response;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private String name;
    private String total_recharge;
    private String commission_rate;
    private String commission;
    private String image;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The totalRecharge
     */
    public String getTotalRecharge() {
        return total_recharge;
    }

    /**
     * @param totalRecharge The total_recharge
     */
    public void setTotalRecharge(String totalRecharge) {
        this.total_recharge = totalRecharge;
    }

    /**
     * @return The commissionRate
     */
    public String getCommissionRate() {
        return commission_rate;
    }

    /**
     * @param commissionRate The commission_rate
     */
    public void setCommissionRate(String commissionRate) {
        this.commission_rate = commissionRate;
    }

    /**
     * @return The commission
     */
    public String getCommission() {
        return commission;
    }

    /**
     * @param commission The commission
     */
    public void setCommission(String commission) {
        this.commission = commission;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
