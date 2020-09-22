package com.bxing.bordercrossing.tjhobbies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Function;

//Imagenes
//https://img.fenixzone.net/es/


public class MainActivity extends AppCompatActivity {

    public Button btnrestaurant, btncines, btncultura, btnfamiliar;
    public Context context;
    public ArrayList<DataModel> ArraySitios = new ArrayList<DataModel>();
    public ArrayList<DataModel> sitio;
    public ArrayList<String> StrSitios; // = new ArrayList();
    public ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    public ArrayList<Double> SitiosCercanos; // = new ArrayList();

    public String mylat= "32.507311";
    public String mylong = "-116.981621";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar1.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);
        progressBar3.setVisibility(View.INVISIBLE);
        progressBar4.setVisibility(View.INVISIBLE);

        btnrestaurant = (Button) findViewById(R.id.btnRestaurant);
        btncines = (Button) findViewById(R.id.btnCines);
        btncultura = (Button) findViewById(R.id.btnCultura);
        btnfamiliar = (Button) findViewById(R.id.btnDiversion) ;


        //Guardar session de usuario.
        context = this;
        //SharedPreferences sharedPref = getSharedPreferences("archivoDatos", context.MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        showExitAlertDialogue();
    }

    //create aleart dialogue to confirm exit of user
    public void showExitAlertDialogue(){
        AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
                .setTitle("Confirmar")
                .setMessage("Minimiza la aplicacion")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(true)
                .create();
        dialog.show();
    }

    /*
     * Metodo Onclick para botones del menu main.
     * */
    public void onClick(View view)
    {
        StrSitios = new ArrayList();

        switch(view.getId()) {
            case R.id.btnRestaurant:
                progressBar1.setVisibility(View.VISIBLE);
                //Restaurantes
                GlobalValues.setCategorySelected("1");
                GlobalValues.setModoSel("1");
                getItemsSelectedPhp();
                DisplayListaItems();
                break;

            case R.id.btnCines:
                progressBar2.setVisibility(View.VISIBLE);
                //Cines
                GlobalValues.setCategorySelected("2");
                GlobalValues.setModoSel("1");
                getItemsSelectedPhp();
                DisplayListaItems();
                break;

            case R.id.btnCultura:
                progressBar3.setVisibility(View.VISIBLE);
                //Sitios de Cultura
                GlobalValues.setCategorySelected("3");
                GlobalValues.setModoSel("1");
                getItemsSelectedPhp();
                DisplayListaItems();
                break;

            case R.id.btnDiversion:
                progressBar4.setVisibility(View.VISIBLE);
                //Sitios de Cultura
                GlobalValues.setCategorySelected("4");
                GlobalValues.setModoSel("1");
                getItemsSelectedPhp();
                DisplayListaItems();
                break;

            case R.id.btnTodos:
                //progressBar3.setVisibility(View.VISIBLE);
                //Todos los sitios.
                GlobalValues.setCategorySelected("1000");
                GlobalValues.setModoSel("2");
                getItemsAllPhp();
                DisplayListaItems();
                break;

            case R.id.btnCloser:
                //progressBar3.setVisibility(View.VISIBLE);
                //Mas cercanos a mi ubicacion geografica.
                GlobalValues.setCategorySelected("1001");
                GlobalValues.setModoSel("3");
                getItemsAllPhp();
                DisplayListaItems();
                break;
        }
    }


    //PARA LLAMAER PHP 1.
    public void getItemsAllPhp()
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
                final String res = getItemsAll();
                Log.d("ITEMSALL JSON","*** ALL: " + res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = objJSON(res);
                        if (r > 0) {
                            GetJSonItemsAll(res);  //Llena StrSitios array.
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "No items found.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }


    /*
   Get Json Data from JsonObjects getItemsAll.
    */
    public void GetJSonItemsAll(String resp){

        try {
            StrSitios = new ArrayList(); //Para guardar los sitios.
            JSONArray sitios = new JSONArray(resp);

            // looping through All records.
            for(int i = 0; i < sitios.length(); i++){
                JSONObject s = sitios.getJSONObject(i);
                String datos="";

                // Storing each json item in variable
                String clave = s.getString("clave");
                String nombre = s.getString("nombre");
                String direccion = s.getString("direccion");
                String telefono = s.getString("telefono");
                String email = s.getString("email");
                String url = s.getString("url");
                String latitud = s.getString("latitud");
                String longitud = s.getString("longitud");
                String socialnet = s.getString("socialnet");
                String categoria = s.getString("categoria");

                String distancia  = String.format ("%.2f",(getDistancias(mylat, mylong, latitud, longitud)/1000));

                Log.d("JSONDATA","distancia/1000kms = " + distancia);

                datos = clave + ","+nombre+","+direccion+","+telefono+","+ email+"," + url+","
                        +latitud+","+longitud+","+socialnet+","+categoria+","+ distancia;

                Log.d("JSONDATA","datos = " + datos);

                StrSitios.add(datos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            //Error al guardar los sitios en array.
        }
    }

    //LLAMAR EL SERVICIO 2.
    public String getItemsAll(){
        String parametros = "";
        HttpURLConnection conn = null;
        String respuesta = "";

        try{
            URL url = new URL("https://flagrant-fiber.000webhostapp.com/getItemsAll.php");
            Log.d("URL URL","URL= " + url);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", ""+Integer.toString(parametros.getBytes().length));
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();
            Scanner inStream = new Scanner(conn.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }

            Log.d("ITEMSALL","ITEMS : " + respuesta);

        }catch (IOException e){
            e.printStackTrace();
            Log.d("WORKING","No se pudo conectar a la base de datos: " + conn);
        }
        return respuesta.toString();
    }

    /*
    * Despliega los items devueltos por el servicio web. 3.
    * */
    public void DisplayListaItems(){
        //Llenar el listview.
        Thread sitiosThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Espera...
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1.setVisibility(View.INVISIBLE);
                            progressBar2.setVisibility(View.INVISIBLE);
                            progressBar3.setVisibility(View.INVISIBLE);
                            progressBar4.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(context, ListaItems.class);
                            intent.putStringArrayListExtra("listaSitios", StrSitios);
                            startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {//Lanza procesamiento de actividad.
                    Log.d("finally ", "displaylista items");
                }
            }
        };
        sitiosThread.start();
    }


    //PARA LLAMAER PHP 1.
    public void getItemsSelectedPhp()
    {
        Thread thread = new Thread() {
            @Override
            public void run() {
                final String res = enviarCatPhp(GlobalValues.getcategorySelected());
                Log.d("objJSON","objJSON to split: " + res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = objJSON(res);
                        if (r > 0) {
                            GetJSonData(res);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "No items found.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    /*
    Get Json Data from JsonObjects.
     */
    public void GetJSonData(String resp){

        try {
            StrSitios = new ArrayList(); //Para guardar los sitios.
            JSONArray sitios = new JSONArray(resp);

            // looping through All records.
            for(int i = 0; i < sitios.length(); i++){
                JSONObject s = sitios.getJSONObject(i);
                String datos="";

                // Storing each json item in variable
                String clave = s.getString("clave");
                String nombre = s.getString("nombre");
                String direccion = s.getString("direccion");
                String telefono = s.getString("telefono");
                String email = s.getString("email");
                String url = s.getString("url");
                String latitud = s.getString("latitud");
                String longitud = s.getString("longitud");
                String socialnet = s.getString("socialnet");
                String categoria = s.getString("categoria");

                Log.d("GETDISTANCIA","nombre: "+ nombre);
                String distancia  = String.format ("%.2f",(getDistancias(mylat, mylong, latitud, longitud)/1000));

                //Log.d("DISTANCIA EN MTS","METROSS : " + distancia);
                Log.d("DISTANCIA","ITEM: "+ nombre+" distancia/1000kms = " + distancia);

                datos = clave + ","+nombre+","+direccion+","+telefono+","+ email+"," + url+","
                        +latitud+","+longitud+","+socialnet+","+categoria+","+ distancia;

                Log.d("JSONDATA","datos = " + datos);

                StrSitios.add(datos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            //Error al guardar los sitios en array.
        }
    }

    //LLAMAR EL SERVICIO. 2.
    public String enviarCatPhp(String categoria){
        String parametros = "categoria="+categoria+"&categoria1="+categoria;
        HttpURLConnection conn = null;
        String respuesta = "";

        try{
            //URL url = new URL("http://androidserver.webcindario.com/login.php");
            URL url = new URL("https://flagrant-fiber.000webhostapp.com/getItems.php");
            Log.d("URL URL","URL= " + url);
            Log.d("CATEGORIA","params...: " + parametros);

            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", ""+Integer.toString(parametros.getBytes().length));
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();
            Scanner inStream = new Scanner(conn.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }

        }catch (IOException e){
            e.printStackTrace();
            Log.d("WORKING","No se pudo conectar a la base de datos: " + conn);
        }
        return respuesta.toString();
    }

    public int objJSON (String respJson){
        int resj = 0;
        try{
            JSONArray json = new JSONArray(respJson);
            if (json.length()>0){
                resj=1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resj;
    }


    /*
    Despliega datos recuperados del servicio Web.
    No quita las comillas a Strings.
    */
    public void DisplaySitiosJson(String res){
        String  dominios[];
        String campos[];

        res = res.replaceAll("\\[", "");
        res = res.replaceAll("]", "");
        res = res.replaceAll("\\}", "");
        String llave [] = res.split("\\{");

        //Guardar datos en array.
        for (int j =1; j< llave.length; j++){
            String value[];
            String sitio="";

            campos = llave[j].split(",");

            for (int i = 0; i < campos.length ; i++) {
                dominios = campos[i].split(":");
                sitio += dominios[1] + ",";
            }
            //StrSitios.add(sitio); Agrega al array.

        }//temina for Sitios
    }

    /*
    Metodo OnClick de Layouts en Main.
    Misma rutina de botones en main.
    */
    public void OnClick(View view) {
        StrSitios = new ArrayList();

        switch(view.getId()) {
            case R.id.lay_rest:
                progressBar1.setVisibility(View.VISIBLE);
                //Restaurantes
                GlobalValues.setCategorySelected("1");
                getItemsSelectedPhp();
                DisplayListaItems();
                break;

            case R.id.lay_cines:
                progressBar2.setVisibility(View.VISIBLE);
                //Cines
                GlobalValues.setCategorySelected("2");
                getItemsSelectedPhp();
                DisplayListaItems();
                break;

            case R.id.lay_cultura:
                progressBar3.setVisibility(View.VISIBLE);
                //Sitios de Cultura
                GlobalValues.setCategorySelected("3");
                getItemsSelectedPhp();
                DisplayListaItems();
                break;

            case R.id.lay_diversion:
                progressBar4.setVisibility(View.VISIBLE);
                //Sitios de Diversion
                GlobalValues.setCategorySelected("4");
                getItemsSelectedPhp();
                DisplayListaItems();
                break;
        }
    }


    /////////  GOOGLE LOCATION /////////

    /*
     * Funcion para obtenerla distancia en metros entre dos puntos.
     * */
    public Double getDistancias (String latA, String lonA, String latB, String lonB){

        Log.d("DISTANCIA","lat-longA : " + latA+ "***"+lonA);
        Log.d("DISTANCIA","lat-longB : " + latB+ "***"+lonB);

        Location locationA = new Location("punto A");

        locationA.setLatitude(Double.parseDouble(latA.trim()));
        locationA.setLongitude(Double.parseDouble(lonA.trim()));

        Location locationB = new Location("punto B");

        locationB.setLatitude(Double.parseDouble(latB.trim()));

        locationB.setLongitude(Double.parseDouble(lonB.trim())); /////////////

        double distance = locationA.distanceTo(locationB);

        Log.d("DISTANCIA EN MTS","METROSS : " + distance);

        return distance;
    }


}
