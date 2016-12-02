package me.prasheelsoni.bookmyspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.prasheelsoni.bookmyspace.pojo.LoginRegisterPojo;
import me.prasheelsoni.bookmyspace.utils.SharedPrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

//    // UI references.
//    SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
//    private AutoCompleteTextView mEmailView;
//    private EditText mPasswordView;
//    Button mEmailSignInButton;
//    private View mProgressView;
//    private View mLoginFormView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        // Set up the login form.
//        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
//
//        mPasswordView = (EditText) findViewById(R.id.password);
//
//
//        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mProgressView.setVisibility(View.VISIBLE);
//                attemptLogin();
//            }
//        });
//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
//    }
//
//    public void attemptLogin(){
//
//        Retrofit adapter = new Retrofit.Builder()
//                .baseUrl("http://bms-ps11.rhcloud.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        NetworkCalls service = adapter.create(NetworkCalls.class);
//
//        Call<LoginRegisterPojo> response =  service.login(mEmailView.getText().toString(), mPasswordView.getText().toString());
//
//        response.enqueue(new Callback<LoginRegisterPojo>() {
//            @Override
//            public void onResponse(Call<LoginRegisterPojo> call, Response<LoginRegisterPojo> response) {
//                if(response.isSuccessful()){
//                    if(response.body().getCode().equals("1")){
//                    mProgressView.setVisibility(View.GONE);
//                        sharedPrefUtil.addString("loggedin","1");
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                    }else{
//                        mProgressView.setVisibility(View.GONE);
//                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginRegisterPojo> call, Throwable t) {
//                mProgressView.setVisibility(View.GONE);
//                Toast.makeText(LoginActivity.this, "Network Connection Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//
//
//    }
    SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    private TextInputEditText mEmailView, mPasswordView, mEmailSignUp, mPasswordSignup, mMobileSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup= (Button) findViewById(R.id.btnSignup);
        btnSignin= (Button) findViewById(R.id.btnSignin);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        mEmailView = (TextInputEditText)findViewById(R.id.login_email);
        mPasswordView = (TextInputEditText)findViewById(R.id.login_password);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSigninForm();
            }
        });
        showSigninForm();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             signUp();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }

    private void signIn(){
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("http://bms-ps11.rhcloud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetworkCalls service = adapter.create(NetworkCalls.class);

        Call<LoginRegisterPojo> response =  service.login(mEmailView.getText().toString(), mPasswordView.getText().toString());

        response.enqueue(new Callback<LoginRegisterPojo>() {
            @Override
            public void onResponse(Call<LoginRegisterPojo> call, Response<LoginRegisterPojo> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode().equals("1")){
                        sharedPrefUtil.addString("loggedin","1");
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }else{

                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginRegisterPojo> call, Throwable t) {

                Toast.makeText(LoginActivity.this, "Network Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUp(){}

}

