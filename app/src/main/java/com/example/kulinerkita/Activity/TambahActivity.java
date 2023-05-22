package com.example.kulinerkita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kulinerkita.API.APIRequestData;
import com.example.kulinerkita.API.RetroServer;
import com.example.kulinerkita.Model.ModelResponse;
import com.example.kulinerkita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etAsal, etDeskripsiSingkat;
    private Button btnSimpan;
    private String nama, asal, deskripsiSingkat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_name);
        etAsal = findViewById(R.id.et_asal);
        etDeskripsiSingkat = findViewById(R.id.et_deskripsi);
        btnSimpan = findViewById(R.id.btn_tambah);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama =  etNama.getText().toString();
                asal = etAsal.getText().toString();
                deskripsiSingkat = etDeskripsiSingkat.getText().toString();

                if(nama.trim().isEmpty()||asal.trim().isEmpty()||deskripsiSingkat.trim().isEmpty()){
                    if(nama.trim().isEmpty()){
                        etNama.setError("Nama Tidak Boleh Kosong");
                    }
                    if(asal.trim().isEmpty()){
                        etAsal.setError("Asal Tidak Boleh Kosong");
                    }
                    if(deskripsiSingkat.trim().isEmpty()){
                        etDeskripsiSingkat.setError("Deskripsi Tidak Boleh Kosong");
                    }
                }
               else{
                    tambahKuliner();
                }
            }
        });
    }

    private void tambahKuliner(){
        APIRequestData ARD = RetroServer.koneksiRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardCreate(nama, asal, deskripsiSingkat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}