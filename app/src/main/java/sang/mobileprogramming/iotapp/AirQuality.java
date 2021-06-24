package sang.mobileprogramming.iotapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AirQuality extends AppCompatActivity{
    static boolean sub = false;
    double co, co2, nh4, acetona;
    Dialog mdialog;
    Button mbtt;
    static MyTask myTask = null;
    MqttAndroidClient client = null;

    private static class MyTask extends AsyncTask<Void, Void, String>
    {
        private WeakReference<AirQuality> airQualityWeakReference;
        MyTask(AirQuality activity){
            airQualityWeakReference = new WeakReference<AirQuality>(activity);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                int executionTime = 1000;
                Thread.sleep(executionTime);
                Log.d("ASYNCDEBUG", Long.toString(Thread.currentThread().getId()));

                AirQuality activity = airQualityWeakReference.get();
                activity.setData();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            AirQuality activity = airQualityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            activity.updateAirQuality();
            activity.createWarningObject();
        }
    }

    void pub(String content){
        String topic = "/MQ135/data";
        String payload = content;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }
    void setData(){
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String msg = new String(message.getPayload());
                System.out.println("mqtt" + "Received message: " + msg);
                if (!topic.equals("/MQ135/data")) {
                    System.out.println("mqtt" + "Message Arrived not subcribed collect topic");
                    return;
                } else {
                    int devid = 0, packet_no;
                    double tempVal, humidVal, tdsVal, phVal;
                    String status;
                    System.out.println("mqtt" + "Received a collect data packet");
                    //Parse json data packet
                    try {
                        JSONObject jobj = new JSONObject(msg);
                        co = jobj.getDouble("CO");
                        System.out.println("CO: " + co);
                        co2 = jobj.getDouble("CO2");
                        System.out.println("CO2: " + co2);
                        nh4 = jobj.getDouble("NH4");
                        System.out.println("NH4: " + nh4);
                        acetona = jobj.getDouble("Acetona");
                        System.out.println("Acetona: " + acetona);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        System.out.println("mqtt" + "Collect data packet is wrong format");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
    void connect(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.emqx.io:1883", clientId);

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("mqtt", "onSuccess");
                    String topic = "/MQ135/data";
                    int qos = 2;
                    try {
                        IMqttToken subToken = client.subscribe(topic, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                // The message was published
                                Log.d("mqtt", "Subcribed");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                // The subscription could not be performed, maybe the user was not
                                // authorized to subscribe on the specified topic e.g. using wildcards
                                Log.d("mqtt", "Fail Subcribe");
                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("mqtt", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.air_quality);
        /*mbtt = findViewById(R.id.historygraph);
        mdialog = new Dialog(this);
        mbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.setContentView(R.layout.graph);
                mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mdialog.show();
            }
        });*/
        Log.d("ASYNCDEBUG", Long.toString(Thread.currentThread().getId()));
        connect();
        updateAirQuality();
    }

    public  void setTextAirQuality(double gas, TextView textView){
        if(gas > 10) {
            textView.setText("Dangerous");
            textView.setTextColor(Color.rgb(255,0,0));
        }
        else if(gas > 5){
            textView.setText("Warning");
            textView.setTextColor(Color.rgb(255,199,0));
        }else{
            textView.setText("Normal");
            textView.setTextColor(Color.rgb(36,255,0));
        }
    }

    public void updateAirQuality(){
        TableLayout tableLayout = findViewById(R.id.airquarlity);
        TextView coindex = tableLayout.findViewById(R.id.coindex);
        TextView co2index = tableLayout.findViewById(R.id.co2index);
        TextView nh4index = tableLayout.findViewById(R.id.nh4index);
        TextView acetonaindex = tableLayout.findViewById(R.id.acetonaindex);
        coindex.setText(co + "ppm");
        co2index.setText(co2 + "ppm");
        nh4index.setText(nh4 + "ppm");
        acetonaindex.setText(acetona + "ppm");

        setTextAirQuality(co, (TextView)tableLayout.findViewById(R.id.costatus));
        setTextAirQuality(co2, (TextView) tableLayout.findViewById(R.id.co2status));
        setTextAirQuality(nh4, (TextView) tableLayout.findViewById(R.id.nh4status));
        setTextAirQuality(acetona, (TextView) tableLayout.findViewById(R.id.acetonastatus));

        myTask = new MyTask(this);
        myTask.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void createWarningObject (){
        if(co > 5 || co2 > 5 || nh4 > 5 || acetona > 5){
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);

            WarningObject warningObject = new WarningObject();
            if(co > 10 || co2 > 10 || nh4 > 10 || acetona > 10){
                warningObject.setStatus("DANGER");
            }
            else warningObject.setStatus("Warning");
            warningObject.setLocation(SubscribedList.subscribedItem);
            warningObject.setTime(formattedDate);
            warningObject.setCO(co);
            warningObject.setCO2(co2);
            warningObject.setNH4(nh4);
            warningObject.setAcetona(acetona);
            warningObject.setNews(0);
            HistoricWarining.warningStatusList.add(warningObject);
        }
    }

    public void unsubscribeClick(View v)
    {
        Toast t = null;
        TextView textView = findViewById(R.id.unsubscribe);
        int len = SubscribedList.subcribedList.size();
        if(SubscribedList.subscribedItem != null && SubscribedList.subcribedList.contains(SubscribedList.subscribedItem)){
            sub = true;
            textView.setText("Subscribe");
            t = Toast.makeText(this, "Unsubscribed", Toast.LENGTH_SHORT);
            List<String> tempList = new ArrayList<String>();
            for(int i = 0; i < len; i++){
                String location = SubscribedList.subcribedList.get(i);
                if(SubscribedList.subscribedItem != location){
                    tempList.add(location);
                }
            }
            len--;
            SubscribedList.subcribedList.clear();
            for(int i = 0; i < len; i++){
                SubscribedList.subcribedList.add(tempList.get(i));
            }
        }
        else if(SubscribedList.subscribedItem != null && !SubscribedList.subcribedList.contains(SubscribedList.subscribedItem)){
            sub = false;
            textView.setText("Unsubscribe");
            t = Toast.makeText(this, "Subscribed", Toast.LENGTH_SHORT);
            SubscribedList.subcribedList.add(SubscribedList.subscribedItem);
        }
        else{
            t = Toast.makeText(this, "None", Toast.LENGTH_SHORT);
        }
        t.show();
    }
    public void graph(View view) {
        /*mdialog = new Dialog(this);
        mdialog.setContentView(R.layout.graph);
        mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mdialog.show();*/
        Intent intent = new Intent(this, Line_chart.class);
        startActivity(intent);
    }

}
