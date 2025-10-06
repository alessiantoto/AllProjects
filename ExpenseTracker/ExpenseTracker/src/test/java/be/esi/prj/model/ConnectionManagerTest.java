package be.esi.prj.model;

import be.esi.prj.model.Connection.ConnectionManager;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionManagerTest {
    @Test
    void testConnectionNotNull() {
        Connection connection = ConnectionManager.getConnection();
        assertNotNull(connection);
    }

}
