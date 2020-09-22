package com.bxing.bordercrossing.tjhobbies;

public class SitiosModel {

    String clave;
    String nombre;
    String direccion;
    String telefono;
    String email;
    String url;
    String latitud;
    String longitud;
    String socialnet;
    String categoria;

    public SitiosModel(String clave, String nombre, String direccion, String telefono,
                       String email, String url,  String latitud, String longitud,
                       String socialnet, String categoria) {

        this.clave = clave;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.url = url;
        this.latitud = latitud;
        this.longitud = longitud;
        this.socialnet = socialnet;
        this.categoria = categoria;

    }

    public String  getClave(){ return clave;}
    public String  getNombre(){ return nombre;}
    public String  getDireccion(){ return direccion;}
    public String  getTelefono(){ return telefono;}
    public String  getEmail(){ return email;}
    public String  getUrl(){ return url;}
    public String  getLatitud(){ return latitud;}
    public String  getLongitud(){ return longitud;}
    public String  getSocialnet(){ return socialnet;}
    public String  getCategoria(){ return categoria;}

}
