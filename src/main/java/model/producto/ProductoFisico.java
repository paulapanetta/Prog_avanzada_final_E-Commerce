package model.producto;

public class ProductoFisico extends Producto {


    public ProductoFisico(String nombre, String descripcion,
                          double precio, Categoria categoria,
                          double peso, EstadoProducto estado) {

        super(nombre, descripcion, precio, categoria, peso, estado);
    }

    public ProductoFisico(int codigo, String nombre,
                          String descripcion, double precio,
                          Categoria categoria,
                          double peso, EstadoProducto estado) {

        super(codigo, nombre, descripcion, precio, categoria, peso, estado);
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
