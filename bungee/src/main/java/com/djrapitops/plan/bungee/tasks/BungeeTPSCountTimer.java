package com.djrapitops.plan.bungee.tasks;

import com.djrapitops.plan.bungee.PlanBungee;
import com.djrapitops.plan.data.container.TPS;
import com.djrapitops.plan.data.container.builders.TPSBuilder;

public class BungeeTPSCountTimer extends com.djrapitops.plan.system.tasks.TPSCountTimer<PlanBungee> {

    public BungeeTPSCountTimer(PlanBungee plugin) {
        super(plugin);
    }

    @Override
    public void addNewTPSEntry(long nanoTime, long now) {
        int onlineCount = plugin.getProxy().getOnlineCount();
        TPS tps = TPSBuilder.get()
                .date(now)
                .skipTPS()
                .playersOnline(onlineCount)
                .toTPS();

        history.add(tps);
        latestPlayersOnline = onlineCount;
    }
}
