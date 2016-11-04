
package base.rechargeapp.beans.transaction_list.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ResponseTransactionList {

    private String errorCode;
    private String result;
    private List<Response> response = new ArrayList<Response>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 
     * @param errorCode
     *     The errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 
     * @return
     *     The result
     */
    public String getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The response
     */
    public List<Response> getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
