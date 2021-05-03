package com.geekhive.studentsoft.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.geekhive.studentsoft.R;
import com.geekhive.studentsoft.adapter.LeaveAdapter;
import com.geekhive.studentsoft.adapter.TimeTableAdapter;
import com.geekhive.studentsoft.beans.applyleave.ApplyLeave;
import com.geekhive.studentsoft.beans.applyleave.ApplyLeaveStudent;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.leaveapply.Dates;
import com.geekhive.studentsoft.beans.leaveapply.Leaveapply;
import com.geekhive.studentsoft.beans.leavecancel.Leavecancel;
import com.geekhive.studentsoft.beans.leaveimage.LeaveImage;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.beans.studentapplyleave.StudentleaveApply;
import com.geekhive.studentsoft.utils.ConnectionDetector;
import com.geekhive.studentsoft.utils.DrawableUtils;
import com.geekhive.studentsoft.utils.OnResponseListner;
import com.geekhive.studentsoft.utils.Prefs;
import com.geekhive.studentsoft.utils.SnackBar;
import com.geekhive.studentsoft.utils.WebServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.geekhive.studentsoft.utils.util.IMAGE_DIRECTORY_NAME;
import static com.geekhive.studentsoft.utils.util.REQUEST_FOR_STORAGE_PERMISSION;
import static com.geekhive.studentsoft.utils.util.REQUEST_TAKE_PHOTO_ADHAR_FRONT;
import static com.geekhive.studentsoft.utils.util.REQUEST_TAKE_PHOTO_COLLEGE_FRONT;
import static com.geekhive.studentsoft.utils.util.REQUEST_TAKE_PHOTO_PROFILE_PIC;
import static com.geekhive.studentsoft.utils.util.SELECT_FILE_ADHAR_FRONT;
import static com.geekhive.studentsoft.utils.util.SELECT_FILE_COLEEGE_FRONT;
import static com.geekhive.studentsoft.utils.util.SELECT_FILE_PROFILE_PIC;


