package ru.linachan.db;

import ru.linachan.db.query.SQLDeleteQuery;
import ru.linachan.db.query.SQLInsertQuery;
import ru.linachan.db.query.SQLSelectQuery;
import ru.linachan.db.query.SQLUpdateQuery;

public class SQLQueryBuilder {

    private String table;
    private SQLDBManager dbManager;

    public SQLQueryBuilder(SQLDBManager dbManager, String table) {
        this.table = table;
        this.dbManager = dbManager;
    }

    public SQLSelectQuery select(String... fields) {
        return new SQLSelectQuery(dbManager, table, fields);
    }

    public SQLInsertQuery insert(String... fields) {
        return new SQLInsertQuery(dbManager, table, fields);
    }

    public SQLUpdateQuery update() {
        return new SQLUpdateQuery(dbManager, table);
    }


    public SQLDeleteQuery delete() {
        return new SQLDeleteQuery(dbManager, table);
    }
}
