package org.fhi360.ddd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.User;

import java.util.Objects;

public class CreateOutLetAccount extends AppCompatActivity {

    private Button button;
    private EditText username, password, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddd_outlet);
        button = findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1 = username.getText().toString();
                String password1 = password.getText().toString();
                String name1 = name.getText().toString();
                if (validateInput(name1, username1, password1)) {
                    User user = DDDDb.getInstance(CreateOutLetAccount.this).userRepository().findByUsernameAndPassword(username1, password1);
                    if (user != null) {
                        FancyToast.makeText(getApplicationContext(), "User with these credentials already registered", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        User user1 = new User();
                        user1.setPassword(password1);
                        user1.setUsername(username1);
                        user1.setName(name1);
                        save(name1);
                        user1.setRole("DDD outlet");
                        DDDDb.getInstance(CreateOutLetAccount.this).userRepository().save(user1);
                        Intent intent = new Intent(CreateOutLetAccount.this, OutletWelcome.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean validateInput(String username1, String password1, String name1) {
        if (name1.isEmpty()) {
            name.setError("Phone can not be empty");
            return false;
        } else if (username1.isEmpty()) {
            username.setError("username can not be empty");
            return false;
        } else if (password1.isEmpty()) {
            password.setError("password can not be empty");
            return false;

        }
        return true;

    }

    public void save(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("name", name);
        editor.apply();
    }
}
