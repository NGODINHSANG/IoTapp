package sang.mobileprogramming.iotapp;

public class WarningObject {
    private String status;
    private String location;
    private String time;
    private double co;
    private double co2;
    private double nh4;
    private double acetona;
    private int news;

    public  WarningObject(){
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public double getCO() {
        return co;
    }
    public void setCO(double co) {
        this.co = co;
    }
    public double getCO2() {
        return co2;
    }
    public void setCO2(double co2) {
        this.co2 = co2;
    }
    public double getNH4() {
        return nh4;
    }
    public void setNH4(double nh4) {
        this.nh4 = nh4;
    }
    public double getAcetona() {
        return acetona;
    }
    public void setAcetona(double acetona) {
        this.acetona = acetona;
    }
    public int getNews() {
        return news;
    }
    public void setNews(int news) {
        this.news = news;
    }
}
