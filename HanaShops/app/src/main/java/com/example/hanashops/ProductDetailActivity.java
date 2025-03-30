package com.example.hanashops;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String productType = getIntent().getStringExtra("productType");

        if (productType != null) {
            switch (productType) {
                case "Tazas":
                    setContentView(R.layout.activity_tazas);
                    break;
                case "Mousepad":
                    setContentView(R.layout.activity_mousepad);
                    break;
                case "Termos":
                    setContentView(R.layout.activity_termos);
                    break;
                case "Gorras":
                    setContentView(R.layout.activity_gorras);
                    break;
                case "Camisas":
                    setContentView(R.layout.activity_camisas);
                    break;
                case "Sudaderas":
                    setContentView(R.layout.activity_sudaderas);
                    break;
                default:
                    finish();
                    return;
            }
        } else {
            finish();
            return;
        }

        // Configurar el botón de envío de email
        Button btnSendEmail = findViewById(R.id.btn_sendEmail);
        btnSendEmail.setOnClickListener(v -> sendEmail());
    }

    private void sendEmail() {
        // Obtener referencias a los campos de entrada
        /* EditText imageLinkEditText = findViewById(R.id.ligaImagen);
        EditText phoneEditText = findViewById(R.id.celular);
        Spinner cupTypeSpinner = findViewById(R.id.tipoTazaSpinner);
        Spinner imageSizeSpinner = findViewById(R.id.tamanoImagenSpinner);

        // Obtener los valores ingresados
        String imageLink = imageLinkEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String cupType = cupTypeSpinner.getSelectedItem().toString();
        String imageSize = imageSizeSpinner.getSelectedItem().toString();

        // Validar que los campos obligatorios no estén vacíos
        if (imageLink.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields before sending the email.", Toast.LENGTH_SHORT).show();
            return;
        }
        */
        // Crear el mensaje de correo
        String subject = "Solicitud de presupuesto";
        String body = "Saludos,\n\nMe gustaria cotizar el siguiente producto:\n\n" +
                "Cup Type: " + "cupType" + "\n" +
                "Image Size: " + "imageSize" + "\n" +
                "Image Link: " + "imageLink" + "\n" +
                "Contact Number: " + "phone" + "\n\n" +
                "Quedo al pendiente de tu respuesta!";

        // Construir la URI de correo con subject y body
        String uriText = "mailto:felixsanchezoswaldo@gmail.com" +
                "?subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(body);

        // Crear Intent
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(uriText)); // Usar la URI generada

        // Iniciar la actividad de correo
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            Toast.makeText(this, "No email app found.", Toast.LENGTH_SHORT).show();
        }
    }

}
