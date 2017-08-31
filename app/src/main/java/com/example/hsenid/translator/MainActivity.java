package com.example.hsenid.translator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerTo;
    private Spinner spinnerFrom;
    private String inputLang;
    private String outputLang;
    private EditText displayText;
    private EditText inputText;
    String inputString= "";
    private LanguagesService languagesService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLanguages();
    }

    public void getLanguages() {
//        googleBtn = (RadioButton) findViewById(R.id.radioGoogle);
//        googleBtn.setEnabled(false);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        languagesService = new LanguagesService(spinnerFrom, spinnerTo, MainActivity.this);
        languagesService.execute();
    }


    public void getTranslated(View view){
        inputText = (EditText) findViewById((R.id.originalWord_text));
        displayText = (EditText) findViewById((R.id.translatedWord_text));
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        inputLang = languagesService.getKeyFromValue(spinnerFrom.getSelectedItem()).toString();
        outputLang = languagesService.getKeyFromValue(spinnerTo.getSelectedItem()).toString();
        inputString = inputText.getText().toString();

        if (!inputText.getText().toString().equals("")) {
            TranslationService translate = new TranslationService(inputLang, outputLang, inputString, displayText);
            translate.execute();
        }else {
            Toast.makeText(getApplicationContext(), "Please Enter a word", Toast.LENGTH_LONG).show();
        }

    }
    public void onClickClear(View view){
        inputString = "";
        inputText.setText(inputString);
        displayText.setText(inputString);

    }
}
