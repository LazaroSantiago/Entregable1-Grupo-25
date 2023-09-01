package DAOs;

import Entity.Producto;

import java.sql.SQLException;

public interface DAOProducto {
    public void insert(Producto p) throws SQLException;
    public Producto getProductoMasRecaudado() throws SQLException;
}