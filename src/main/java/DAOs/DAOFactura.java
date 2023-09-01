package DAOs;

import Entity.Factura;

import java.sql.SQLException;

public interface DAOFactura {
    void insert(Factura f) throws SQLException;
}
