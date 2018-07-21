package com.djrapitops.plan.bukkit.database;

import com.djrapitops.plan.api.exceptions.database.DBInitException;
import com.djrapitops.plan.bukkit.PlanBukkit;
import com.djrapitops.plan.system.database.DBSystem;
import com.djrapitops.plan.system.database.databases.sql.MySQLDB;
import com.djrapitops.plan.system.database.databases.sql.SQLiteDB;
import com.djrapitops.plan.system.settings.Settings;

public class BukkitDatabaseSystem extends DBSystem {
    public BukkitDatabaseSystem(PlanBukkit plugin) {
        super(plugin.getRunnableFactory());
    }

    @Override
    protected void initDatabase() throws DBInitException {
        databases.add(new MySQLDB(runnableFactory));
        databases.add(new SQLiteDB(runnableFactory));

        String dbType = Settings.DB_TYPE.toString().toLowerCase().trim();
        db = getActiveDatabaseByName(dbType);
    }
}
