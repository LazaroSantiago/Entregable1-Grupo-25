package DAOs;


import Entity.Cliente;

import java.sql.*;
import java.util.ArrayList;

import static Helpers.ConnectionHelper.startConnection;

public class DAOClienteConcreto implements DAOCliente {
    private static DAOClienteConcreto daoClienteConcreto;
    private Connection connection = startConnection();

    private DAOClienteConcreto() {
    }

    public static DAOClienteConcreto createDAOCliente() {
        if (daoClienteConcreto == null)
            daoClienteConcreto = new DAOClienteConcreto();
        return daoClienteConcreto;
    }

    @Override
    public void insert(Cliente c) throws SQLException {
        String insert = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(insert);

        ps.setInt(1, c.getIdCliente());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getEmail());

        ps.executeUpdate();
        ps.close();
    }

    @Override
    public ArrayList<Cliente> getClientes() throws SQLException {
        String query = "select * FROM cliente";
        ArrayList<Cliente> result = resolveStatement(query);
        return result;
    }

    @Override
    public ArrayList<Cliente> getClientesMasFacturados() throws SQLException {
        String query =
                "SELECT c.*, SUM(p.valor*fp.cantidad) as mejores_clientes\n" +
                "FROM cliente c JOIN factura f ON (c.idCliente = f.idCliente) JOIN factura_producto fp ON (f.idFactura = fp.idFactura) JOIN producto p ON (p.idProducto = fp.idProducto)\n" +
                "WHERE c.idCliente = f.idCliente GROUP BY c.idCliente ORDER BY mejores_clientes DESC";
        ArrayList<Cliente> result = resolveStatement(query);
        return result;
    }

    private ArrayList<Cliente> resolveStatement(String query) throws SQLException {
        ArrayList<Cliente> result = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next())
            result.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3)));
        return result;
    }

    //select c.*, sum(p.valor*fp.cantidad)  as mejores_clientes
    //from cliente c join factura f on (c.idCliente = f.idCliente) JOIN factura_producto fp ON(f.idFactura = fp.idFactura)
    //WHERE c.idCliente = f.idCliente GROUP BY c.idCliente ORDER BY mejores_clientes DESC
}
