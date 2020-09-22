package com.bxing.bordercrossing.tjhobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ItemDescription extends AppCompatActivity {

    public String clave, nombre, direccion, telefono, distancia, latitud, longitud;
    public String url, socialnet, email;
    public TextView txtNom, txtDir, txtTel, txtID, txtDist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

       clave =  getIntent().getStringExtra("clave");
       nombre =  getIntent().getStringExtra("nombre");
       direccion =  getIntent().getStringExtra("direccion");
       telefono =  getIntent().getStringExtra("telefono");
       distancia =  getIntent().getStringExtra("distancia");
       latitud=  getIntent().getStringExtra("latitud");
       longitud =  getIntent().getStringExtra("longitud");
       socialnet =  getIntent().getStringExtra("socialnet");
       email =  getIntent().getStringExtra("email");
       url =  getIntent().getStringExtra("url");

       txtNom = (TextView) findViewById(R.id.txtnombre);
       txtDir = (TextView) findViewById(R.id.txtdireccion);
       txtTel = (TextView) findViewById(R.id.txttelefono);
       txtDist = (TextView) findViewById(R.id.txtdistancia);

       txtNom.setText(nombre);
       txtDir.setText(direccion);
       txtTel.setText(telefono);
       txtDist.setText(distancia);

    }

    public void OnClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("nombre",nombre);
        intent.putExtra("latitud",latitud);
        intent.putExtra("longitud", longitud);
        startActivity(intent);
    }

}
