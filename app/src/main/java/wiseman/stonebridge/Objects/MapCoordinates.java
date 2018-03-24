package wiseman.stonebridge.Objects;

/**
 * Created by Wiseman on 2018-02-06.
 */

public class MapCoordinates {
    private String latitude;
    private String longitude;

    public MapCoordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
