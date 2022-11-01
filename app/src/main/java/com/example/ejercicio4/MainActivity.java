package com.example.ejercicio4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
   Button btnCamara;
   ImageView visor;
   String ruta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamara=findViewById(R.id.btnCamara);
        visor=findViewById(R.id.iv_visor);

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  && ActivityCompat.
                checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA}, 1000);


        }





        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camara();
            }
        });

    }
//abre o activa la camara
    private void camara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            File fotoArchivo = null;
            try {
                fotoArchivo = GuardarImagen();
            }catch (IOException ex)
            {
                Log.e("error", ex.toString());

            }


            if(fotoArchivo !=null){
                Uri uri = FileProvider.getUriForFile(this,"com.example.ejercicio4.fileprovider",fotoArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            startActivityForResult(intent,1);
            }



        }
    }
    //este bloque captura la imagen
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && resultCode == RESULT_OK){
           // Bundle extras = data.getExtras();
          //  Bitmap imgBitmap = (Bitmap) extras.get("data");
            Bitmap imgBitmap = BitmapFactory.decodeFile(ruta);
            visor.setImageBitmap(imgBitmap);
        }
    }
    private  File GuardarImagen()throws IOException{
        String nombreFoto = "foto";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File foto = File.createTempFile(nombreFoto,".jpg",directorio);
        ruta=foto.getAbsolutePath();
        return foto;
    }

    }
