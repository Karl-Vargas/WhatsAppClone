package karl.vargas.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import karl.vargas.whatsappclone.Models.Users;
import karl.vargas.whatsappclone.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

//    Accessing the xml
    ActivitySignUpBinding binding;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;

//    Progressbar
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


//        Hiding the bar above the application
        getSupportActionBar().hide();

        // Adding text to the progressbar and connecting the activity to this
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating account...");
        progressDialog.setMessage("We're creating your account.");

        // Creating a setOnClickListener on Sign up button
        // if the user clicked on signup button without entering info, it wil toast a message
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.txtUsername.getText().toString().isEmpty() && !binding.txtEmail.getText().toString().isEmpty() && !binding.txtPassword.getText().toString().isEmpty())
                {
                    // signup operation
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString(),
                            binding.txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                          Once the signup is success the progressbar will disappear
                            progressDialog.dismiss();

                            if(task.isSuccessful()) {
                                // Users Objects
                                Users user = new Users(binding.txtUsername.getText().toString(),
                                        binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString());

//                              Extracting Id number of username from fb
                                String id = task.getResult().getUser().getUid();

                                // Whatever info you pass it will stored to realtimedb
                                database.getReference().child("Users").child(id).setValue(user);


//                              Toast Signup Success
                                Toast.makeText(SignUpActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        } 
                    });

                } else {
                    Toast.makeText(SignUpActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Binding and navigate from signup fragment to signin fragment
        binding.txtAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}