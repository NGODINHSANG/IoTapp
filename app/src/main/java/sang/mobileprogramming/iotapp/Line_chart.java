package sang.mobileprogramming.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Line_chart extends AppCompatActivity {

    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        lineChart = findViewById(R.id.lineChart);
        LineDataSet lineDataSet1 = new LineDataSet(lineChartDataSet1(),"Co");
        LineDataSet lineDataSet2 = new LineDataSet(lineChartDataSet2(),"Co2");
        LineDataSet lineDataSet3 = new LineDataSet(lineChartDataSet3(),"Nh4");
        LineDataSet lineDataSet4 = new LineDataSet(lineChartDataSet4(),"Acetone");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet1);
        iLineDataSets.add(lineDataSet2);
        iLineDataSets.add(lineDataSet3);
        iLineDataSets.add(lineDataSet4);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.setNoDataText("Data not Available");

        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setLineWidth(5);
        lineDataSet1.setValueTextSize(10);
        lineDataSet1.setValueTextColor(Color.BLACK);

        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setLineWidth(5);
        lineDataSet2.setValueTextSize(10);
        lineDataSet2.setValueTextColor(Color.BLACK);

        lineDataSet3.setColor(Color.GREEN);
        lineDataSet3.setLineWidth(5);
        lineDataSet3.setValueTextSize(10);
        lineDataSet3.setValueTextColor(Color.BLACK);

        lineDataSet4.setColor(Color.YELLOW);
        lineDataSet4.setLineWidth(5);
        lineDataSet4.setValueTextSize(10);
        lineDataSet4.setValueTextColor(Color.BLACK);
    }
    private ArrayList<Entry> lineChartDataSet1(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();
        int k = Math.min(AirQuality.cCo.size(), 8);
        for(int i = 0; i < k; i++){
            double d = AirQuality.cCo.get(i);
            float f = (float) d;
            dataSet.add(new Entry(i, f));
        }
        return  dataSet;
    }
    private ArrayList<Entry> lineChartDataSet2(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();
        int k = Math.min(AirQuality.cCo2.size(), 8);
        for(int i = 0; i < k; i++){
            double d = AirQuality.cCo2.get(i);
            float f = (float) d;
            dataSet.add(new Entry(i, f));
        }
        return  dataSet;
    }

    private ArrayList<Entry> lineChartDataSet3(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();
        int k = Math.min(AirQuality.cNh4.size(), 8);
        for(int i = 0; i < k; i++){
            double d = AirQuality.cNh4.get(i);
            float f = (float) d;
            dataSet.add(new Entry(i, f));
        }
        return  dataSet;
    }

    private ArrayList<Entry> lineChartDataSet4(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();
        int k = Math.min(AirQuality.cAceton.size(), 8);
        for(int i = 0; i < k; i++){
            double d = AirQuality.cAceton.get(i);
            float f = (float) d;
            dataSet.add(new Entry(i, f));
        }
        return  dataSet;
    }
}
