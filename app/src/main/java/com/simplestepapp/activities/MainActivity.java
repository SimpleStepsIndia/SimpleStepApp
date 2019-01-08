package com.simplestepapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.Task;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.simplestepapp.BuildConfig;
import com.simplestepapp.R;
import com.simplestepapp.utils.ConnectivityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private AppCompatButton btn_SignUp;

    private SignInButton btn_sign_in;

    private AppCompatImageView img_DOB;

    public AppCompatEditText edtTxt_Name, edtTxt_EmailId, edtTxt_MobNumber, edt_Txt_DOB;

    public double latitude = 0.0, longitude = 0.0;

    public static ArrayList<String> timeSlots = new ArrayList<>();

    CircleImageView img_Profile;

    private Uri outputFileUri;

    public static Bitmap bitmapCaptredImg;

    String imageStatus = "0", str_Date;

    Calendar calendar;

    private int year, month, day, hour, minute;

    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarsetUp();
        timeSlots = new ArrayList<>();
        timeSlots.add("5:00");
        timeSlots.add("5:15");
        timeSlots.add("5:30");
        timeSlots.add("5:45");
        timeSlots.add("6:00");
        timeSlots.add("6:15");
        timeSlots.add("6:30");
        timeSlots.add("6:45");
        timeSlots.add("7:00");
        timeSlots.add("7:15");
        timeSlots.add("7:30");
        timeSlots.add("7:45");
        initviews();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btn_sign_in.setSize(SignInButton.SIZE_STANDARD);
        btn_sign_in.setScopes(gso.getScopeArray());


        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* if (edtTxt_Name.getText().toString().isEmpty()) {
                    edtTxt_Name.setError("Please Enter the Name !");
                    edtTxt_Name.requestFocus();
                } else if (edtTxt_EmailId.getText().toString().isEmpty() ||
                        !ValidationUtils.isValidEmaillId(edtTxt_EmailId.getText().toString().trim())) {
                    edtTxt_EmailId.setError("Please Enter valid Email !");
                    edtTxt_EmailId.requestFocus();
                } else if (edtTxt_MobNumber.getText().toString().trim().isEmpty() ||
                        !ValidationUtils.isValidMobile(edtTxt_MobNumber.getText().toString().trim())) {
                    edtTxt_MobNumber.setError("Please Enter the Valid Mobile Number !");
                } else if (edt_Txt_DOB.getText().toString().isEmpty()) {
                    edt_Txt_DOB.setError("Please Enter the DOB !");
                    edt_Txt_DOB.requestFocus();
                } else if (imageStatus.equals("0")) {
                    Toaster.showWarningMessage("Please Capture the Image !");
                } else {
                    Intent intent_Pager = new Intent(getApplicationContext(), ViewPagerActivity.class);
                    startActivity(intent_Pager);
                }*/

                Intent intent_Pager = new Intent(getApplicationContext(), ViewPagerActivity.class);
                startActivity(intent_Pager);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        img_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        img_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        str_Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        edt_Txt_DOB.setText(str_Date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.setTitle("Please Select Date");
                datePickerDialog.show();
            }
        });
    }

    private void initviews() {

        edtTxt_Name = findViewById(R.id.edtTxt_Name);
        btn_sign_in=findViewById(R.id.btn_sign_in);
        edtTxt_EmailId = (AppCompatEditText) findViewById(R.id.edtTxt_EmailId);
        edtTxt_MobNumber = (AppCompatEditText) findViewById(R.id.edtTxt_MobNumber);
        edt_Txt_DOB = (AppCompatEditText) findViewById(R.id.edt_Txt_DOB);
        btn_SignUp = (AppCompatButton) findViewById(R.id.btn_SignUp);
        img_Profile = (CircleImageView) findViewById(R.id.img_Profile);
        img_DOB = (AppCompatImageView) findViewById(R.id.img_DOB);

    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.d("GmailData",""+result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            Log.d("Info", "Name: " + personName + ", email: " + email);
            Intent intent_Pager = new Intent(getApplicationContext(), ViewPagerActivity.class);
            startActivity(intent_Pager);


        } else {
            // Signed out, show unauthenticated UI.

            Intent intent_Pager = new Intent(getApplicationContext(), ViewPagerActivity.class);
            startActivity(intent_Pager);

        }
    }

    public void toolbarsetUp() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.lyt_header, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void requestPermissions() {
        Permissions.check(this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,},
                "Camera and storage permissions are required because...", new Permissions.Options()
                        .setRationaleDialogTitle("Info"),
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        if (ConnectivityUtils.locationServicesEnabled(MainActivity.this)) {
                            try {
                                int locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                                if (locationMode == LOCATION_MODE_HIGH_ACCURACY) {
                                    latitude = ConnectivityUtils.latitude;
                                    longitude = ConnectivityUtils.longitude;
                                    selectImage();
                                    Toast.makeText(getApplicationContext(), "Enable" + ConnectivityUtils.latitude + "Enable" + ConnectivityUtils.longitude, Toast.LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            } catch (Settings.SettingNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ConnectivityUtils.locationSettings(MainActivity.this);
                        }

                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        Toast.makeText(context, "Camera+Storage Denied:\n" + Arrays.toString(deniedPermissions.toArray()),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public boolean onBlocked(Context context, ArrayList<String> blockedList) {
                        Toast.makeText(context, "Camera+Storage blocked:\n" + Arrays.toString(blockedList.toArray()),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public void onJustBlocked(Context context, ArrayList<String> justBlockedList,
                                              ArrayList<String> deniedPermissions) {
                        Toast.makeText(context, "Camera+Storage just blocked:\n" + Arrays.toString(deniedPermissions.toArray()),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void selectImage() {
        new MaterialDialog.Builder(MainActivity.this).title(R.string.filetype).items(R.array.filetypes).itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                if (position == 0) {
                    captureImagefrom_Camera();
                } else if (position == 1) {
                    selectImagefrom_Gallery();
                }
            }
        }).show();
    }

    private void captureImagefrom_Camera() {

        if (Build.VERSION.SDK_INT <= 23) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(intent, 1);

        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(MainActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider", f));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 1);
        }
    }

    private void selectImagefrom_Gallery() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            imageStatus = "1";
            String picturePath = "";
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "SimpleSteps";
                    File camerapath = new File(path);

                    if (!camerapath.exists()) {
                        camerapath.mkdirs();
                    }
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(camerapath, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {
                        outFile = new FileOutputStream(file);
                        outputFileUri = Uri.fromFile(file);
                        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                        mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, outFile);
                        outFile.flush();
                        outFile.close();
                        bitmapCaptredImg = mutableBitmap;
                        img_Profile.setImageBitmap(mutableBitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        bitmapCaptredImg = null;
                        imageStatus = "0";

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    bitmapCaptredImg = null;
                    imageStatus = "0";
                }

            } else if (requestCode == 2) {

                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                    if (thumbnail != null) {
                        Bitmap mutableBitmap = thumbnail.copy(Bitmap.Config.ARGB_8888, true);
                        img_Profile.setImageBitmap(mutableBitmap);
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes);
                        bitmapCaptredImg = mutableBitmap;

                    } else if (thumbnail == null) {
                        bitmapCaptredImg = null;
                        imageStatus = "0";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    bitmapCaptredImg = null;
                    imageStatus = "0";

                }
            } else {
                bitmapCaptredImg = null;
                imageStatus = "0";
            }


        }
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("Gmail",""+account.toString());
            } catch (ApiException e) {
                e.printStackTrace();
            }
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
