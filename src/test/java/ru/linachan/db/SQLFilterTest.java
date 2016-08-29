package ru.linachan.db;

import junit.framework.Assert;
import org.junit.Test;

public class SQLFilterTest {

    @Test
    public void testRawFilter() {
        SQLFilter filter = new SQLFilter().raw("raw filter string");
        Assert.assertEquals("raw filter string", filter.toString());
    }

    @Test
    public void testEqualFilter() {
        SQLFilter filter = new SQLFilter("column1").eq("value1");
        Assert.assertEquals("`column1` = 'value1'", filter.toString());
    }

    @Test
    public void testLikeFilter() {
        SQLFilter filter = new SQLFilter("column1").like("value1");
        Assert.assertEquals("(`column1` LIKE '%value1%') OR (`column1` LIKE 'value1%') OR (`column1` LIKE '%value1') OR (`column1` LIKE 'value1')", filter.toString());
    }

    @Test
    public void testOrFilter() {
        SQLFilter filter = new SQLFilter().or(new SQLFilter("column1").eq("value1"), new SQLFilter("column2").eq("value2"));
        Assert.assertEquals("(`column1` = 'value1') OR (`column2` = 'value2')", filter.toString());
    }

    @Test
    public void testAndFilter() {
        SQLFilter filter = new SQLFilter().and(new SQLFilter("column1").eq("value1"), new SQLFilter("column2").eq("value2"));
        Assert.assertEquals("(`column1` = 'value1') AND (`column2` = 'value2')", filter.toString());
    }

    @Test
    public void testComplexFilter() {
        SQLFilter filter = new SQLFilter("column1").and(
            new SQLFilter().or(new SQLFilter("column1").eq("value1"), new SQLFilter("column2").eq("value2")),
            new SQLFilter().or(new SQLFilter("column3").eq("value3"), new SQLFilter("column4").eq("value4"))
        );
        Assert.assertEquals("((`column1` = 'value1') OR (`column2` = 'value2')) AND ((`column3` = 'value3') OR (`column4` = 'value4'))", filter.toString());
    }
}