package wiseman.stonebridge.Objects;

import android.graphics.Bitmap;

/**
 * Created by Wiseman on 2018-01-25.
 */

public class VehicleObject {
    private Bitmap image;
    private String name;
    private String price;
    private String featurers;
    private String transmission;
    private String mileage;
    private String province;
    private String url;

    public VehicleObject(String price, String transmission, String mileage, String province, String featurers, String name, String url) {
        this.price = price;
        this.transmission = transmission;
        this.mileage = mileage;
        this.province = province;
        this.featurers = featurers;
        this.name = name;
        this.url = url;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFeaturers() {
        return featurers;
    }

    public void setFeaturers(String featurers) {
        this.featurers = featurers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
