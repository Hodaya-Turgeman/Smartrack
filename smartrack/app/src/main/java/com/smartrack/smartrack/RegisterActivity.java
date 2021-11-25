package com.smartrack.smartrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.smartrack.smartrack.ui.newUserDetailsFragment;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout InputsName, InputMail, InputPassword, InputVerifyPassword;
    TextView haveAccount;
    ProgressDialog myLoadingDialog;
    Button signUpBtn;

    App app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InputsName =findViewById(R.id.activity_register_input_userName);
        InputMail =findViewById(R.id.activity_register_input_mail);
        InputPassword =findViewById(R.id.activity_register_input_password);
        InputVerifyPassword =findViewById(R.id.activity_register_input_verify_password);
        signUpBtn=findViewById(R.id.activity_register_btn_signup);

        Realm.init(this); // context, usually an Activity or Application
        app=new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());

        myLoadingDialog=new ProgressDialog(this);
        haveAccount=findViewById(R.id.activity_register_Have_Account);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUpToApp();
            }
        });

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogIn();
            }
        });
    }

    private void goToLogIn() {
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void singUpToApp() {
        String mail= InputMail.getEditText().getText().toString();
        String password= InputPassword.getEditText().getText().toString();
        String verifyPassword= InputVerifyPassword.getEditText().getText().toString();
        String username= InputsName.getEditText().getText().toString();

        if(mail.isEmpty() || !mail.contains("@")){
            showError(InputMail,"Email Address is not Valid");
        }
        else if(password.isEmpty() || password.length()<6){
            showError(InputPassword,"Password must be longer than 6 characters");
        }
        else if(!verifyPassword.equals(password)){
            showError(InputVerifyPassword,"Password does not equal");
        }
        else if(username.isEmpty() || username.length()<3)  {
            showError(InputsName,"UserName is not valid");
        }
        else{
            createAccountInMongoDb(mail,password,username);
        }

    }


    private void createAccountInMongoDb(String mail,String password,String username){
        myLoadingDialog.setTitle("Registration");
        myLoadingDialog.setMessage("Please Wait, Creating Your Account");
        myLoadingDialog.setCanceledOnTouchOutside(false);
        myLoadingDialog.show();
        Credentials credential = Credentials.emailPassword(mail,password);
        app.loginAsync(credential,new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()) {
                    myLoadingDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Account is already exists", Toast.LENGTH_LONG).show();
                } else {
                    app.getEmailPassword().registerUserAsync(mail, password, it -> {
                        if (it.isSuccess()) {
                            myLoadingDialog.dismiss();
<<<<<<< HEAD
                            Toast.makeText(RegisterActivity.this,"Account Creation Success",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else{
=======
                            Toast.makeText(RegisterActivity.this, "Account Creation Success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
>>>>>>> 9023430b09def941a7b48b20db566dc9b12bc8df
                            myLoadingDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Account Creation Failed", Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });
    }
    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
}