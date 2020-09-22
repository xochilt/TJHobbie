package com.xrg.application.tjpoints;

public class DataModel {

    String name;
    String version;
    int id_;
    int image;

    //Modelo de thjobbies.
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
    String distancia;

    public DataModel(String name, String version, int id_, int image, String clave, String nombre,
                     String direccion, String telefono, String email, String url, String latitud,
                     String longitud, String socialnet, String categoria, String distancia) {

        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image =image;
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
        this.distancia = distancia;
    }

    public DataModel() {
        //contructor para guardar la firma de Datamodel.
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getSocialnet() {
        return socialnet;
    }

    public void setSocialnet(String socialnet) {
        this.socialnet = socialnet;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String dist) {
        this.distancia = dist;
    }

    public String getName() {
        return name;
    }
    public String getVersion() {
        return version;
    }
    public int getImage() {
        return image;
    }
    public int setImage(int img) {
        this.image = img;
        return image;
    }
    public int getId() {
        return id_;
    }

}
