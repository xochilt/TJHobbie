package com.bxing.bordercrossing.tjhobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ListaItems extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> dataItems = new ArrayList<DataModel>();
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    public  ArrayList<String> listaSitios;
    String fields[];
    public String categoria;
    public String clave, email, url, latitud, longitud,socialnet;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_items);

        myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listaSitios = (ArrayList<String>) getIntent().getSerializableExtra("listaSitios");

        //Toast.makeText(this, "Listasitios size:"+listaSitios.size(), Toast.LENGTH_SHORT).show();

        dataItems = new ArrayList<DataModel>();

        for (int i = 0; i < listaSitios.size(); i++) {
            DataModel data = new DataModel();
            fields = listaSitios.get(i).toString().split(",");
            data.setClave(fields[0]);
            data.setNombre(fields[1]);
            data.setDireccion(fields[2]);
            data.setTelefono(fields[3]);
            data.setEmail(fields[4]);
            data.setUrl(fields[5]);
            data.setLatitud(fields[6]);
            data.setLongitud(fields[7]);
            data.setSocialnet(fields[8]);
            //data.setImage(i);
            data.setCategoria(fields[9]);
            data.setDistancia(fields[10]);

            dataItems.add(data);
        }

        //Previo ordenamiento del dataItems array.
        if (GlobalValues.getModoSel().equals("2")){
            getOrderItems();
        }

        //Selecciona los mas cercanos, alrededor de 5 kms.
        if (GlobalValues.getModoSel().equals("3")){

            getCloserItems();

            for (int i=0; i<dataItems.size(); i++){
                //Log.d("ORDENADISTANCIA","nombre: " + dataItems.get(i).nombre + " dist: "+ dataItems.get(i).distancia);
                if (Double.parseDouble((dataItems.get(i).distancia)) > Double.parseDouble("3.00")){
                    //Log.d("REMOVE ITEM","double value" +Double.parseDouble(dataItems.get(i).distancia));
                    //Log.d("REMOVE ITEM","nombre: " + dataItems.get(i).nombre + " dist: "+ dataItems.get(i).distancia);
                    dataItems.remove(i);
                }
            }
        }

        adapter = new CustomAdapter(dataItems);
        recyclerView.setAdapter(adapter);

        //progressBar.setVisibility(View.INVISIBLE);
    }//fin oncreate


    //ordena array alfabeticamente x nombre.
    public void getOrderItems(){

        Collections.sort(dataItems, new Comparator<DataModel>() {
            public int compare(DataModel d1, DataModel d2) {
                return d1.nombre.compareTo(d2.nombre);
            }
        });
    }

    //ordena arrays x distancia
    public void getCloserItems(){

        Collections.sort(dataItems, new Comparator<DataModel>() {
            public int compare(DataModel d1, DataModel d2) {
                return d1.distancia.compareTo(d2.distancia);
            }
        });
    }


    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            //removeItem(v);
            categoria = GlobalValues.getcategorySelected();
            LlamaDescripcion(v);
        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.txtnombre);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }

            GlobalValues.setitemSelected(String.valueOf(selectedItemId));
        }
    }

    public void  LlamaDescripcion(View v){

        int selectedItemPosition = recyclerView.getChildPosition(v);
        Log.d("POSITION ITEM", "POS:" + selectedItemPosition);
        RecyclerView.ViewHolder viewHolder
                = recyclerView.findViewHolderForPosition(selectedItemPosition);
        TextView textViewNom = (TextView) viewHolder.itemView.findViewById(R.id.txtnombre);
        TextView textViewDir = (TextView) viewHolder.itemView.findViewById(R.id.txtdireccion);
        TextView textViewTel = (TextView) viewHolder.itemView.findViewById(R.id.txttelefono);
        TextView textViewDis = (TextView) viewHolder.itemView.findViewById(R.id.txtdistancia);

        String selectedNom = (String) textViewNom.getText();
        String selectedDir = (String) textViewDir.getText();
        String selectedTel = (String) textViewTel.getText();
        String selectedDis = (String) textViewDis.getText();

        //Informacion completa del item.
        clave = dataItems.get(selectedItemPosition).clave;
        email = dataItems.get(selectedItemPosition).email;
        url = dataItems.get(selectedItemPosition).url;
        latitud = dataItems.get(selectedItemPosition).latitud;
        longitud= dataItems.get(selectedItemPosition).longitud;
        socialnet= dataItems.get(selectedItemPosition).socialnet;


        Intent intent = new Intent(this, ItemDescription.class);
        intent.putExtra("position", selectedItemPosition);
        intent.putExtra("nombre", selectedNom);
        intent.putExtra("direccion", selectedDir);
        intent.putExtra("telefono", selectedTel);
        intent.putExtra("distancia", selectedDis);
        intent.putExtra("clave", clave);
        intent.putExtra("email", email);
        intent.putExtra("url", url);
        intent.putExtra("latitud", latitud);
        intent.putExtra("longitud", longitud);
        intent.putExtra("socialnet", socialnet);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_item) {
            //check if any items to add
            if (removedItems.size() != 0) {
                addRemovedItemToList();
            } else {
            }
        }
        return true;
    }

    private void addRemovedItemToList() {
           }

}//fin clase
