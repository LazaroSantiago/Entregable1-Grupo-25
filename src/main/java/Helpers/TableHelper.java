package Helpers;

import DAOs.DAOCliente;
import DAOs.DAOFactura;
import DAOs.DAOFactura_Producto;
import DAOs.DAOProducto;
import Entity.Cliente;
import Entity.Factura;
import Entity.Factura_Producto;
import Entity.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableHelper {
    private final String tablaCliente =
            "CREATE TABLE IF NOT EXISTS cliente(" +
                    "idCliente INT," +
                    "nombre VARCHAR(500)," +
                    "email VARCHAR(150)," +
                    "PRIMARY KEY(idCliente))";

    private final String tablaFactura =
            "CREATE TABLE IF NOT EXISTS factura(" +
                    "idFactura INT," +
                    "idCliente INT," +
                    "PRIMARY KEY(idFactura)," +
                    "FOREIGN KEY (idCliente) REFERENCES cliente (idCliente))";

    private final String tablaProducto =
            "CREATE TABLE IF NOT EXISTS producto(" +
                    "idProducto INT," +
                    "nombre VARCHAR(45)," +
                    "valor FLOAT," +
                    "PRIMARY KEY(idProducto))";

    private final String tablaFacturaProducto =
            "CREATE TABLE IF NOT EXISTS factura_producto(" +
                    "idFactura INT," +
                    "idProducto INT," +
                    "cantidad INT," +
                    "FOREIGN KEY (idFactura) REFERENCES factura (idFactura)," +
                    "FOREIGN KEY (idProducto) REFERENCES producto (idProducto))";

    private Connection connection;
    private DAOCliente daoCliente;
    private DAOFactura daoFactura;
    private DAOProducto daoProducto;
    private DAOFactura_Producto daoFacturaProducto;
    public TableHelper(Connection connection, DAOCliente daoCliente, DAOFactura daoFactura, DAOProducto daoProducto, DAOFactura_Producto daoFacturaProducto) throws SQLException {
        this.connection = connection;
        this.daoCliente = daoCliente;
        this.daoFactura = daoFactura;
        this.daoProducto = daoProducto;
        this.daoFacturaProducto = daoFacturaProducto;
        this.createTables();
    }

    private void createTables() throws SQLException {
        connection.prepareStatement(tablaCliente).execute();
        connection.prepareStatement(tablaFactura).execute();
        connection.prepareStatement(tablaProducto).execute();
        connection.prepareStatement(tablaFacturaProducto).execute();
        fillTables();
        connection.commit();
    }

    private void fillTables() {
        CSVParser parser = null;
        try {
            fillCliente();
            fillFactura();
            fillProducto();
            fillFacturaProducto();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCliente() throws IOException, SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * FROM cliente LIMIT 1");

        if (!rs.next()) {
            CSVParser parser;
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("src\\main\\resources\\clientes.csv"));
            for (CSVRecord row : parser) {
                Cliente c = new Cliente(Integer.parseInt(row.get("idCliente")), row.get("nombre"), row.get("email"));
                this.daoCliente.insert(c);
            }
        }
    }

    private void fillFactura() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * FROM factura LIMIT 1");

        if (!rs.next()) {
            CSVParser parser;
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("src\\main\\resources\\facturas.csv"));
            for (CSVRecord row : parser) {
                Factura f = new Factura(Integer.parseInt(row.get("idFactura")), Integer.parseInt(row.get("idCliente")));
                this.daoFactura.insert(f);
            }
        }
    }

    private void fillFacturaProducto() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * FROM factura_producto LIMIT 1");

        if (!rs.next()) {
            CSVParser parser;
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("src\\main\\resources\\facturas-productos.csv"));
            for (CSVRecord row : parser) {
                Factura_Producto fp = new Factura_Producto(Integer.parseInt(row.get("idFactura")), Integer.parseInt(row.get("idProducto")), Integer.parseInt(row.get("cantidad")));
                this.daoFacturaProducto.insert(fp);
            }
        }
    }

    private void fillProducto() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * FROM producto LIMIT 1");

        if (!rs.next()) {
            CSVParser parser;
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("src\\main\\resources\\productos.csv"));
            for (CSVRecord row : parser) {
                Producto p = new Producto(Integer.parseInt(row.get("idProducto")), row.get("nombre"), Float.parseFloat(row.get("valor")));
                this.daoProducto.insert(p);
            }
        }
    }

}
