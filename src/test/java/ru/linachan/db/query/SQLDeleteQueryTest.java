package ru.linachan.db.query;

import junit.framework.Assert;
import org.junit.Test;
import ru.linachan.db.SQLFilter;

public class SQLDeleteQueryTest {

    @Test
    public void testNoFilter() {
        SQLDeleteQuery query = new SQLDeleteQuery(null, "table");

        Assert.assertEquals("DELETE FROM `table`;", query.prepareQuery());
    }

    @Test
    public void testWithFilter() {
        SQLDeleteQuery query = new SQLDeleteQuery(null, "table").filter(new SQLFilter("column1").eq("value1"));

        Assert.assertEquals("DELETE FROM `table` WHERE (`column1` = 'value1');", query.prepareQuery());
    }
}