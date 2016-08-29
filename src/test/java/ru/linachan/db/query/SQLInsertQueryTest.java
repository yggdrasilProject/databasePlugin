package ru.linachan.db.query;

import junit.framework.Assert;
import org.junit.Test;

public class SQLInsertQueryTest {

    @Test
    public void testSingleValue() {
        SQLInsertQuery query = new SQLInsertQuery(null, "table", new String[] { "column1", "column2" })
            .values("value1", "value2");
        Assert.assertEquals("INSERT INTO `table` (`column1`,`column2`) VALUES ('value1','value2');", query.prepareQuery());
    }

    @Test
    public void testMultipleValues() {
        SQLInsertQuery query = new SQLInsertQuery(null, "table", new String[] { "column1", "column2" })
            .values("value1", "value2")
            .values("value3", "value4");
        Assert.assertEquals("INSERT INTO `table` (`column1`,`column2`) VALUES ('value1','value2'),('value3','value4');", query.prepareQuery());
    }
}