package ru.linachan.db.query;

import ru.linachan.db.SQLDBManager;
import ru.linachan.db.SQLFilter;
import ru.linachan.db.SQLQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLDeleteQuery implements SQLQuery {

    private SQLDBManager dbManager;
    private String table;

    private List<SQLFilter> filters = new ArrayList<>();

    public SQLDeleteQuery(SQLDBManager dbManager, String table) {
        this.dbManager = dbManager;
        this.table = table;
    }


    public SQLDeleteQuery filter(SQLFilter filter) {
        filters.add(filter);
        return this;
    }


    @Override
    public String prepareQuery() {
        return String.format(
            "DELETE FROM `%s`%s;",
            table, (filters.size() > 0) ? " WHERE " + new SQLFilter().and(filters).toString() : ""
        );
    }

    @Override
    public ResultSet execute() throws SQLException {
        String query = prepareQuery();
        dbManager.getStatement().execute(query);
        return null;
    }
}
