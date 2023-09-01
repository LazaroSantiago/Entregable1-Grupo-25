package DAOs;

import Entity.Factura;

import java.sql.*;

import static Helpers.ConnectionHelper.startConnection;

public class DAOFacturaConcreto implements DAOFactura{
    private static DAOFacturaConcreto daoFacturaConcreto;
    private Connection connection = startConnection();

    private DAOFacturaConcreto() {
    }

    public static DAOFacturaConcreto createDAOFactura() {
        if (daoFacturaConcreto == null)
            daoFacturaConcreto = new DAOFacturaConcreto();
        return daoFacturaConcreto;
    }

    @Override
    public void insert(Factura f) throws SQLException {
        String insert = "INSERT INTO factura (idFactura, idCliente) VALUES (?,?)";
        PreparedStatement ps = connection.prepareStatement(insert);

        ps.setInt(1, f.getIdFactura());
        ps.setInt(2, f.getIdCliente());
        ps.executeUpdate();
        ps.close();
    }

}
