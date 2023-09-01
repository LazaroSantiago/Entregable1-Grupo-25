package DAOs;

import Entity.Factura;
import Entity.Factura_Producto;

import java.sql.SQLException;

public interface DAOFactura_Producto {
    void insert(Factura_Producto fp) throws SQLException;
}
