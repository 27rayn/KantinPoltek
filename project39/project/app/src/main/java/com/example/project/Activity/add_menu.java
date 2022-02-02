package com.example.project.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.project.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class add_menu extends AppCompatActivity {

    // inisialisasi
    com.rengwuxian.materialedittext.MaterialEditText ETMenu,ETHarga, ETKantin;
    // menampung nilai dari inputan
    String menu,harga,kantin;
    Button BTNSubmit;
    // loading
    ProgressDialog progressDialog;

    // image
    ImageButton BTNImage;
    String pilihan;
    private static final int PHOTO_REQUEST_GALLERY = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    final int CODE_GALLERY_REQUEST = 999;
    // apakah fotonya ambil secara langsung atau dari ambul
    String rPilihan[] = {"Take a photo", "From Album"};
    public final String APP_TAG = "MyApp";
    // krn fotonya kosong jadi di null-kan
    Bitmap bitMap = null;
    public  String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // layout activity yg digunakan
        setContentView(R.layout.activity_add_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // hubungkan java dan layout
        ETMenu       = findViewById(R.id.ETMenu);
        ETHarga      = findViewById(R.id.ETHarga);
        ETKantin   = findViewById(R.id.ETKantin);
        BTNSubmit   = findViewById(R.id.BTNSubmit);


        progressDialog = new ProgressDialog(this);

        // jika klik button image
        BTNImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //condition jika imagenya sudah ada atau terisi
                if (bitMap != null) {

                    // pesan apakah ingin ambil gambar lagi
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(add_menu.this);
                    alertDialogBuilder.setMessage("Do yo want to take photo again?");

                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //call fuction of TakePhoto
                            TakePhoto();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    // jika belum ada foto
                } else {
                    TakePhoto();
                }
            }
        });

        // jika klik button submit
        BTNSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pesan yang muncul
                progressDialog.setMessage("Menambahkan Data...");
                // mengirim data tidak dapat di cancel
                progressDialog.setCancelable(false);
                // menampilkan dialog
                progressDialog.show();

                // ambil data
                menu     = ETMenu.getText().toString();
                harga    = ETHarga.getText().toString();
                kantin   = ETKantin.getText().toString();

                // jika bitmap atau fotonya kosong
                if (bitMap == null){

                    // arahkan user untuk mengambil foto terlebih dahulu
                    AlertDialog.Builder builder = new AlertDialog.Builder(add_menu.this);
                    builder.setMessage("Please take a photo");
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    progressDialog.dismiss();

                } else {
                    // run validasi data
                    validasiData();
                }
            }
        });

    }

    // berisi kondisi
    void validasiData(){
        // jika tidak ada data yang diinputkan
        if(menu.equals("") || harga.equals("") || kantin.equals("")){
            progressDialog.dismiss();
            Toast.makeText(add_menu.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            // jika ni name address hobby terisi
            kirimData();
        }
    }

    // take photo dari kamera
    public void TakePhoto(){

        AlertDialog.Builder builder = new AlertDialog.Builder(add_menu.this);
        builder.setTitle("Select");
        builder.setItems(rPilihan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                pilihan = String.valueOf(rPilihan[which]);

                if (pilihan.equals("Take a photo")) {
                    // create Intent to take a picture and return control to the calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Create a File reference to access to future access
                    photoFile = getPhotoFileUri(photoFileName);

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    String authorities = getPackageName() + ".fileprovider";
                    Uri fileProvider = FileProvider.getUriForFile(add_menu.this, authorities, photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);

                    }
                }
                // external storage atau galeri
                else {

                    ActivityCompat.requestPermissions(add_menu.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);

                }
            }
        });
        builder.show();
    }

    //permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // jika membuka atau memilih galeri
        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else{
                // jika tidak diizinkan
                Toast.makeText(getApplicationContext(), "You don't have permission to access gallery!", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // sudah mengambil foto
    // set foto berapa width, height
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //set photo size
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                // by this point we have the camera photo on disk
                //Bitmap takenImage = BitmapFactory.decodeFile(String.valueOf(photoFile));
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                bitMap = decodeSampledBitmapFromFile(String.valueOf(photoFile), 1000, 700);
                // set ke btn image
                BTNImage.setImageBitmap(bitMap);
            } else { // Result was a failure
                Toast.makeText(add_menu.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // untuk mengambil gambar dari galeri
            if (intent != null) {
                Uri photoUri = intent.getData();
                // Do something with the photo based on Uri
                bitMap = null;
                try {
                    //InputStream inputStream = getContentResolver().openInputStream(photoUri);
                    //bitMap = BitmapFactory.decodeStream(inputStream);

                    //btnImage.setVisibility(View.VISIBLE);
                    bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the selected image into a preview
                BTNImage.setImageBitmap(bitMap);
            }
        }
    }
    //get data photo
    public File getPhotoFileUri(String fileName)  {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

            return file;

        }
        return null;
    }

    //set photo
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    // get encode image to minimize image
    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    // kirim data
    void kirimData(){
        String photo = getStringImage(bitMap);
        // menggunakan library dengan menggunakan post untuk mengirim data
        AndroidNetworking.post("https://tkjb2019.com/mobile/api_kelompok_7/addMahasiswa.php")
                // parameter2 / apa2 saja yg ingin dimasukkan
                .addBodyParameter("menu",""+menu)
                .addBodyParameter("harga",""+harga)
                .addBodyParameter("kantin",""+kantin)
                .addBodyParameter("photo",""+photo)
                // prioritas misalkan ada data yang ingin sama2 mengirim
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(add_menu.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            // jika status true
                            if(status){
                                new AlertDialog.Builder(add_menu.this)
                                        .setMessage("Berhasil Menambahkan Data !")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(add_menu.this, MainActivity.class);
                                                startActivity(i);
                                                add_menu.this.finish();
                                            }
                                        })
                                        .show();
                            }
                            // jika status false
                            else{
                                new AlertDialog.Builder(add_menu.this)
                                        .setMessage("Gagal Menambahkan Data !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(add_menu.this, MainActivity.class);
                                                startActivity(i);
                                                add_menu.this.finish();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    // jika ada error
                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }



}

