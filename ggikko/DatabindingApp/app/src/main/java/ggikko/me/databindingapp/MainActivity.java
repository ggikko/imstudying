package ggikko.me.databindingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ggikko.me.databindingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "good", Toast.LENGTH_LONG).show();
            }
        });

        ((TextView)findViewById(R.id.gettext)).setText(((EditText)findViewById(R.id.edittext)).getText());
        ((TextView)findViewById(R.id.tostring)).setText(((EditText)findViewById(R.id.edittext)).getText().toString());

    }
}
