package com.arifcebe.artistdemenan.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arifcebe.artistdemenan.R;
import com.arifcebe.artistdemenan.config.BaseApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadImageActivity extends AppCompatActivity {

    Button btnPilih, btnUpload,btnLiat;
    ImageView previewImage;

    public static final int CHOOSE_FILE =2;
    private static final String URL_UPLOAD = "http://api.arifcebe.com/sample_json/upload_image.php";
    private Bitmap bitmap;
    private Uri filePath;
    private String imgPath;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        btnPilih = (Button) findViewById(R.id.buttonChoose);
        btnUpload = (Button) findViewById(R.id.buttonUpload);
        btnLiat = (Button) findViewById(R.id.buttonViewImage);
        previewImage = (ImageView) findViewById(R.id.imageView);


        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImage();
            }
        });
    }

    /**
     * pilih gambar dari galeri
     */
    private void pilihGambar(){
        Intent intent = new Intent();
        intent.setType("image/**");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih gambar"), CHOOSE_FILE);
    }

    /**
     * convert gambar ke string
     * @param bmp
     * @return
     */
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void sendImage(){

        BaseApplication.getInstance().startLoader(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        BaseApplication.getInstance().stopLoader();
                        Log.d("upload", "response upload " + response);
                        try {
                            JSONObject jo = new JSONObject(response);
                            String msg = jo.getString("message");

                            Toast.makeText(UploadImageActivity.this,
                                    msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        BaseApplication.getInstance().stopLoader();
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            Log.d("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                        }

                        if (error instanceof TimeoutError) {
                            Log.d("Volley", "TimeoutError");
                        } else if (error instanceof NoConnectionError) {
                            Log.d("Volley", "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.d("Volley", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.d("Volley", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.d("Volley", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.d("Volley", "ParseError");
                        }
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();

                params.put("image", getStringImage(bitmap));
                params.put("filename", fileName);
                Log.d("upload", "path file " + fileName);

                return params;
            }
        };

        BaseApplication.getInstance().addToRequestQueue(request, "uploadImage");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_FILE && resultCode == RESULT_OK
                && data !=null && data.getData() != null){
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                previewImage.setImageBitmap(bitmap);

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
