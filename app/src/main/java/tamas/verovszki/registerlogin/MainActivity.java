package tamas.verovszki.registerlogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText EditText_FelhasznaloiNev, EditText_Jelszo;
    Button Button_Login, Button_Register;
    AdatbazisSegito db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); // képernyő-elforgatás tiltása
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // Login gomb kezelése
        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditText_FelhasznaloiNev.getText().toString().equals("") || EditText_Jelszo.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Mindkét mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String teljesNev = db.felhasznaloKereses(EditText_FelhasznaloiNev.getText().toString(), EditText_Jelszo.getText().toString());
                    if (!teljesNev.equals("")) {
                        Intent belepve = new Intent(MainActivity.this, LoggedInActivity.class);
                        belepve.putExtra("Teljesnev", teljesNev); // a felhasználói név átadása az új activitynek, hogy üdvözölni tudja őt
                        startActivity(belepve);
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // átmenetes animáció az activity-k között
                    } else {
                        Toast.makeText(MainActivity.this, "Hibás felhasználói név vagy jelszó!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Regisztráció gomb kezelése
        Button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regisztracio = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(regisztracio);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // átmenetes animáció az activity-k között;
            }
        });
    }

    public void init(){
        Button_Login = findViewById(R.id.Button_Login);
        Button_Register = findViewById(R.id.Button_Register);
        EditText_FelhasznaloiNev = findViewById(R.id.EditText_FelhasznaloiNev);
        EditText_Jelszo = findViewById(R.id.EditText_Jelszo);
        db = new AdatbazisSegito(this);
    }
}
