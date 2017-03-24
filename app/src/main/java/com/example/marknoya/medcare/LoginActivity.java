package com.example.marknoya.medcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private Database db;
    private Session session;


    EditText _Email;
    EditText _Password;
    Button _BtnLogin;
    TextView _SignupLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new Database(this);

        _Email = (EditText)findViewById(R.id.input_email);
        _Password = (EditText)findViewById(R.id.input_password);
        _BtnLogin = (Button)findViewById(R.id.btn_login);
        _SignupLink = (TextView)findViewById(R.id.link_signup);
        session = new Session(this);
        if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        _BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        _SignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }
    public void login(){
        Log.d(TAG,"Login");

        if(!validate()){
            onLoginFailed();
            return;
        }
        _BtnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _Email.getText().toString();
        final String pass  = _Password.getText().toString();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(db.getUser(email,pass)){
                            onLoginSuccess();
                        }
                        else{
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        session.setLoggin(true,db.getId(_Email.getText().toString()));
        _BtnLogin.setEnabled(true);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _BtnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _Email.getText().toString();
        String password = _Password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _Email.setError("enter a valid email address");
            valid = false;
        } else {
            _Email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _Password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _Password.setError(null);
        }
        return valid;
    }
}
