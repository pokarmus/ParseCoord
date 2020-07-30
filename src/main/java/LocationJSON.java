import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "@type",
        "id",
        "coordinates"
})
@JsonIgnoreProperties(ignoreUnknown = true)
class LocationJSON {
    @JsonProperty("@type")
    private String type;
    @JsonProperty("id")
    private int id;
    @JsonProperty("coordinates")
    private CoordinatesJSON coordinatesJSON;

    @JsonProperty("@type")
    public String getType() {
        return type;
    }
    @JsonProperty("name")
    public void setName(String type) {
        this.type = type;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty("coordinates")
    public CoordinatesJSON getCoord() {
        return coordinatesJSON;
    }
    @JsonProperty("coordinates")
    public void setCoord(CoordinatesJSON coord) {
        this.coordinatesJSON = coord;
    }

    @Override
    public String toString() {
        return "Type" + this.type + "ID: " + this.id + ", " + this.coordinatesJSON.toString();
    }
}
