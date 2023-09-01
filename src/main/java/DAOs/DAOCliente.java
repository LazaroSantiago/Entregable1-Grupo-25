package DAOs;


import Entity.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAOCliente {
    void insert(Cliente cliente) throws SQLException;

    ArrayList<Cliente> getClientes() throws SQLException;

    ArrayList<Cliente> getClientesMasFacturados() throws SQLException;
}
