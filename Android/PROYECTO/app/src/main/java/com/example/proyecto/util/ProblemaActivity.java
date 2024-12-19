package com.example.proyecto.util;

import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.models.Problema;
import com.example.proyecto.services.ProblemaService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProblemaActivity extends AppCompatActivity {
    private EditText editTextFecha;
    private EditText editTextTitulo;
    private EditText editTextMensaje;
    public static final String BASE_URI = "http://10.0.2.2:8080/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);
        editTextFecha = findViewById(R.id.FechaText);
        editTextMensaje = findViewById(R.id.MensajeText);
        editTextTitulo = findViewById(R.id.TituloText);
    }

    public void VolverOnClick(View v) {
        Intent intent = new Intent(ProblemaActivity.this, MenuUsuario.class);
        startActivity(intent);
        finish();
    }

    public void EnviarOnClick(View v) {
            String titulo = editTextTitulo.getText().toString();
            String mensaje = editTextMensaje.getText().toString();
            String fecha= editTextFecha.getText().toString();
            //Validar el input
            if (titulo.isEmpty() || mensaje.isEmpty() || fecha.isEmpty()) {

                Toast.makeText(this, "Rellena los campos.", Toast.LENGTH_SHORT).show();
                return;

            }
            SharedPreferences prefs= getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            int id = prefs.getInt("id", -1);
            Problema denunciaCliente = new Problema(fecha, titulo, mensaje, id);
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URI)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ProblemaService denunciar = retrofit.create(ProblemaService.class);
            Call<Void> call = denunciar.denuncia(denunciaCliente);
            String respuesta = null;

            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(ProblemaActivity.this, "Denuncia realizada correctamente.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        //Por si falla el login
                        Toast.makeText(ProblemaActivity.this, "Ha fallado el login. Inténtalo otra vez.", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                    Toast.makeText(ProblemaActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("Error", t.getMessage());
                }

            });
        }
    }


