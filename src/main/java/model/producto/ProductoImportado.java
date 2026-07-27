package model.producto;

public class ProductoImportado extends Producto{

    private static final double IMPUESTO_IMPORTACION = 0.20;

    public ProductoImportado(String nombre, String descripcion,
                             double precio, Categoria categoria,
                             double peso, EstadoProducto estado) {

        super(nombre, descripcion, precio, categoria,
                peso, estado);
    }

    public ProductoImportado(int codigo, String nombre,
                             String descripcion, double precio,
                             Categoria categoria,
                             double peso, EstadoProducto estado) {

        super(codigo, nombre, descripcion, precio,
                categoria, peso, estado);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio() * (1 + IMPUESTO_IMPORTACION);
    }

    @Override
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje > 0 && porcentaje <= 100) {
            setPrecio(getPrecio() - (getPrecio() * porcentaje / 100));
        }
    }
}
