package com.djrapitops.plan.system.database.databases.sql.patches;

import com.djrapitops.plan.api.exceptions.database.DBInitException;
import com.djrapitops.plan.api.exceptions.database.DBOpException;
import com.djrapitops.plan.system.database.databases.sql.SQLDB;
import com.djrapitops.plan.system.database.databases.sql.tables.move.Version8TransferTable;

public class Version10Patch extends Patch {

    public Version10Patch(SQLDB db) {
        super(db);
    }

    @Override
    public boolean hasBeenApplied() {
        return !hasTable("plan_gamemodetimes");
    }

    @Override
    public void apply() {
        try {
            new Version8TransferTable(db).alterTablesToV10();
        } catch (DBInitException e) {
            throw new DBOpException(e.getMessage(), e);
        }
    }
}
