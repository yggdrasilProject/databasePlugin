package ru.linachan.db.query;

import com.google.common.base.Joiner;
import ru.linachan.db.SQLDBManager;
import ru.linachan.db.SQLFilter;
import ru.linachan.db.SQLQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLSelectQuery implements SQLQuery {

    private SQLDBManager dbManager;
    private String table;
    private String[] fields;
    private List<SQLFilter> filters = new ArrayList<>();

    private int limit = -1;
    private int offset = -1;

    private String order_by = null;
    private boolean descending = false;

    public SQLSelectQuery(SQLDBManager dbManager, String table, String[] fields) {
        this.dbManager = dbManager;
        this.table = table;
        this.fields = fields;
    }

    public SQLSelectQuery filter(SQLFilter filter) {
        filters.add(filter);
        return this;
    }

    public SQLSelectQuery limit(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;

        return this;
    }

    public SQLSelectQuery limit(int limit) {
        this.limit = limit;
        this.offset = -1;

        return this;
    }

    public SQLSelectQuery order(String column, boolean descending) {
        this.order_by = column;
        this.descending = descending;

        return this;
    }

    public SQLSelectQuery order(String column) {
        this.order_by = column;
        this.descending = false;

        return this;
    }

    @Override
    public String prepareQuery() {
        return String.format(
            "SELECT %s FROM `%s`%s%s%s;",
            (fields.length > 0) ? "`" + Joiner.on("`,`").join(fields) + "`" : "*", table,
            (filters.size() > 0) ? " WHERE " + new SQLFilter().and(filters).toString() : "",
            (order_by != null) ? String.format(" ORDER BY `%s` ", order_by) + ((descending) ? "DESC" : "ASC"): "",
            (limit >= 0) ? " LIMIT " + ((offset >= 0) ? String.format("%d,%d", limit, offset) : String.format("%d", limit)) : ""
        );
    }

    @Override
    public ResultSet execute() throws SQLException {
        String query = prepareQuery();

        return dbManager.getStatement().executeQuery(query);
    }
}
