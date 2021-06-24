package sang.mobileprogramming.iotapp;

import android.app.Dialog;

public class Air {

   // float ppm[];
    String _id;
    String air;
    String location;
    int __v;

    public Air( String _id, String air, String location, int __v){
        //this.ppm = ppm;
        this._id = _id;
        this.air = air;
        this.location = location;
        this.__v = __v;
    }

   // public float[] getPpm(){
      //  return ppm;
    //}
    public String get_id(){
        return _id;
    }
    public String getAir(){
        return air;
    }
    public String getLocation(){
        return location;
    }
    public int get__v(){
        return __v;
    }
}
