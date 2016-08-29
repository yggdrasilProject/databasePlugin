package ru.linachan.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLQuery {

    String prepareQuery();
    ResultSet execute() throws SQLException;
}
