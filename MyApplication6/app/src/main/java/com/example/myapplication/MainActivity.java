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

    // Función que se ejecuta cuando se hace clic en un CardView
    public void onCardViewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.cardViewRutinaLibre:
                // Aquí puedes abrir la actividad de "Rutina Libre"
                intent = new Intent(this, RutinaLibreActivity.class);
                startActivity(intent);
                break;
            case R.id.cardViewNuevoReto:
                // Aquí puedes abrir la actividad de "Nuevo Reto"
                intent = new Intent(this, NuevoRetoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
