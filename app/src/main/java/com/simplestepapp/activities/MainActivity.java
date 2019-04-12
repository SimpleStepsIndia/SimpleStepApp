package com.simplestepapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.os.StrictMode;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.tasks.Task;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.simplestepapp.BuildConfig;
import com.simplestepapp.R;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.ConnectivityUtils;
import com.simplestepapp.utils.SessionManager;
import com.simplestepapp.utils.Toaster;
import com.simplestepapp.utils.ValidationUtils;
import com.sms.sma.UnityPlayerActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import static android.provider.Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private AppCompatButton btn_SignUp;

    private AppCompatButton btn_sign_in;

    public AppCompatEditText edtTxt_Name, edtTxt_EmailId, edtTxt_Pwd, edt_Txt_DOB;

    public double latitude = 0.0, longitude = 0.0;

    public static ArrayList<String> timeSlots = new ArrayList<>();

    String   str_UserName="", str_Email;

    private static final int RC_SIGN_IN = 7;

    private GoogleApiClient mGoogleApiClient;

    ProgressDialog progressDialog;

    SessionManager session;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        session = new SessionManager(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
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
                .requestIdToken(AppConfig.sClientId)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (edtTxt_EmailId.getText().toString().isEmpty() ||
                        !ValidationUtils.isValidEmaillId(edtTxt_EmailId.getText().toString().trim())) {
                    edtTxt_EmailId.setError("Please Enter valid Email !");
                    edtTxt_EmailId.requestFocus();
                } else if (edtTxt_Pwd.getText().toString().trim().isEmpty()) {
                    edtTxt_Pwd.setError("Please Enter the Password !");
                } else {

                    str_UserName = edtTxt_Name.getText().toString().trim();
                    str_Email = edtTxt_EmailId.getText().toString().trim();

                    /*Intent intent=new Intent(getApplicationContext(), UnityActivity.class);
                    startActivity(intent);*/
                    user_Registration(str_UserName, str_Email, edtTxt_Pwd.getText().toString().trim());
                }
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, 7);
            }
        });


    }

    private void initviews() {

        edtTxt_Name = findViewById(R.id.edtTxt_Name);
        edtTxt_EmailId = findViewById(R.id.edtTxt_EmailId);
        edtTxt_Pwd = findViewById(R.id.edtTxt_Pwd);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        btn_sign_in=findViewById(R.id.btn_sign_in);
    }

    public void user_Registration(final String userName, final String eMailId, final String pwd) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("User Registering ...");
        progressDialog.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("firstName", userName);
        params.put("emailId", eMailId);
        params.put("password", pwd);
        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, AppConfig.user_REGISTER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (null != response) {

                        Log.d("Login Res: ", "" + response.toString());
                        String status = response.getString("Message");
                        if ("User created".equals(status)) {
                            Toaster.showSuccessMessage("User Registered Successfully !");
                            JSONObject jsonObj_User = response.getJSONObject("User");
                            String userName = jsonObj_User.getString("firstName");
                            String eMail = jsonObj_User.getString("emailId");
                            String token = jsonObj_User.getString("token");
                            String userID = jsonObj_User.getString("userId");
                            session.createLoginSession(userName, eMail, token, userID);
                            Intent intent = new Intent(getApplicationContext(), QuestionerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Toaster.showWarningMessage("User Registration Failed !" + status);
                        }
                    } else {
                        Toaster.showErrorMessage("User Registration Failed !");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("error in response", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(req);
    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.d("GmailData", "" + result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = Objects.requireNonNull(acct).getDisplayName();
            String email = acct.getEmail();
            Log.d("Info", "Name: " + personName + ", email: " + email);
            Intent intent_Pager = new Intent(getApplicationContext(), QuestionerActivity.class);
            startActivity(intent_Pager);
        } else {
            Intent intent_Pager = new Intent(getApplicationContext(), QuestionerActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
       /* OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
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



        }
        if (requestCode == 7) {


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("Gmail", "" + account.toString());
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
