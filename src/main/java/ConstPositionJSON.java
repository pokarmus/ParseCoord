import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "location"
})
@JsonIgnoreProperties(ignoreUnknown = true)
class ConstPositionJSON {

    @JsonProperty("location")
    private LocationJSON location;

    @JsonProperty("location")
    public LocationJSON getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(LocationJSON location) {
        this.location = location;
    }


}