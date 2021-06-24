package sang.mobileprogramming.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void subscribedListClick(View v)
    {
        Toast t = Toast.makeText(this, "Opening Subscribed List", Toast.LENGTH_SHORT);
        t.show();
        Intent intent = new Intent(this, SubscribedList.class);
        startActivity(intent);
    }
    public void subscribeMoreClick(View v)
    {
        Toast t = Toast.makeText(this, "Opening Subscribe More", Toast.LENGTH_SHORT);
        t.show();
        Intent intent = new Intent(this, SubscribeMore.class);
        startActivity(intent);
    }
    public void historicWarningClick(View v)
    {
        Toast t = Toast.makeText(this, "Opening Historic warning", Toast.LENGTH_SHORT);
        t.show();
        Intent intent = new Intent(this, HistoricWarining.class);
        startActivity(intent);
    }

}