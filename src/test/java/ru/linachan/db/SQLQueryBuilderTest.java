package ru.linachan.db;

import junit.framework.Assert;
import org.junit.Test;
import ru.linachan.db.query.SQLDeleteQuery;
import ru.linachan.db.query.SQLInsertQuery;
import ru.linachan.db.query.SQLSelectQuery;
import ru.linachan.db.query.SQLUpdateQuery;

public class SQLQueryBuilderTest {

    @Test
    public void testSelect() {
        SQLSelectQuery query = new SQLQueryBuilder(null, "table").select();

        Assert.assertEquals("SELECT * FROM `table`;", query.prepareQuery());
    }

    @Test
    public void testInsert() {
        SQLInsertQuery query = new SQLQueryBuilder(null, "table").insert("column1").values("value1");

        Assert.assertEquals("INSERT INTO `table` (`column1`) VALUES ('value1');", query.prepareQuery());
    }

    @Test
    public void testUpdate() {
        SQLUpdateQuery query = new SQLQueryBuilder(null, "table").update().set("column1", "value1");

        Assert.assertEquals("UPDATE `table` SET `column1` = 'value1';", query.prepareQuery());
    }

    @Test
    public void testDelete() {
        SQLDeleteQuery query = new SQLQueryBuilder(null, "table").delete();

        Assert.assertEquals("DELETE FROM `table`;", query.prepareQuery());
    }

}