package tamas.verovszki.registerlogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity {

    TextView TextView_Welcome;
    Button Button_Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); // képernyő-elforgatás tiltása
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        init();

        // előző activity által átadott adat kinyerése
        Bundle bundle = getIntent().getExtras();
        String teljesNev = bundle.getString("Teljesnev");

        TextView_Welcome.setText("Üdvözöllek " + teljesNev + "!");

        // Kijelentkezés-gomb kezelése
        Button_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fomenu = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(fomenu);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // átmenetes animáció az activity-k között;
            }
        });
    }

    public void init(){
        TextView_Welcome = findViewById(R.id.TextView_Welcome);
        Button_Logout = findViewById(R.id.Button_Logout);
    }
}
