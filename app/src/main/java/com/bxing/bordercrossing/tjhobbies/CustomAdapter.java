package com.bxing.bordercrossing.tjhobbies;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre;
        TextView textViewDireccion;
        TextView textViewTelefono;
        TextView textViewDistancia;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewNombre = (TextView) itemView.findViewById(R.id.txtnombre);
            this.textViewDireccion = (TextView) itemView.findViewById(R.id.txtdireccion);
            this.textViewTelefono = (TextView) itemView.findViewById(R.id.txttelefono);
            this.textViewDistancia = (TextView) itemView.findViewById(R.id.txtdistancia);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_displaylist, parent, false);

        view.setOnClickListener(ListaItems.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        Log.d("POSITION HOLDER", " Position" + listPosition);
        Log.d("onBindViewHolder","dataSet.nombre " + dataSet.get(listPosition).getNombre());

        TextView textViewNom= holder.textViewNombre;
        TextView textViewDir = holder.textViewDireccion;
        TextView textViewTel = holder.textViewTelefono;
        ImageView imageView = holder.imageViewIcon;
        TextView textViewDis = holder.textViewDistancia;

        textViewNom.setText(dataSet.get(listPosition).getNombre());
        textViewDir.setText(dataSet.get(listPosition).getDireccion());
        textViewTel.setText(dataSet.get(listPosition).getTelefono());
        textViewDis.setText("Distancia: "+ dataSet.get(listPosition).getDistancia()+" Kms.");

        if (dataSet.get(listPosition).getCategoria().equals("1")){
            imageView.setImageResource(MyData.drawableArray[0]);
        }
        if (dataSet.get(listPosition).getCategoria().equals("2")){
            imageView.setImageResource(MyData.drawableArray[1]);
        }
        if (dataSet.get(listPosition).getCategoria().equals("3")){
            imageView.setImageResource(MyData.drawableArray[2]);
        }
        if (dataSet.get(listPosition).getCategoria().equals("4")){
            imageView.setImageResource(MyData.drawableArray[3]);
        }
        //imageView.setImageResource(MyData.drawableArray[listPosition]); ////////////////////
    }

    @Override
    public int getItemCount() {
        Log.d("getItemCount", " getItemCount" + dataSet.size());
        return dataSet.size();
    }
}
