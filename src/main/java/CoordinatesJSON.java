import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "lon",
        "lat"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatesJSON {

    @JsonProperty("lng")
    private float lng;
    @JsonProperty("lat")
    private float lat;

    @JsonProperty("lng")
    public float getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(float lon) {
        this.lng = lon;
    }

    @JsonProperty("lat")
    public float getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(float lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "coord [lon: " + lng + "][lat: " + lat + "]";
    }
}
