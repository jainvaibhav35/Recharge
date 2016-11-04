
package base.rechargeapp.beans.PostRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class data {

    private List<Editions> editions = new ArrayList<Editions>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The editions
     */
    public List<Editions> getEditions() {
        return editions;
    }

    /**
     * 
     * @param editions
     *     The Editions
     */
    public void setEditions(List<Editions> editions) {
        this.editions = editions;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
