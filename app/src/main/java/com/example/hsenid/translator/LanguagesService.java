package com.example.hsenid.translator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class LanguagesService extends AsyncTask<Void, Void, String[]> {

    protected HashMap<String, String> result =null;
    private String urlTemplate= "";
    private String yandexUrl;
    private JSONObject obj;
    private Boolean langLoded = false;
    private String[] langArr;
    private Spinner fromLang;
    private Spinner toLang;
    private Context context;

    public LanguagesService(){}

    public LanguagesService(Spinner fromLang, Spinner toLang, Context context){
        this.fromLang = fromLang;
        this.context = context;
        this.toLang = toLang;
    }

    public String[] getLanguages() {
        try {
            URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170829T103611Z.a786a48def3eb11d.7178a4e058ec08a5a9e78c6fee730131a2b328a3&ui=en");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            obj = new JSONObject(br.readLine()).getJSONObject("langs");
            result = new ObjectMapper().readValue(obj.toString(), HashMap.class);
            langArr = result.values().toArray(new String[0]);
            Log.d("D", "Test log");
            conn.disconnect();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return langArr;
    }


    public Object getKeyFromValue(Object value) {
        for (Object o : result.keySet()) {
            if (result.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        return getLanguages();

    }


    @Override
    protected void onPostExecute(String[] langs) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, langs);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        fromLang.setAdapter(spinnerArrayAdapter);
        toLang.setAdapter(spinnerArrayAdapter);
    }

}
