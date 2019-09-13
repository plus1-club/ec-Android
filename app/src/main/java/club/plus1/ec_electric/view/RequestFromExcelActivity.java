package club.plus1.ec_electric.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import club.plus1.ec_electric.R;

public class RequestFromExcelActivity extends AppCompatActivity {

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), RequestTableActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_from_excel);

        Button button = findViewById(R.id.buttonNext);
        button.setOnClickListener(clickListener);
    }
}
