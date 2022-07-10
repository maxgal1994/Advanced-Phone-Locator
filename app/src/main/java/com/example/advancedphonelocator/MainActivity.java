package com.example.advancedphonelocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText emailET,passwordET;
    Button loginButton,signinButton;

    //Array of Users
    ArrayList<User> users = new ArrayList<>();

    // Connect to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://advanced-phone-locator-default-rtdb.firebaseio.com/");
    DatabaseReference myRef = database.getReference();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginButton = findViewById(R.id.loginButton);
        signinButton = findViewById(R.id.signinButton);

        Query q = myRef.child("Users");

        //singup and create users on firebase
        signinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String username = emailET.getText().toString();
                String password = passwordET.getText().toString();
                User u = new User(username, password);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean exists = false;

                        for (DataSnapshot dst : dataSnapshot.getChildren()) {

                            User u1 = dst.getValue(User.class);

                            if (u.email.equals(u1.email)) {
                                exists = true;
                            }
                        }
                        //end of loop

                        if (exists==true) {
                            Toast.makeText(MainActivity.this, "user with this name already exists", Toast.LENGTH_SHORT).show();
                        }
                        else if(u.email.equals("") || u.password.equals("") || u.email.equals("Enter Email") || u.password.equals("Enter Password")){
                            Toast.makeText(MainActivity.this, "Failed! Text field is empty!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            myRef.child("Users").push().setValue(u);
                            Toast.makeText(MainActivity.this, "Registered Sucsessfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
        });

        //user clicks login
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String username = emailET.getText().toString();
                String password = passwordET.getText().toString();
                User u = new User(username, password);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean exists = false;
                        for (DataSnapshot dst : dataSnapshot.getChildren()) {
                            User u1 = dst.getValue(User.class);
                            if (u.email.equals(u1.email) && u.password.equals(u1.password)) {
                                exists = true;
                            }
                        }
                        //end of loop
                        if (exists==true) {
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("user", u.email);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "LOGGED SUCSSESFULLY!", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "LOGIN FAILED!", Toast.LENGTH_LONG).show();

                    }

                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
        });

    }
}