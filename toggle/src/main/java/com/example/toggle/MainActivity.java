package com.example.toggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ui.ToggleView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleView toggleView = (ToggleView) findViewById(R.id.toggleView);
//        toggleView.setSwitchBackgroundBitmapResource(R.mipmap.switch_background);
//        toggleView.setSlideBackgroundBItmapResource(R.mipmap.slide_button);
//        toggleView.setSwitchState(true);
        toggleView.setOnSwitchStateUpdateLinstener(new ToggleView.OnSwitchStateUpdateLinstener() {
            @Override
            public void SwitchStateUpdate(boolean state) {
                Toast.makeText(getApplicationContext(), "state : " + state, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
