package com.simplestepapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;
import com.simplestepapp.R;
import com.simplestepapp.adapters.AgeAdapter;
import com.simplestepapp.utils.SessionManager;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener {

    private static final String TAG = "ProifleActivity";
    private static final String URL_FOR_REGISTRATION = "https://XXX.XXX.X.XX/android_login_example/register.php";
    ProgressDialog progressDialog;

    private EditText signupInputName, signupInputEmail, signupInputPassword, signupInputAge;
    private Button btnSignUp;
    private Button btnLinkLogin;
    public RadioGroup rBtnGrp_Surgens;
    public RadioButton rBtn_SYes, rBtn_SNo;

    DiscreteScrollView age_picker;
    ArrayList<String> age_Lst;
    AgeAdapter ageAdapter;
    SessionManager sessionManager;

    LinearLayout lyt_SurYes;

    RulerValuePicker height_picker, weight_picker;

    AppCompatTextView txt_Name, txt_HtPicker, txt_WtPicker;

    String userName = "", eMailId = "", token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        initviews();
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            userName = user.get(SessionManager.KEY_NAME);
            eMailId = user.get(SessionManager.KEY_EMAIL);
            token = user.get(SessionManager.KEY_TOKEN);
            txt_Name.setText(userName);
        }
        for (int i = 10; i <= 60; i++) {
            age_Lst.add(String.valueOf(i));
        }

        age_picker.setSlideOnFling(true);
        age_picker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        ageAdapter = new AgeAdapter(age_Lst);
        age_picker.setAdapter(ageAdapter);

        age_picker.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
//                Log.d("adapterPosition", "" + adapterPosition);
                ageAdapter.setSelectedIndex(adapterPosition);
                Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        ageAdapter.notifyDataSetChanged();
                    }
                };
                handler.post(r);
            }
        });

        txt_HtPicker.setText("" + 100 + " cms");
        height_picker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                txt_HtPicker.setText("" + selectedValue + " cms");
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                txt_HtPicker.setText("" + selectedValue + " cms");
            }
        });

        txt_WtPicker.setText("" + 30 + " kgs");
        weight_picker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                txt_WtPicker.setText("" + selectedValue + " kgs");
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                txt_WtPicker.setText("" + selectedValue + " kgs");
            }
        });

        rBtnGrp_Surgens.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rBtn_SYes:
                        lyt_SurYes.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rBtn_SNo:
                        lyt_SurYes.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void initviews() {
        txt_Name = findViewById(R.id.txt_Name);
        age_picker = findViewById(R.id.age_picker);
        height_picker = findViewById(R.id.height_picker);
        weight_picker = findViewById(R.id.weight_picker);
        txt_HtPicker = findViewById(R.id.txt_HtPicker);
        txt_WtPicker = findViewById(R.id.txt_WtPicker);
        lyt_SurYes = findViewById(R.id.lyt_SurYes);
        rBtnGrp_Surgens = findViewById(R.id.rBtnGrp_Surgens);
        rBtn_SYes = findViewById(R.id.rBtn_SYes);
        rBtn_SNo = findViewById(R.id.rBtn_SNo);
        age_Lst = new ArrayList<>();
    }

    private void submitForm() {

        String gender;
/*
        if(selectedId == R.id.female_radio_btn)
            gender = "Female";
*/
        //  else
        gender = "Male";

        registerUser(signupInputName.getText().toString(),
                signupInputEmail.getText().toString(),
                signupInputPassword.getText().toString(),
                gender,
                signupInputAge.getText().toString());
    }

    /*
    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }
     */


    private void registerUser(final String name, final String email, final String password,
                              final String gender, final String dob) {
        // Tag used to cancel the request
        String cancel_req_tag = "register";

        progressDialog.setMessage("Adding you ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_REGISTRATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String user = jObj.getJSONObject("user").getString("name");
                        Toast.makeText(getApplicationContext(), "Hi " + user + ", You are successfully Added!", Toast.LENGTH_SHORT).show();

                        // Launch login activity
                        /*Intent intent = new Intent(
                                RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);*/
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("gender", gender);
                params.put("age", dob);
                return params;
            }
        };
        // Adding request to request queue

    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {

    }
}