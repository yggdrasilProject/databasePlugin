package ru.linachan.db.query;

import com.google.common.base.Joiner;
import ru.linachan.db.SQLDBManager;
import ru.linachan.db.SQLFilter;
import ru.linachan.db.SQLQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SQLUpdateQuery implements SQLQuery {

    private SQLDBManager dbManager;
    private String table;

    private Map<String, String> set = new HashMap<>();
    private List<SQLFilter> filters = new ArrayList<>();

    public SQLUpdateQuery(SQLDBManager dbManager, String table) {
        this.dbManager = dbManager;
        this.table = table;
    }

    public SQLUpdateQuery set(String column, String value) {
        set.put(column, value);
        return this;
    }

    public SQLUpdateQuery filter(SQLFilter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public String prepareQuery() {
        return String.format(
            "UPDATE `%s` SET %s%s;",
            table, Joiner.on(",").join(
                set.keySet().stream()
                    .map(key -> String.format("`%s` = '%s'", key, set.get(key)))
                    .collect(Collectors.toList())
            ),
            (filters.size() > 0) ? " WHERE " + new SQLFilter().and(filters).toString() : ""
        );
    }

    @Override
    public ResultSet execute() throws SQLException {
        String query = prepareQuery();

        return dbManager.getStatement().executeQuery(query);
    }
}
