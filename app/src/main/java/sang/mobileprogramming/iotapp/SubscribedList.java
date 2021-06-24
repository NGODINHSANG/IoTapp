package sang.mobileprogramming.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SubscribedList extends AppCompatActivity{
    LinearLayout linearLayout;
    static List<String> subcribedList = new ArrayList<String>();
    List<View> viewList = new ArrayList<View>();
    int allSubscribedList = subcribedList.size();
    static String subscribedItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribed_list);
        showList();
    }

    public void airQualityClick(View v)
    {
        Toast t = Toast.makeText(this, "Show ", Toast.LENGTH_SHORT);
        t.show();
        Intent intent = new Intent(this, AirQuality.class);
        startActivity(intent);
    }
    public void createSubscribedItem(int id,final int i)
    {
        View placeSubsribed = viewList.get(i);
        placeSubsribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribedItem = subcribedList.get(i);
                airQualityClick(v);
            }
        });
    }
    public void showList() {
        linearLayout = findViewById(R.id.layoutList);
        linearLayout.removeAllViews();
        for(int i = 0; i < allSubscribedList; i++) {
            View placeSubsribed = getLayoutInflater().inflate(R.layout.subscribed_item, null, false);
            ImageView imageView = (ImageView ) placeSubsribed.findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.image1detail);
            TextView textView = (TextView) placeSubsribed.findViewById(R.id.place_name);
            textView.setText("Air Quality around " + subcribedList.get(i));
            viewList.add(placeSubsribed);
            createSubscribedItem(R.id.layoutList, i);
            linearLayout.addView(placeSubsribed);
        }
    }
}
