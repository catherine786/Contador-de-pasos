package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onCardViewClick(View view) {
        Intent intent;

        if (view.getId() == R.id.cardViewRutinaLibre) {
            intent = new Intent(this, RutinaLibreActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.cardViewNuevoReto) {
            intent = new Intent(this, NuevoRetoActivity.class);
            startActivity(intent);
        }
    }
}
