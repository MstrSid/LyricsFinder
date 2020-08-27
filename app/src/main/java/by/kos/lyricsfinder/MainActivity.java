package by.kos.lyricsfinder;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText editTextArtist;
    private EditText editTextSong;
    private Button buttonSearch;
    private TextView textViewLyrics;
    //TODO: find api with capability russian artists

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextArtist = findViewById(R.id.editTextArtist);
        editTextSong = findViewById(R.id.editTextSong);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewLyrics = findViewById(R.id.textViewLyrics);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String artist = editTextArtist.getText().toString();
                    String songName = editTextSong.getText().toString();
                    String url = String.format("https://api.lyrics.ovh/v1/%s/%s", artist, songName);
                    url.replace(" ", "20%");
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                textViewLyrics.setText("");
                                textViewLyrics.setText(response.getString("lyrics"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.this, "Ошибка, проверьте данные", Toast.LENGTH_SHORT).show();
                                }
                            });
            requestQueue.add(jsonObjectRequest);
            }
        });
    }
}