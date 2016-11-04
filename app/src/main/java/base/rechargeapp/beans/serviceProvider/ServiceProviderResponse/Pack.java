
package base.rechargeapp.beans.serviceProvider.ServiceProviderResponse;

import java.util.HashMap;
import java.util.Map;
public class Pack {

    private String id;
    private String service_id;
    private String pack_price;
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
     *     The serviceId
     */
    public String getServiceId() {
        return service_id;
    }

    /**
     * 
     * @param serviceId
     *     The service_id
     */
    public void setServiceId(String serviceId) {
        this.service_id = serviceId;
    }

    /**
     * 
     * @return
     *     The packPrice
     */
    public String getPackPrice() {
        return pack_price;
    }

    /**
     * 
     * @param packPrice
     *     The pack_price
     */
    public void setPackPrice(String packPrice) {
        this.pack_price = packPrice;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
