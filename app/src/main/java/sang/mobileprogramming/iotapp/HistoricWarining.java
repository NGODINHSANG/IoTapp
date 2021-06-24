package sang.mobileprogramming.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HistoricWarining extends AppCompatActivity{
    LinearLayout linearLayout1 = null;
    LinearLayout linearLayout2 = null;
    LinearLayout linearLayout3 = null;
    LinearLayout allWarningLayout = null;
    LinearLayout onlyWarningLayout = null;
    LinearLayout onlyDangerousLayout = null;
    List<View> allWarningList = new ArrayList<View>();
    int viewListLength = 0;

    static List<WarningObject> warningStatusList = new ArrayList<WarningObject>();
    int allWarningStatus = warningStatusList.size();
    int []read = new int[allWarningStatus];
    int []delete = new int[allWarningStatus];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historic_warning);

        linearLayout1 = findViewById(R.id.warning_layout1);
        linearLayout2 = findViewById(R.id.warning_layout2);
        linearLayout3 = findViewById(R.id.warning_layout3);

        createWarningList();
        allWarning();
    }
    public void allClick(View v)
    {
        Toast t = Toast.makeText(this, "Click \"All\"", Toast.LENGTH_SHORT);
        t.show();
        linearLayout1.setVisibility(View.VISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.INVISIBLE);
        allWarning();
    }
    public void warningClick(View v)
    {
        Toast t = Toast.makeText(this, "Click \"Warning\"", Toast.LENGTH_SHORT);
        t.show();
        linearLayout1.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.VISIBLE);
        linearLayout3.setVisibility(View.INVISIBLE);
        onlyWarning();
    }
    public void dangerousClick(View v)
    {
        Toast t = Toast.makeText(this, "Click \"Dangerous\"", Toast.LENGTH_SHORT);
        t.show();
        linearLayout1.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.VISIBLE);
        onlyDanger();
    }

    public void createWarningList(){
        allWarningList.clear();
        viewListLength = 0;
        for(int i = 0; i < allWarningStatus; i++) {
            View warning = getLayoutInflater().inflate(R.layout.warning_status, null, false);
            WarningObject warningObject = warningStatusList.get(i);
            TextView textView1 = (TextView) warning.findViewById(R.id.text1);
            textView1.setText(warningObject.getStatus());
            if(warningObject.getStatus() == "DANGER") textView1.setTextColor(Color.rgb(255,0,0));
            TextView textView2 = (TextView) warning.findViewById(R.id.text2);
            textView2.setText("Location: "+warningObject.getLocation());
            TextView textView3 = (TextView) warning.findViewById(R.id.text3);
            textView3.setText("Time: "+warningObject.getTime());
            TextView textView4 = (TextView) warning.findViewById(R.id.text4);
            textView4.setText("CO: "+warningObject.getCO()+" ppm");
            TextView textView5 = (TextView) warning.findViewById(R.id.text5);
            textView5.setText("NH4: "+warningObject.getNH4()+" ppm");
            TextView textView6 = (TextView) warning.findViewById(R.id.text6);
            textView6.setText("CO2: "+warningObject.getCO2()+" ppm");
            TextView textView7 = (TextView) warning.findViewById(R.id.text7);
            textView7.setText("Acetona: "+warningObject.getAcetona()+" ppm");
            TextView textView8 = (TextView) warning.findViewById(R.id.text8);
            textView8.setText("N");
            allWarningList.add(warning);
            viewListLength++;
        }
    }

    public void clearLayout(){
        allWarningLayout = findViewById(R.id.layoutAll);
        allWarningLayout.removeAllViews();
        onlyWarningLayout = findViewById(R.id.layoutWarning);
        onlyWarningLayout.removeAllViews();
        onlyDangerousLayout = findViewById(R.id.layoutDangerous);
        onlyDangerousLayout.removeAllViews();
    }

    public void clickNew(final int i)
    {
        final View warning = allWarningList.get(i);
        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView8 = (TextView) allWarningList.get(i).findViewById(R.id.text8);
                textView8.setText("");
                read[i] = 1;
            }
        });
    }

    public void deleteWarning(final int i){
        final View warning = allWarningList.get(i);
        final ImageView deleteImage = (ImageView) warning.findViewById(R.id.deleteImage);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWarningClick();
                delete[i] = 1;
                allWarningLayout.removeView(warning);
                onlyWarningLayout.removeView(warning);
                onlyDangerousLayout.removeView(warning);
            }
        });
    }


    public void allWarning(){
        clearLayout();

        for(int i = 0; i < allWarningStatus; i++) {
            if(delete[i] == 1) continue;
            View warning = allWarningList.get(i);
            clickNew(i);
            deleteWarning(i);
            allWarningLayout.addView(warning);
        }
    }

    public void onlyWarning(){
        clearLayout();

        for(int i = 0; i < allWarningStatus; i++) {
            if(delete[i] == 1) continue;
            if(warningStatusList.get(i).getStatus() == "Warning"){
                View warning = allWarningList.get(i);
                deleteWarning(i);
                onlyWarningLayout.addView(warning);
            }
        }
    }

    public void onlyDanger(){
        clearLayout();

        for(int i = 0; i < allWarningStatus; i++) {
            if(delete[i] == 1) continue;
            if(warningStatusList.get(i).getStatus() == "DANGER"){
                View warning = allWarningList.get(i);
                deleteWarning(i);
                onlyDangerousLayout.addView(warning);
            }
        }
    }

    public void deleteWarningClick()
    {
        Toast t = Toast.makeText(this, "\"Warning\" deleted", Toast.LENGTH_SHORT);
        t.show();
    }
}
