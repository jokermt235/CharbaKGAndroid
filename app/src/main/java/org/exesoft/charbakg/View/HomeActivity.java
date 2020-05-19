package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.exesoft.charbakg.Model.Offspring;
import org.exesoft.charbakg.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Init KRS ACTIONS
        initKRSActions();
        // Init MRS ACTIONS
        initMRSActions();
        // Init Horse ACTIONS
        initHorseActions();
    }

    private void initKRSActions(){
        ImageButton imageButtonKRS = findViewById(R.id.homeCowBtn);
        imageButtonKRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), LivestockActivity.class));
                finish();
            }
        });

        Button buttonOffspringKRS = findViewById(R.id.homeCowBirthBtn);
        buttonOffspringKRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), KrsOffspringActivity.class);
                intent.putExtra("owner","krs");
                v.getContext().startActivity(intent);
                finish();
            }
        });

        Button buttonFeed = findViewById(R.id.homeCowMealBtn);
        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), FeedActivity.class));
                finish();
            }
        });

        Button buttonYield = findViewById(R.id.homeCowYieldBtn);
        buttonYield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), YieldActivity.class);
                intent.putExtra("owner","krs");
                v.getContext().startActivity(intent);
                finish();
            }
        });
    }

    private void initMRSActions(){
        ImageButton imageButtonKRS = findViewById(R.id.homeSheepBtn);
        imageButtonKRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), MRSActivity.class));
                finish();
            }
        });

        Button buttonOffspringKRS = findViewById(R.id.homeSheepBirthBtn);
        buttonOffspringKRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MrsOffspringActivity.class);
                intent.putExtra("owner","mrs");
                v.getContext().startActivity(intent);
                finish();
            }
        });

        Button buttonFeed = findViewById(R.id.homeSheepMealBtn);
        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FeedActivity.class);
                intent.putExtra("owner","mrs");
                v.getContext().startActivity(intent);
                finish();
            }
        });

        Button buttonYield = findViewById(R.id.homeSheepYieldBtn);
        buttonYield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), YieldActivity.class);
                intent.putExtra("owner","mrs");
                v.getContext().startActivity(intent);
                finish();
            }
        });
    }

    private  void initHorseActions(){
        ImageButton imageButtonKRS = findViewById(R.id.homeHorseBtn);
        imageButtonKRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), HorseActivity.class));
                finish();
            }
        });

        Button buttonOffspringKRS = findViewById(R.id.homeHorseBirthBtn);
        buttonOffspringKRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HorseOffspringActivity.class);
                intent.putExtra("owner","horse");
                v.getContext().startActivity(intent);
                finish();
            }
        });

        Button buttonFeed = findViewById(R.id.homeHorseMealBtn);
        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), FeedActivity.class));
                finish();
            }
        });

        Button buttonYield = findViewById(R.id.homeHorseYieldBtn);
        buttonYield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), YieldActivity.class);
                intent.putExtra("owner","horse");
                v.getContext().startActivity(intent);
                finish();
            }
        });
    }

}
