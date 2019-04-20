package com.simplestepapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.simplestepapp.R;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.SessionManager;
import com.simplestepapp.utils.Toaster;
import com.simplestepapp.utils.ValidationUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private AppCompatButton btn_SignIn;

    public AppCompatEditText edtTxt_EmailId, edtTxt_Pwd;

    String str_UserName = "", str_Email;

    AppCompatTextView txt_SignUp;

    ProgressDialog progressDialog;

    SessionManager session;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initviews();
        progressDialog = new ProgressDialog(this);
        session = new SessionManager(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Objects.requireNonNull(edtTxt_EmailId.getText()).toString().isEmpty() ||
                        !ValidationUtils.isValidEmaillId(edtTxt_EmailId.getText().toString().trim())) {
                    edtTxt_EmailId.setError("Please Enter valid Email !");
                    edtTxt_EmailId.requestFocus();
                } else if (Objects.requireNonNull(edtTxt_Pwd.getText()).toString().trim().isEmpty()) {
                    edtTxt_Pwd.setError("Please Enter the Password !");
                } else {
                    str_Email = edtTxt_EmailId.getText().toString().trim();
                    user_Login(str_Email, edtTxt_Pwd.getText().toString().trim());
                }
            }
        });

        txt_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initviews() {
        edtTxt_EmailId = findViewById(R.id.edtTxt_EmailId);
        edtTxt_Pwd = findViewById(R.id.edtTxt_Pwd);
        btn_SignIn = findViewById(R.id.btn_SignIn);
        txt_SignUp = findViewById(R.id.txt_SignUp);

    }

    public void user_Login(final String eMailId, final String pwd) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("User Loging  ...");
        progressDialog.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("emailId", eMailId);
        params.put("password", pwd);
        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, AppConfig.user_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    if (null != response) {

                        Log.d("Login Res: ", "" + response.toString());
                        String status = response.getString("Message");
                        if ("User login successful".equals(status)) {
                            Toaster.showSuccessMessage("User Login Successfully !");
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
                            Toaster.showWarningMessage("UserName or Password are Incorrect !" + status);
                        }
                    } else {
                        Toaster.showErrorMessage("User Login Failed !");
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


}
