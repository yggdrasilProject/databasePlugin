package ru.linachan.db;

import ru.linachan.yggdrasil.plugin.YggdrasilPlugin;
import ru.linachan.yggdrasil.plugin.helpers.Plugin;

import java.sql.SQLException;

@Plugin(name = "sql", description = "Provides support for SQL databases.")
public class SQLDBPlugin implements YggdrasilPlugin {

    private SQLDBManager manager;

    @Override
    public void onInit() {
        String dbDriver = core.getConfig().getString("mysql.driver", "com.mysql.jdbc.Driver");
        String dbUrl = core.getConfig().getString("mysql.uri", "jdbc:mysql://localhost/yggdrasil");
        String dbUser = core.getConfig().getString("mysql.user", "yggdrasil");
        String dbPassword = core.getConfig().getString("mysql.password", "");

        try {
            manager = new SQLDBManager(dbDriver, dbUrl, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            logger.error("SQL driver [{}] not found.", dbDriver);
            core.disablePackage("db");
        } catch (SQLException e) {
            logger.error("Unable to establish SQL connection [{}]: {}", e.getErrorCode(), e.getSQLState());
            core.disablePackage("db");
        }
    }

    @Override
    public void onShutdown() {
        try {
            manager.disconnect();
        } catch (SQLException e) {
            logger.error("Unable to properly close MySQL connection [{}]: {}", e.getErrorCode(), e.getSQLState());
        }
    }

    public SQLDBManager getDBManager() {
        return manager;
    }
}
