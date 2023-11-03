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

        if (view.getId() == R.id.cardViewRutinaLibre) {
            // Abre la actividad de "Rutina Libre"
            intent = new Intent(this, RutinaLibreActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.cardViewNuevoReto) {
            // Abre la actividad de "Nuevo Reto"
            //intent = new Intent(this, NuevoRetoActivity.class);
            //startActivity(intent);
        }
    }
}
