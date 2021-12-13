package com.smartrack.smartrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;


public class LoginActivity extends AppCompatActivity {

    private TextInputLayout InputMail, InputPassword;
    Button loginBtn;
    TextView forgotPassword, createAccount;
    ProgressDialog myLoadingDialog;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");
        InputMail=findViewById(R.id.activity_login_input_mail);
        InputPassword=findViewById(R.id.activity_login_input_password);
        loginBtn=findViewById(R.id.activity_login_btn_signin);
        forgotPassword=findViewById(R.id.activity_login_forget_password);
        createAccount=findViewById(R.id.activity_login_create_new_account);
        myLoadingDialog=new ProgressDialog(this);

        Realm.init(this); // context, usually an Activity or Application
        app=new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentLinkToEmailResPassword();
            }
        });
    }
    private void sentLinkToEmailResPassword() {
        Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
        startActivity(intent);
    }
    private void goToSignUp(){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    private void UserLogin(){
        String mail= InputMail.getEditText().getText().toString();
        String password= InputPassword.getEditText().getText().toString();

        if(mail.isEmpty() || !mail.contains("@")){
            showError(InputMail,"Email Address is not Valid");
        }
        else if(password.isEmpty() || password.length()<6){
            showError(InputPassword,"Password must be longer than 6 characters");
        }
        else{
            myLoadingDialog.setTitle("Login");
            myLoadingDialog.setMessage("Please Wait,Log-in To Your Account");
            myLoadingDialog.setCanceledOnTouchOutside(false);
            myLoadingDialog.show();
            Credentials credential = Credentials.emailPassword(mail,password);
        app.loginAsync(credential,new App.Callback<User>(){
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    myLoadingDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Log-in Succeeded",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    myLoadingDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Log-in Failed",Toast.LENGTH_LONG).show();
                }
            }

        });
        }
    }
    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
}