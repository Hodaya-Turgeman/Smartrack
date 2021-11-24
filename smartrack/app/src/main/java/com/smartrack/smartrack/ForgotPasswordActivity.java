package com.smartrack.smartrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText inputMail;
    Button btnSave;
    App app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        inputMail=findViewById(R.id.activity_forgetPassword_inputMail);
        btnSave=findViewById(R.id.activity_forgetPassword_btn_send);
        Realm.init(this); // context, usually an Activity or Application
        app=new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLinkToMail();
            }
        });
    }

    private void sendLinkToMail() {
        String mail=inputMail.getText().toString();
        if(mail.isEmpty()){
            Toast.makeText(ForgotPasswordActivity.this,"Please Enter Your Email", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else{
            String newPassword = "newFakePassword";
            String[] args = {"security answer 1", "security answer 2"};

            app.getEmailPassword().callResetPasswordFunctionAsync(mail, newPassword, args, it -> {
                if (it.isSuccess()) {
                    Log.i("EXAMPLE", "Successfully reset the password for" + mail);
                } else {
                    Log.e("EXAMPLE", "Failed to reset the password for" + mail + ": " + it.getError().getErrorMessage());
                }
            });

//            app.getEmailPassword().sendResetPasswordEmailAsync(mail, new App.Callback<Void>() {
//                @Override
//                public void onResult(App.Result<Void> result) {
//                    if(result.isSuccess()){
//                        Toast.makeText(ForgotPasswordActivity.this,"Please Check Your Email", Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else{
//                        Toast.makeText(ForgotPasswordActivity.this,"Email was not Send! ", Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            }) ;

        }

    }
}