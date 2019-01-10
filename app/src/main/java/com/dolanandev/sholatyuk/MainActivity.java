package com.dolanandev.sholatyuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dolanandev.sholatyuk.api.ApiService;
import com.dolanandev.sholatyuk.api.ApiUrl;
import com.dolanandev.sholatyuk.model.ModelJadwal;

import java.nio.FloatBuffer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tv_lokasi_value, tv_fajar_value, tv_subuh_value, tv_dhuhur_value, tv_ashar_value, tv_maghrib_value, tv_isya_value;
    private FloatingActionButton tab_refresh;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Jadwal Sholat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_lokasi_value = findViewById(R.id.tv_lokasi_value);
        tv_fajar_value = findViewById(R.id.tv_fajar_value);
        tv_subuh_value = findViewById(R.id.tv_subuh_value);
        tv_dhuhur_value = findViewById(R.id.tv_dhuhur_value);
        tv_ashar_value = findViewById(R.id.tv_ashar_value);
        tv_maghrib_value = findViewById(R.id.tv_maghrib_value);
        tv_isya_value = findViewById(R.id.tv_isya_value);
        tab_refresh = findViewById(R.id.tab_refresh);

        getJadwal();

        tab_refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getJadwal();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getJadwal(){

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Silahkan tunggu...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ModelJadwal> call = apiService.getJadwal();

        call.enqueue(new Callback<ModelJadwal>() {
            @Override
            public void onResponse(Call<ModelJadwal> call, Response<ModelJadwal> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()){
                    tv_lokasi_value.setText(response.body().getCity());
                    tv_fajar_value.setText(response.body().getItems().get(0).getFajr());
                    tv_subuh_value.setText(response.body().getItems().get(0).getShurooq());
                    tv_dhuhur_value.setText(response.body().getItems().get(0).getDhuhr());
                    tv_ashar_value.setText(response.body().getItems().get(0).getAsr());
                    tv_maghrib_value.setText(response.body().getItems().get(0).getMaghrib());
                    tv_isya_value.setText(response.body().getItems().get(0).getIsha());
                }else {

                }
            }

            @Override
            public void onFailure(Call<ModelJadwal> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Oouch... Silahkan kembali beberapa saat lagi.. Server Down", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
