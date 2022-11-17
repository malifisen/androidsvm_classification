package com.example.cobapickle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText area,perimeter,diameter,mean,deviation,smoothness,skewness,uniformity,entropy;
    Button predict;
    TextView result;
    String url = "https://silentik.herokuapp.com/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        area = findViewById(R.id.area);
        perimeter = findViewById(R.id.perimeter);
        diameter = findViewById(R.id.diameter);
        mean = findViewById(R.id.mean);
        deviation = findViewById(R.id.deviation);
        smoothness = findViewById(R.id.smoothness);
        skewness = findViewById(R.id.skewness);
        uniformity = findViewById(R.id.uniformity);
        entropy = findViewById(R.id.entropy);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener <String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("class");
                                    if(data.equals("1")){
                                        result.setText("Myeloblast");
                                    }else{
                                        result.setText("Lymphoblast");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map getParams(){
                        Map params = new HashMap();
                        params.put("area",area.getText().toString());
                        params.put("perimeter",perimeter.getText().toString());
                        params.put("diameter",diameter.getText().toString());
                        params.put("mean",mean.getText().toString());
                        params.put("deviation",deviation.getText().toString());
                        params.put("smoothness",smoothness.getText().toString());
                        params.put("skewness",skewness.getText().toString());
                        params.put("uniformity",uniformity.getText().toString());
                        params.put("entropy",entropy.getText().toString());
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}