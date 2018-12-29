package tamas.verovszki.registerlogin;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by verov on 2018.12.28..
 */

public class AdatbazisSegito extends SQLiteOpenHelper {

    // Adatbázis felépítése
    public static final String DATABASE_NAME = "RegisterAndLogin.db";
    public static final String TABLE_NAME = "Felhasznalok";
    public static final String COL_ID = "Id";
    public static final String COL_FELHASZNALONEV = "Felhasznalonev";
    public static final String COL_JELSZO = "Jelszo";
    public static final String COL_TELJESNEV = "TeljesNev";
    public static final String COL_TELEFONSZAM = "Telefonszam";

    // Konstruktor
    public AdatbazisSegito(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Tábla létrehozása
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Felhasznalonev TEXT, Jelszo TEXT, TeljesNev Text, Telefonszam Text)");
    }

    // Tábla eldobása, ha már létezik
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    // adatfelvétel - regisztráció
    public boolean adatFelvetel(String felhasznalonev, String jelszo, String teljesnev, String telefonszam ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FELHASZNALONEV, felhasznalonev);
        contentValues.put(COL_JELSZO, jelszo);
        contentValues.put(COL_TELJESNEV, teljesnev);
        contentValues.put(COL_TELEFONSZAM, telefonszam);

        long eredmeny = db.insert(TABLE_NAME, null, contentValues);

        if (eredmeny == -1) {
            return false;           // hiba az adatfelvétel során
        }
        else
            return true;            // sikeres adatfelvétel
    }

    // felhasználó keresése
    public String felhasznaloKereses(String felhasznalonev, String jelszo){
        String felhasznaloTeljesNeve="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor eredmeny = db.rawQuery("Select TeljesNev from " + TABLE_NAME + " where Felhasznalonev='" + felhasznalonev + "' AND Jelszo='" + jelszo + "'", null);
        if (eredmeny!=null && eredmeny.getCount()>0) {
            eredmeny.moveToFirst();
            felhasznaloTeljesNeve = eredmeny.getString(eredmeny.getColumnIndex("TeljesNev"));
        }
        return felhasznaloTeljesNeve;
    }

    // egyező felhasználói név ellenőrzése
    public boolean vanEMarIlyenFelhasznalo(String felhasznalonev){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor eredmeny = db.rawQuery("Select Felhasznalonev from " + TABLE_NAME + " where Felhasznalonev='" + felhasznalonev + "'", null);
        if (eredmeny!=null && eredmeny.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }
}