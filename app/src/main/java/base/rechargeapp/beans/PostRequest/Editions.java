package base.rechargeapp.beans.PostRequest;

import java.util.HashMap;
import java.util.Map;

public class Editions {

    private String id;
    private String edition_name;
    private String image;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The editionName
     */
    public String getEditionName() {
        return edition_name;
    }

    /**
     * @param edition_name The edition_name
     */
    public void setEditionName(String edition_name) {
        this.edition_name = edition_name;
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
