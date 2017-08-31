package com.example.hsenid.translator;

import android.os.AsyncTask;
import android.widget.EditText;

import com.example.hsenid.translator.models.Words;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hsenid on 8/30/17.
 */

public class TranslationService  extends  AsyncTask<Void, Void, String[]> {
    private String inputLang;
    private String outputLang;
    private String input;
    private EditText displayText;
    private Words convertedWords= new Words();
    private Gson gson = new Gson();

    public TranslationService(String inputLang, String outputLang, String input, EditText displayText) {
        this.inputLang = inputLang;
        this.outputLang = outputLang;
        this.input = input;
        this.displayText = displayText;
       // this.translateMode = translateMode;
    }


    public String[] doTranslateYandex(String inputLang, String outputLang, String wordToConvert) {
        try {


            URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170829T103611Z.a786a48def3eb11d.7178a4e058ec08a5a9e78c6fee730131a2b328a3&text=" + wordToConvert + "&lang=" + inputLang + "-" + outputLang);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            convertedWords = gson.fromJson(br.readLine(), Words.class);
            conn.disconnect();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedWords.getText();
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        return doTranslateYandex(inputLang,outputLang, input);
    }

    protected void onPostExecute(String[] translatedText){
        displayText.setText(translatedText[0]);

    }

}
