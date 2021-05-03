package com.geekhive.studentsoft.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.beans.getallclasses.GetAllClasses;

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

public class SyllabusAdapter extends RecyclerView.Adapter {

        Context context;
        GetAllClasses getAllClasses;
        private String baseUrl;
        ProgressDialog pDialog;
        private String imageName = "Class_Syllabus";

        public SyllabusAdapter(Context context, GetAllClasses getAllClasses) {
            this.context = context;
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
                    new DownloadFileFromURL().execute(baseUrl);
                }
            });


        }

        class DownloadFileFromURL extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                System.out.println("Starting download");

                pDialog = new ProgressDialog(context);
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
                    String imageFileName = imageName + timeStamp + ".xlsx";
                    File myDirectory = new File(Environment.getExternalStorageDirectory(), "StudentSoft");

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
            File myDirectory = new File(Environment.getExternalStorageDirectory(), "StudentSoft");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            Uri uri = Uri.parse(myDirectory.getAbsolutePath());
            intent.setDataAndType(uri, "application/pdf");
            context.startActivity(Intent.createChooser(intent, "Open folder"));
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
