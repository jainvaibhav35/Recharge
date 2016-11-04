
package base.rechargeapp.beans.PostRequest;

import java.util.HashMap;
import java.util.Map;

public class Example {

    private String status;
    private base.rechargeapp.beans.PostRequest.data data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The data
     */
    public base.rechargeapp.beans.PostRequest.data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(base.rechargeapp.beans.PostRequest.data data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
