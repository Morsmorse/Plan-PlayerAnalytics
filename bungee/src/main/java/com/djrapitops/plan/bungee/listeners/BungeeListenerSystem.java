package com.djrapitops.plan.bungee.listeners;

import com.djrapitops.plan.bungee.PlanBungee;
import com.djrapitops.plan.system.listeners.ListenerSystem;

public class BungeeListenerSystem extends ListenerSystem {

    private final PlanBungee plugin;

    public BungeeListenerSystem(PlanBungee plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void registerListeners() {
        plugin.registerListener(new PlayerOnlineListener(plugin.getRunnableFactory()));
    }

    @Override
    protected void unregisterListeners() {
        plugin.getProxy().getPluginManager().unregisterListeners(plugin);
    }
}
