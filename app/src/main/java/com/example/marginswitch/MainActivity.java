package com.example.marginswitch;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String SP_FILE = "SP_FILE";
    private final String CUR_LANG_KEY = "CUR_LANG_KEY";

    private Spinner spnSwitchLang;
    private Spinner spnSwitchMargins;
    private Button btnOk;
    private Locale curLocale;
    private Configuration config;
    private SharedPreferences sharedPref;
    private TextView txtExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.onActivityCreateSetTheme(this);

        sharedPref = getSharedPreferences(SP_FILE, MODE_PRIVATE);
        config = new Configuration();

        curLocale = new Locale(sharedPref.getString(CUR_LANG_KEY, Locale.getDefault().getLanguage()));

        config.setLocale(curLocale);
        getResources().updateConfiguration(config, getBaseContext().getResources()
                .getDisplayMetrics());

        setContentView(R.layout.activity_main);

        btnOk = findViewById(R.id.btnOk);
        txtExample = findViewById(R.id.txtExample);

        initLanguagesSpinner();

        initMarginsSpinner();

    }

    private void initMarginsSpinner() {
        spnSwitchMargins = findViewById(R.id.spnSwitchMargins);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.margins, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSwitchMargins.setAdapter(adapter);

        spnSwitchMargins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setEnableBtnBySelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initLanguagesSpinner() {
        spnSwitchLang = findViewById(R.id.spnSwitchLang);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSwitchLang.setAdapter(adapter);

        spnSwitchLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setEnableBtnBySelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Locale getLocaleByItemSelected() {
        int position = spnSwitchLang.getSelectedItemPosition();
        String[] languages = getResources().getStringArray(R.array.languages);
        String selectedLanguages = languages[position];
        Locale selectedLocale = null;

        if (selectedLanguages.contains("Рус") || selectedLanguages.contains("Rus")) {
            selectedLocale = new Locale("ru");
        } else if (selectedLanguages.contains("Англ") || selectedLanguages.contains("Eng")) {
            selectedLocale = new Locale("en");
        }

        return selectedLocale;
    }


    public void onClick(View view) {
        Locale selectedLocale = getLocaleByItemSelected();

        config.setLocale(selectedLocale);

        getResources().updateConfiguration(config, getBaseContext().getResources()
                .getDisplayMetrics());

        curLocale = selectedLocale;

        Utils.changeToTheme(this, spnSwitchMargins.getSelectedItemPosition());

        recreate();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPref.edit().putString(CUR_LANG_KEY, curLocale.getLanguage()).commit();
    }

    private void setEnableBtnBySelectedItem() {
        if (getLocaleByItemSelected().getLanguage().equals(curLocale.getLanguage())
                && spnSwitchMargins.getSelectedItemPosition() == Utils.getsTheme()) {
            btnOk.setEnabled(false);
        } else {
            btnOk.setEnabled(true);
        }
    }


}

