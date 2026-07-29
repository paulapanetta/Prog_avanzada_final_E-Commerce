package model.envio;

import model.interfaces.Mostrable;

import exceptions.DatosInvalidosException;

public class Envio implements Mostrable {

    private int id;
    private int ordenId;
    private String codigoSeguimiento;
    private String direccion;
    private String provincia;
    private String ciudad;
    private String codigoPostal;
    private TipoEnvio tipoEnvio;
    private EstadoEnvio estado;
    private double costo;


    public Envio(int ordenId, String direccion, String provincia,
                 String ciudad, String codigoPostal,
                 TipoEnvio tipoEnvio) {

        if (direccion == null || direccion.isBlank()) {
            throw new DatosInvalidosException(
                    "La direccion no puede estar vacia"
            );
        }

        if (tipoEnvio == null) {
            throw new DatosInvalidosException(
                    "Debe indicarse un tipo de envio"
            );
        }

        this.ordenId = ordenId;
        this.direccion = direccion;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.tipoEnvio = tipoEnvio;
        this.estado = EstadoEnvio.PENDIENTE;
        this.costo = calcularCostoEnvio();
    }

    public Envio(int id, String codigoSeguimiento, int ordenId,
                 String direccion, String provincia,
                 String ciudad, String codigoPostal,
                 TipoEnvio tipoEnvio, EstadoEnvio estado,
                 double costo) {

        this.id = id;
        this.codigoSeguimiento = codigoSeguimiento;
        this.ordenId = ordenId;
        this.direccion = direccion;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.tipoEnvio = tipoEnvio;
        this.estado = estado;
        this.costo = costo;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdenId() {return ordenId;}

    public String getCodigoSeguimiento() { return codigoSeguimiento; }

    public void asignarCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() { return provincia; }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() { return ciudad; }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() { return codigoPostal; }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public TipoEnvio getTipoEnvio() { return tipoEnvio; }

    public EstadoEnvio getEstado() { return estado; }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }

    public double getCosto() { return costo; }


    public double calcularCostoEnvio() {

        if (tipoEnvio == TipoEnvio.RETIRO_SUCURSAL) {

            return 0;

        } else if (tipoEnvio == TipoEnvio.ESTANDAR) {

            return 1500;

        } else if (tipoEnvio == TipoEnvio.EXPRES) {

            return 3200;

        } else {

            return 8000;
        }
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Envio{" +
                "id=" + id +
                ", ordenId=" + ordenId +
                ", codigoSeguimiento='" + codigoSeguimiento + '\'' +
                ", direccion='" + direccion + '\'' +
                ", provincia='" + provincia + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", tipoEnvio=" + tipoEnvio +
                ", estado=" + estado +
                ", costo=" + costo +
                '}';
    }
}
