package org.example;

import DAOs.*;
import Helpers.TableHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static DAOs.DAOClienteConcreto.createDAOCliente;
import static DAOs.DAOFacturaConcreto.createDAOFactura;
import static DAOs.DAOFactura_ProductoConcreto.createDAOFacturaProducto;
import static DAOs.DAOProductoConcreto.createDAOProducto;
import static Helpers.ConnectionHelper.startConnection;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = startConnection();
        DAOClienteConcreto dc = createDAOCliente();
        DAOFactura df = createDAOFactura();
        DAOProducto dp = createDAOProducto();
        DAOFactura_Producto dfp = createDAOFacturaProducto();
        TableHelper th = new TableHelper(connection, dc, df, dp, dfp);
        System.out.println("El producto mas recaudado es: " + dp.getProductoMasRecaudado().getNombre());
        System.out.println("El cliente que mas facturo es: " + dc.getClientesMasFacturados().get(0));
        connection.close();
    }
}