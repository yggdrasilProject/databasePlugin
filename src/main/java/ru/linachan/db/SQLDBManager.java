package ru.linachan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDBManager {

    private Connection dbConnection;

    public SQLDBManager(String dbDriver, String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException, SQLException {
        Class.forName(dbDriver);
        dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void disconnect() throws SQLException {
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public SQLQueryBuilder query(String table) {
        return new SQLQueryBuilder(this, table);
    }

    public Statement getStatement() throws SQLException {
        return dbConnection.createStatement();
    }
}
