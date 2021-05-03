package com.geekhive.studentsoft.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.StudentBHAdapter;
import com.geekhive.studentsoft.adapter.SyllabusAdapter;
import com.geekhive.studentsoft.beans.getallclasses.GetAllClasses;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.geekhive.studentsoft.utils.util.REQUEST_FOR_STORAGE_PERMISSION;

public class SyllabusActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.vr_syllabus)
    RecyclerView vrSyllabus;
    @BindView(R.id.backPress)
    ImageView backPress;
    SyllabusAdapter syllabusAdapter;
    ConnectionDetector mDetector;
    GetAllClasses getAllClasses;
    String dbkey;
    ProgressDialog pDialog;
    String className;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        dbkey = getIntent().getStringExtra("dbkey");

        if (mayRequestReadWriteSd()){
            CallInitial();
        }
        backPress.setOnClickListener(this);
    }

    private void CallInitial(){
        if(dbkey != null){
            CallSchoolwiseService();
        }else {
            CallService();
        }

    }
    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Getallclas(WebServices.SM_Services,
                    WebServices.ApiType.getclasses,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    private void CallSchoolwiseService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Getallclas(WebServices.SM_Services,
                    WebServices.ApiType.getclasses,dbkey);
        }
        return;
    }
    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.getclasses) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getAllClasses = (GetAllClasses) response;

                if (!isSucces || getAllClasses.getResult().getMessage() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    vrSyllabus.setLayoutManager(linearLayoutManager);
                    syllabusAdapter = new SyllabusAdapter(getAllClasses);
                    vrSyllabus.setAdapter(syllabusAdapter);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.login_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public class SyllabusAdapter extends RecyclerView.Adapter {

        GetAllClasses getAllClasses;
        private String baseUrl;
        private String imageName = "Class_Syllabus";

        public SyllabusAdapter(GetAllClasses getAllClasses) {
            this.getAllClasses = getAllClasses;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabus_item_list, parent, false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder) holder).bindView(position);
            ((ListViewHolder) holder).tvHMS.setText(getAllClasses.getResult().getMessage().get(position).getClassName() + " Class Syllabus");
            ((ListViewHolder) holder).llClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseUrl = "http://" + getAllClasses.getResult().getMessage().get(position).getSyllabus();
                    className = getAllClasses.getResult().getMessage().get(position).getClassName() + "Class";
                    new DownloadFileFromURL().execute(baseUrl);
                }
            });


        }



        @Override
        public int getItemCount() {
            return getAllClasses.getResult().getMessage().size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            LinearLayout llClass;
            TextView tvHMS, tvHWDue;

            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                llClass = itemView.findViewById(R.id.llClass);
                tvHMS = itemView.findViewById(R.id.tvHMS);
                tvHWDue = itemView.findViewById(R.id.tvHWDue);
            }

            public void bindView(int position) {
            }

            @Override
            public void onClick(View view) {
            }
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");

            pDialog = new ProgressDialog(SyllabusActivity.this);
            pDialog.setMessage("Loading... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                String root = Environment.getExternalStorageDirectory().toString();

                System.out.println("Downloading");
                URL url = new URL(f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String imageFileName =  className + timeStamp + ".pdf";
                File myDirectory = new File(Environment.getExternalStorageDirectory(), "Student Soft");

                if (!myDirectory.exists()) {
                    myDirectory.mkdirs();
                }

                File fileWithinMyDir = new File(myDirectory, imageFileName); //Getting a file within the dir.
                OutputStream output = new FileOutputStream(fileWithinMyDir);
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);

                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }


        /**
         * After completing background task
         **/
        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("Downloaded");
            pDialog.dismiss();
            openFolder();
        }

    }

    public void openFolder() {
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "Student Soft");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(myDirectory.getAbsolutePath());
        intent.setDataAndType(uri, "application/pdf");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }

    private boolean mayRequestReadWriteSd() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(
                new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_FOR_STORAGE_PERMISSION);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FOR_STORAGE_PERMISSION:
                Log.e("Permission", "entered");
                if (grantResults.length > 0) {
                    Log.e("Permission", "entered1");
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        CallInitial();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showPermissionRationaleSnackBar();
                        } else {
                            Toast.makeText(this, "Go to settings and enable permission", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
    }

    private void showPermissionRationaleSnackBar() {
        SnackBar.makeText(this,
                "Please Grant Permissions",
                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(SyllabusActivity.this,
                                new String[]{Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_FOR_STORAGE_PERMISSION);
                    }
                }).show();
    }
}
