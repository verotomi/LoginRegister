package tamas.verovszki.registerlogin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText EditText_FelhasznaloiNev, EditText_Jelszo, EditText_Jelszo_Ujra, EditText_TeljesNev, EditText_Telefonszam;
    Button Button_Register, Button_Cancel;
    AdatbazisSegito db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();

        Button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (    EditText_FelhasznaloiNev.getText().toString().equals("") ||
                        EditText_Jelszo.getText().toString().equals("") ||
                        EditText_Jelszo_Ujra.getText().toString().equals("") ||
                        EditText_TeljesNev.getText().toString().equals("") ||
                        EditText_Telefonszam.getText().toString().equals("")
                        ) {
                    Toast.makeText(RegistrationActivity.this, "Minden adatot meg kell adni!", Toast.LENGTH_SHORT).show();
                }
                else if (!EditText_Jelszo.getText().toString().equals(EditText_Jelszo_Ujra.getText().toString())){
                    Toast.makeText(RegistrationActivity.this, "Nem egyeznek a jelszavak!", Toast.LENGTH_SHORT).show();
                }
                else if (db.vanEMarIlyenFelhasznalo(EditText_FelhasznaloiNev.getText().toString())){
                    Toast.makeText(RegistrationActivity.this, "Ilyen nevű felhasználó már létezik!", Toast.LENGTH_SHORT).show();
                }
                else {
                    RegisztracioRogzitese();
                    Intent fomenu = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(fomenu);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        Button_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fomenu = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(fomenu);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    public void init(){
        EditText_FelhasznaloiNev = findViewById(R.id.EditText_FelhasznaloiNev);
        EditText_Jelszo = findViewById(R.id.EditText_Jelszo);
        EditText_Jelszo_Ujra = findViewById(R.id.EditText_Jelszo_Ujra);
        EditText_TeljesNev = findViewById(R.id.EditText_TeljesNev);
        EditText_Telefonszam = findViewById(R.id.EditText_Telefonszám);
        Button_Register = findViewById(R.id.Button_Register);
        Button_Cancel = findViewById(R.id.Button_Cancel);
        db = new AdatbazisSegito(this);
    }

    public void RegisztracioRogzitese(){
        String felhasznaloinev = EditText_FelhasznaloiNev.getText().toString();
        String jelszo = EditText_Jelszo.getText().toString();
        String teljesnev = EditText_TeljesNev.getText().toString();
        String telefonszam = EditText_Telefonszam.getText().toString();

        boolean eredmeny = db.adatFelvetel(felhasznaloinev, jelszo, teljesnev, telefonszam);

        if (!eredmeny){
            Toast.makeText(this, "Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(RegistrationActivity.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
        }
    }
}