public class LeaveManagementStudent extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.backPress)
    ImageView backPress;
    @BindView(R.id.vr_leaves)
    RecyclerView vrLeaves;
    LeaveAdapter leaveAdapter;
    @BindView(R.id.fab_add_leave)
    FloatingActionButton fabAddLeave;
    DatePickerDialog.OnDateSetListener setListenerStart;
    DatePickerDialog.OnDateSetListener setListenerEnd;
    String dateS;
    Dialog dialogSuccess;
    ConnectionDetector mDetector;
    List<String> datelist = new ArrayList<>();
    Dates dates;
    CalendarPickerView calendar;
    EditText reason_text;
    Date date = null;
    String[] classHW = {"Sick", "Normal"};

    ImageView collegeFrontImage;
    RelativeLayout collegeFrontRV;
    DialogInterface dialog;
    private String filepathprofilepic = "";
    String fileNameProfilepic;
    Bitmap bitmap;
    Uri fileUri;
    Bitmap bitmapFromGallery;
    private String uriSting;
    String mFileNameGallery;
    Spinner class_spinner;
    List<String> image = new ArrayList<>();
    List<String> listofdate = new ArrayList<>();
    GetStudentById getStudentById;
    LeaveImage leaveImage;
    Leavecancel leavecancel;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_student_leavemanagement);
        ButterKnife.bind(this);
        mDetector = new ConnectionDetector(this);
        id = getIntent().getStringExtra("id");

        if(id != null){
            if(!id.equals("")){
                CallstudentService(id);
            }else {
                CallstudentService(Prefs.getPrefRefId(this));
            }
        }



        fabAddLeave.setOnClickListener(this);
        backPress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPress:
                finish();
                break;
            case R.id.fab_add_leave:
                Initializepopup();
                initializdeletePopup();
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

    private void Initializepopup() {
        dialogSuccess = new Dialog(this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_student_leave_apply);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_student_leave_apply);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        calendar = dialogSuccess.findViewById(R.id.calendar_view);
        reason_text = dialogSuccess.findViewById(R.id.reason_text);
        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("YYYY, MMM ,DD", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        calendar.scrollToDate(new Date());

        class_spinner = dialogSuccess.findViewById(R.id.spinner_class);
        collegeFrontImage = dialogSuccess.findViewById(R.id.iv_AddImageFrontOne);
        collegeFrontRV = dialogSuccess.findViewById(R.id.collegeFrontRV);

        collegeFrontRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_PROFILE_PIC, REQUEST_TAKE_PHOTO_PROFILE_PIC);

                }
            }
        });

        Button btnSubmitLeave = dialogSuccess.findViewById(R.id.btn_submit_leave);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        ArrayAdapter classAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, classHW);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        class_spinner.setAdapter(classAdapter);

        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LeaveManagementStudent.this, classHW[position], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSubmitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();
                    String lunchTime2 = calendar.getSelectedDates().toString();

                    Toast.makeText(LeaveManagementStudent.this, "list " + lunchTime2, Toast.LENGTH_LONG).show();
                    datelist = new ArrayList<>();
                    for (int i = 0; i < calendar.getSelectedDates().size(); i++) {
                        Log.e("Dates", String.valueOf(calendar.getSelectedDates().get(i).getTime()));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String cdate = dateFormat.format(calendar.getSelectedDates().get(i).getTime());
                        Log.e("cdate", cdate);
                        datelist.add(cdate);
                    }
                    if (datelist.size() != 0 && ! filepathprofilepic.equals("") ) {
                        CallImageService();
                        //CallService();
                    }

                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }

    private boolean mayRequestGalleryImages() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        requestPermissions(
                new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                REQUEST_FOR_STORAGE_PERMISSION);
        return false;
    }
    private void selectImage(final int selectfile, final int takephoto) {
        View view = getLayoutInflater().inflate(R.layout.take_image_popup, null);
        dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);
        final TextView chooseFromGallery = (TextView) view.findViewById(R.id.choose_from_gallery);
        final TextView tekePhoto = (TextView) view.findViewById(R.id.take_photo);
        final TextView select_photo = (TextView) view.findViewById(R.id.select_photo);
        final TextView cancel = (TextView) view.findViewById(R.id.cancel);
        new Runnable() {
            public void run() {
            }
        }.run();
        chooseFromGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), selectfile);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        tekePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(takephoto);
                    } catch (IOException ex) {
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = null;
                        try {
                            photoURI = FileProvider.getUriForFile(LeaveManagementStudent.this, getApplicationContext().getPackageName() + ".provider", createImageFile(takephoto));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, takephoto);
                    }
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.show();
    }
    private File createImageFile(int phot) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "studeEMI"+timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (phot == SELECT_FILE_PROFILE_PIC || phot == REQUEST_TAKE_PHOTO_PROFILE_PIC){
            filepathprofilepic = storageDir.getAbsolutePath() + "/" + imageFileName;
            File file = new File(filepathprofilepic);
            return file;
        }else {
            return null;
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == 100) {
            if (mDetector.isConnectingToInternet()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                String[] fileTemp = fileUri.getPath().split("/");
                /*fileName = fileTemp[fileTemp.length - 1];
                filePath = fileUri.getPath();*/
                compressImage(fileUri.getPath(), 1);

                //vI_ad_image_display.setImageBitmap(bitmap);

                return;
            }
            SnackBar.makeText((Context) this, "no_internet", SnackBar.LENGTH_SHORT).show();
        } else if (requestCode == SELECT_FILE_PROFILE_PIC) {
            onSelectFromGalleryResult(data, SELECT_FILE_PROFILE_PIC);
            if (mDetector.isConnectingToInternet()) {
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                collegeFrontImage.setImageBitmap(bitmapFromGallery);
               // CallImageService();
            } else {
                SnackBar.makeText((Context) this, "no_internet", SnackBar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO_PROFILE_PIC && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView

            String[] fileTemp = filepathprofilepic.split("/");
            filepathprofilepic = fileTemp[fileTemp.length - 1];
            Uri imageUri = Uri.parse(filepathprofilepic);
            filepathprofilepic = imageUri.getPath();
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                collegeFrontImage.setImageBitmap(BitmapFactory.decodeStream(ims));
                //CallImageService();

            } catch (FileNotFoundException e) {
                return;
            }

            MediaScannerConnection.scanFile(this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
    }
    private void onSelectFromGalleryResult(Intent data, int resultImage) {

        Uri selectedImageUri = data.getData();
        String selectedImagePath = getRealPathFromURI(selectedImageUri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        int scale = 1;
        while ((options.outWidth / scale) / 2 >= ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION && (options.outHeight / scale) / 2 >= ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            scale *= 2;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        compressImage(selectedImagePath, 2);
        Bitmap bm = BitmapFactory.decodeFile(uriSting, options);
        fetchImageName(selectedImagePath, resultImage);
    }
    private void fetchImageName(String selectedImagePath, int id) {
        mFileNameGallery = "";
        StringTokenizer st = new StringTokenizer(selectedImagePath, "/");
        while (st.hasMoreTokens()) {
            mFileNameGallery = st.nextToken().toString();
        }
        if (id == SELECT_FILE_PROFILE_PIC){
            filepathprofilepic = selectedImagePath;
            fileNameProfilepic = mFileNameGallery;
        }

        selectedImagePath = selectedImagePath;
    }
    public String compressImage(String imageUri, int flag) {
        String filename;
        OutputStream outputStream;
        FileNotFoundException e;
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float imgRatio = (float) (actualWidth / actualHeight);
        float maxRatio = 612.0f / 816.0f;
        if (((float) actualHeight) > 816.0f || ((float) actualWidth) > 612.0f) {
            if (imgRatio < maxRatio) {
                actualWidth = (int) (((float) actualWidth) * (816.0f / ((float) actualHeight)));
                actualHeight = (int) 816.0f;
            } else if (imgRatio > maxRatio) {
                actualHeight = (int) (((float) actualHeight) * (612.0f / ((float) actualWidth)));
                actualWidth = (int) 612.0f;
            } else {
                actualHeight = (int) 816.0f;
                actualWidth = (int) 612.0f;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16384];
        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
            bitmapFromGallery = bmp;
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception2) {
            exception2.printStackTrace();
        }
        float ratioX = ((float) actualWidth) / ((float) options.outWidth);
        float ratioY = ((float) actualHeight) / ((float) options.outHeight);
        float middleX = ((float) actualWidth) / 2.0f;
        float middleY = ((float) actualHeight) / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - ((float) (bmp.getWidth() / 2)), middleY - ((float) (bmp.getHeight() / 2)), new Paint(2));
        try {
            int orientation = new ExifInterface(filePath).getAttributeInt("Orientation", 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90.0f);
            } else if (orientation == 3) {
                matrix.postRotate(180.0f);
            } else if (orientation == 8) {
                matrix.postRotate(270.0f);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (flag == 1) {
            filename = imageUri;
        } else {
            filename = getFilename();
        }
        try {
            OutputStream fileOutputStream = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            outputStream = fileOutputStream;
        } catch (FileNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            return filename;
        }
        return filename;
    }
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
        }
        while (((float) (width * height)) / ((float) (inSampleSize * inSampleSize)) > ((float) ((reqWidth * reqHeight) * 2))) {
            inSampleSize++;
        }
        return inSampleSize;
    }
    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        uriSting = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
        return uriSting;
    }
    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String path = cursor.getString(idx);
            cursor.close();
            return path;
        }
    }
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        }
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("_data"));
    }

    private void CallImageService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Leaveimageimage(WebServices.SM_Services,
                    WebServices.ApiType.leaveimage,fileNameProfilepic,filepathprofilepic,Prefs.getPrefUserAuthenticationkey(this));
        } else {
            SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
        }
        return;
    }
    private void CallstudentService(String id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetStudentbyid(WebServices.SM_Services,
                    WebServices.ApiType.getstudentbyid,id,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }
    private void CallCancelLeave() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).CancelLeave(WebServices.SM_Services,
                    WebServices.ApiType.cancelleave, Prefs.getPrefRefId(this),dates,Prefs.getPrefUserAuthenticationkey(this));
        }
        return;
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            if (!reason_text.getText().toString().isEmpty() || !reason_text.getText().toString().equals("")) {
                ApplyLeaveStudent applyLeaveStudent = new ApplyLeaveStudent();
                applyLeaveStudent.setDates(datelist);
                applyLeaveStudent.setReason(class_spinner.getSelectedItem().toString());
                applyLeaveStudent.setMediaUrl(leaveImage.getResult().getMessage());
                new WebServices(this).ApplyStudentLeave(WebServices.SM_Services,
                        WebServices.ApiType.applyleave, Prefs.getPrefRefId(this), applyLeaveStudent,Prefs.getPrefUserAuthenticationkey(this));
            } else {
                SnackBar.makeText(LeaveManagementStudent.this, "Please enter reason of leave", SnackBar.LENGTH_SHORT).show();
            }
        } else {
            SnackBar.makeText(LeaveManagementStudent.this, "No internet connectivity", SnackBar.LENGTH_SHORT).show();
        }
        return;
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.applyleave) {
            if (!isSucces) {
                SnackBar.makeText(LeaveManagementStudent.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                final StudentleaveApply studentleaveApply = (StudentleaveApply) response;

                if (!isSucces || studentleaveApply == null) {
                    SnackBar.makeText(LeaveManagementStudent.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    //CallImageService();
                    SnackBar.makeText(LeaveManagementStudent.this, "Sucess", SnackBar.LENGTH_SHORT).show();
                }
            }
        } if (apiType == WebServices.ApiType.leaveimage) {
            if (!isSucces) {
                SnackBar.makeText(LeaveManagementStudent.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                leaveImage = (LeaveImage) response;

                if (!isSucces || leaveImage == null) {
                    SnackBar.makeText(LeaveManagementStudent.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    image = new ArrayList<>();
                    image.add(leaveImage.getResult().getMessage().get(0));
                    CallService();
                    SnackBar.makeText(LeaveManagementStudent.this,"Leave image uploded", SnackBar.LENGTH_SHORT).show();
                }
            }
        }if (apiType == WebServices.ApiType.getstudentbyid) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                getStudentById = (GetStudentById) response;

                if (!isSucces || getStudentById.getResult() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } if (!isSucces || getStudentById.getResult().getMessage() == null) {
                    SnackBar.makeText(this,getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    vrLeaves.setLayoutManager(linearLayoutManager);
                    leaveAdapter = new LeaveAdapter(this,getStudentById);
                    vrLeaves.setAdapter(leaveAdapter);
                    SnackBar.makeText(this,getStudentById.getResult().getMessage().getFirstName(), SnackBar.LENGTH_SHORT).show();
                }
            }
        }if (apiType == WebServices.ApiType.leaveimage) {
            if (!isSucces) {
                SnackBar.makeText(LeaveManagementStudent.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                leaveImage = (LeaveImage) response;

                if (!isSucces || leaveImage == null) {
                    SnackBar.makeText(LeaveManagementStudent.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    image = new ArrayList<>();
                    image.add(leaveImage.getResult().getMessage().get(0));
                    CallService();
                    SnackBar.makeText(LeaveManagementStudent.this,"Leave image uploded", SnackBar.LENGTH_SHORT).show();
                }
            }
        }if (apiType == WebServices.ApiType.cancelleave) {
            if (!isSucces) {
                SnackBar.makeText(LeaveManagementStudent.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                leavecancel = (Leavecancel) response;

                if (!isSucces || leavecancel.getResult().getMessage() == null) {
                    SnackBar.makeText(LeaveManagementStudent.this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(LeaveManagementStudent.this,"Leave Canceled", SnackBar.LENGTH_SHORT).show();
                    Intent intent = new Intent(LeaveManagementStudent.this, LeaveManagementStudent.class);
                    startActivity(intent);
                }
            }
        }
    }
    public class LeaveAdapter extends RecyclerView.Adapter {

        Context context;
        GetStudentById getStudentById;
        public LeaveAdapter(Context context,GetStudentById getStudentById) {
            this.context = context;
            this.getStudentById = getStudentById;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_leave_item_list,parent,false);
            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ListViewHolder)holder).bindView(position);
            ((ListViewHolder)holder).tvClass.setText("Date:"+getStudentById.getResult().getMessage().getAttendance().get(position).getDate());
            ((ListViewHolder)holder).cancel_leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dates = new Dates();
                    datelist.add(getStudentById.getResult().getMessage().getAttendance().get(position).getDate());
                    dates.setDates(datelist);
                    CallCancelLeave();
                }
            });
         }

        @Override
        public int getItemCount() {
            return getStudentById.getResult().getMessage().getAttendance().size();
        }

        private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            LinearLayout llClass;
            TextView tvClass,reason,status;
            ImageView cancel_leave;
            public ListViewHolder(@NonNull View itemView) {
                super(itemView);
                llClass = itemView.findViewById(R.id.llClass);
                tvClass = itemView.findViewById(R.id.tvClass);
                cancel_leave = itemView.findViewById(R.id.cancel_leave);
            }

            public void bindView(int position){
            }

            @Override
            public void onClick(View view) {
            }
        }
    }
}
