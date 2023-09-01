package DAOs;

import Entity.Cliente;
import Entity.Factura_Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Helpers.ConnectionHelper.startConnection;

public class DAOFactura_ProductoConcreto implements DAOFactura_Producto {
    private static DAOFactura_ProductoConcreto daoClienteConcreto;
    private Connection connection = startConnection();

    private DAOFactura_ProductoConcreto() {
    }

    public static DAOFactura_ProductoConcreto createDAOFacturaProducto() {
        if (daoClienteConcreto == null)
            daoClienteConcreto = new DAOFactura_ProductoConcreto();
        return daoClienteConcreto;
    }

    @Override
    public void insert(Factura_Producto fp) throws SQLException {
        String insert = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(insert);

        ps.setInt(1, fp.getIdFactura());
        ps.setInt(2, fp.getIdProducto());
        ps.setInt(3, fp.getCantidad());

        ps.executeUpdate();
        ps.close();
    }
}
