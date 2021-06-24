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
    private String JSON_URL = "http://www.json-generator.com/api/json/get/cozBMLMTaW?indent=2";
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        lineChart = findViewById(R.id.lineChart);
        LineDataSet lineDataSet1 = new LineDataSet(lineChartDataSet1(),"Co");
        LineDataSet lineDataSet2 = new LineDataSet(lineChartDataSet2(),"Co2");

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet1);
        iLineDataSets.add(lineDataSet2);
        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();

        lineChart.setNoDataText("Data not Available");

        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setCircleColor(Color.GREEN);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawCircleHole(true);
        lineDataSet1.setLineWidth(5);
        lineDataSet1.setCircleRadius(10);
        lineDataSet1.setCircleHoleRadius(10);
        lineDataSet1.setValueTextSize(10);
        lineDataSet1.setValueTextColor(Color.BLACK);

        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setCircleColor(Color.YELLOW);
        lineDataSet2.setDrawCircles(true);
        lineDataSet2.setDrawCircleHole(true);
        lineDataSet2.setLineWidth(5);
        lineDataSet2.setCircleRadius(10);
        lineDataSet2.setCircleHoleRadius(10);
        lineDataSet2.setValueTextSize(10);
        lineDataSet2.setValueTextColor(Color.BLACK);
    }
    private ArrayList<Entry> lineChartDataSet1(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();

        dataSet.add(new Entry(0,40));
        dataSet.add(new Entry(1,10));
        dataSet.add(new Entry(2,15));
        dataSet.add(new Entry(3,12));
        dataSet.add(new Entry(4,20));
        dataSet.add(new Entry(5,50));
        dataSet.add(new Entry(6,23));
        dataSet.add(new Entry(7,34));
        dataSet.add(new Entry(8,12));
        return  dataSet;


    }
    private ArrayList<Entry> lineChartDataSet2(){
        ArrayList<Entry> dataSet = new ArrayList<Entry>();

        dataSet.add(new Entry(0,30));
        dataSet.add(new Entry(1,20));
        dataSet.add(new Entry(2,25));
        dataSet.add(new Entry(3,22));
        dataSet.add(new Entry(4,30));
        dataSet.add(new Entry(5,20));
        dataSet.add(new Entry(6,33));
        dataSet.add(new Entry(7,54));
        dataSet.add(new Entry(8,22));
        return  dataSet;


    }
}