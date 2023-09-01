package DAOs;

import Entity.Cliente;
import Entity.Producto;

import java.sql.*;
import java.util.ArrayList;

import static Helpers.ConnectionHelper.startConnection;

public class DAOProductoConcreto implements DAOProducto {
    private static DAOProductoConcreto daoProductoConcreto;
    private Connection connection = startConnection();

    private DAOProductoConcreto() {
    }

    public static DAOProductoConcreto createDAOProducto() {
        if (daoProductoConcreto == null)
            daoProductoConcreto = new DAOProductoConcreto();
        return daoProductoConcreto;
    }

    @Override
    public void insert(Producto p) throws SQLException {
        String insert = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(insert);

        ps.setInt(1, p.getIdProducto());
        ps.setString(2, p.getNombre());
        ps.setFloat(3, p.getValor());

        ps.executeUpdate();
        ps.close();
    }

    public Producto getProductoMasRecaudado() throws SQLException {
        String query = "SELECT p.*, SUM(valor*cantidad) as total\n" +
                        "FROM producto p JOIN factura_producto fp ON (p.idProducto = fp.idProducto)\n" +
                        "WHERE p.idProducto = fp.idProducto GROUP BY idProducto\n" +
                        "ORDER BY total DESC\n" +
                        "LIMIT 1";
        Producto result = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next())
            result = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3));

        return result;
    }
}
