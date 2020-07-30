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
    private double lng;
    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lng")
    double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(double lon) {
        this.lng = lon;
    }

    @JsonProperty("lat")
    double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "coord [lon: " + lng + "][lat: " + lat + "]";
    }
}
