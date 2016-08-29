package ru.linachan.db.query;

import junit.framework.Assert;
import org.junit.Test;
import ru.linachan.db.SQLFilter;
import ru.linachan.db.query.SQLSelectQuery;

public class SQLSelectQueryTest {

    @Test
    public void testNoFields() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] {});
        Assert.assertEquals("SELECT * FROM `table`;", query.prepareQuery());
    }

    @Test
    public void testOneField() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] { "column1" });
        Assert.assertEquals("SELECT `column1` FROM `table`;", query.prepareQuery());
    }

    @Test
    public void testManyFields() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] { "column1", "column2" });
        Assert.assertEquals("SELECT `column1`,`column2` FROM `table`;", query.prepareQuery());
    }

    @Test
    public void testFilter() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] {}).filter(new SQLFilter("column1").eq("value1"));
        Assert.assertEquals("SELECT * FROM `table` WHERE (`column1` = 'value1');", query.prepareQuery());
    }

    @Test
    public void testLimit() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] {}).limit(10);
        Assert.assertEquals("SELECT * FROM `table` LIMIT 10;", query.prepareQuery());
    }

    @Test
    public void testLimitOffset() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] {}).limit(10, 15);
        Assert.assertEquals("SELECT * FROM `table` LIMIT 10,15;", query.prepareQuery());
    }

    @Test
    public void testOrder() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] {}).order("column1");
        Assert.assertEquals("SELECT * FROM `table` ORDER BY `column1` ASC;", query.prepareQuery());
    }

    @Test
    public void testOrderDesc() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] {}).order("column1", true);
        Assert.assertEquals("SELECT * FROM `table` ORDER BY `column1` DESC;", query.prepareQuery());
    }

    @Test
    public void testComplexQuery() {
        SQLSelectQuery query = new SQLSelectQuery(null, "table", new String[] {"column1", "column2"})
            .filter(new SQLFilter().and(new SQLFilter("column1").eq("value1"), new SQLFilter().raw("1")))
            .limit(10, 25)
            .order("column1", false);
        Assert.assertEquals("SELECT `column1`,`column2` FROM `table` WHERE ((`column1` = 'value1') AND (1)) ORDER BY `column1` ASC LIMIT 10,25;", query.prepareQuery());
    }
}