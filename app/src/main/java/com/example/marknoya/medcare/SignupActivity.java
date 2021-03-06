package com.example.marknoya.medcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private Database db;
    private Session session;
    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    Button _signupButton;
    TextView _loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new Database(this);
        session = new Session(this);

        _nameText = (EditText)findViewById(R.id.input_name);
        _emailText = (EditText)findViewById(R.id.input_email);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _signupButton = (Button)findViewById(R.id.btn_signup);
        _loginLink = (TextView)findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();




        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if(db.checkUser(email)){
                            onSignupFailed();

                        }else {
                            db.addUser(name,email,password);
                            onSignupSuccess();
                        }
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        session.setLoggin(true,db.getId(_emailText.getText().toString()));
        Toast.makeText(getBaseContext(),db.getId(_emailText.getText().toString())+"  Signup 92", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(),"Email is already used.", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public boolean validate(){
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if(name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 Character");
            valid = false;

        } else {
            _nameText.setError(null);
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailText.setError("enter a valid email address");
            valid = false;

        }else {
            _emailText.setError(null);
        }

        if(password.isEmpty() || password.length() < 4 || password.length() >10){
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;

    }

}
