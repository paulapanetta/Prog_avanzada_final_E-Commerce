package model.producto;

public class ProductoDigital extends Producto {

    public ProductoDigital(String nombre, String descripcion,
                           double precio, Categoria categoria,
                           int stock, double peso,
                           EstadoProducto estado, String linkDescarga) {

        super(nombre, descripcion, precio, categoria, stock, peso, estado);
    }

    public ProductoDigital(int codigo, String nombre,
                           String descripcion, double precio,
                           Categoria categoria, int stock,
                           double peso, EstadoProducto estado,
                           String linkDescarga) {

        super(codigo, nombre, descripcion, precio, categoria, stock, peso, estado);
    }


    @Override
    public double calcularPrecioFinal() {
        return getPrecio();
    }

    @Override
    public void aplicarDescuento(double porcentaje) {

        if (porcentaje > 0 && porcentaje <= 100) {
            setPrecio(getPrecio() - (getPrecio() * porcentaje / 100));
        }
    }

}
