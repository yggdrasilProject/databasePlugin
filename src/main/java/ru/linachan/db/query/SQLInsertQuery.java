package ru.linachan.db.query;

import com.google.common.base.Joiner;
import ru.linachan.db.SQLDBManager;
import ru.linachan.db.SQLQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLInsertQuery implements SQLQuery {

    private SQLDBManager dbManager;
    private String table;
    private String[] fields;

    private List<String[]> values = new ArrayList<>();

    public SQLInsertQuery(SQLDBManager dbManager, String table, String[] fields) {
        this.dbManager = dbManager;
        this.table = table;
        this.fields = fields;
    }

    public SQLInsertQuery values(String... values) {
        this.values.add(values);
        return this;
    }

    @Override
    public String prepareQuery() {
        return String.format(
            "INSERT INTO `%s` (%s) VALUES (%s);",
            table, "`" + Joiner.on("`,`").join(fields) + "`",
            Joiner.on("),(").join(
                values.stream()
                    .map(value -> "'" + Joiner.on("','").join(value) + "'")
                    .collect(Collectors.toList())
            )
        );
    }

    @Override
    public ResultSet execute() throws SQLException {
        String query = prepareQuery();

        return dbManager.getStatement().executeQuery(query);
    }
}
