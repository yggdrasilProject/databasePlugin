package ru.linachan.db.query;

import junit.framework.Assert;
import org.junit.Test;
import ru.linachan.db.SQLFilter;

public class SQLUpdateQueryTest {

    @Test
    public void testSingleSet() {
        SQLUpdateQuery query = new SQLUpdateQuery(null, "table").set("column1", "value1");

        Assert.assertEquals("UPDATE `table` SET `column1` = 'value1';", query.prepareQuery());
    }

    @Test
    public void testMultipleSet() {
        SQLUpdateQuery query = new SQLUpdateQuery(null, "table")
            .set("column1", "value1")
            .set("column2", "value2");

        Assert.assertEquals("UPDATE `table` SET `column1` = 'value1',`column2` = 'value2';", query.prepareQuery());
    }

    @Test
    public void testFilterSet() {
        SQLUpdateQuery query = new SQLUpdateQuery(null, "table").set("column1", "value1").filter(new SQLFilter("column1").eq("value1"));

        Assert.assertEquals("UPDATE `table` SET `column1` = 'value1' WHERE (`column1` = 'value1');", query.prepareQuery());
    }
}