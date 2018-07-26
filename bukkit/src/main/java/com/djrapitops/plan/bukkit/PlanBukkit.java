/*
 *    Player Analytics Bukkit plugin for monitoring server activity.
 *    Copyright (C) 2017  Risto Lahtela / Rsl1122
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the PlanBukkit License. (LICENSE)
 *    Modified software can only be redistributed if allowed in the licence.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    License for more details.
 *
 *    You should have received a copy of the License
 *    along with this program.
 *    If not it should be visible on the distribution page.
 *    Or here
 *    https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/LICENSE
 */
package com.djrapitops.plan.bukkit;

import com.djrapitops.plan.PlanHelper;
import com.djrapitops.plan.PlanPlugin;
import com.djrapitops.plan.api.exceptions.EnableException;
import com.djrapitops.plan.bukkit.metrics.BStats;
import com.djrapitops.plan.bukkit.processing.importing.importers.OfflinePlayerImporter;
import com.djrapitops.plan.command.PlanCommand;
import com.djrapitops.plan.system.processing.importing.ImporterManager;
import com.djrapitops.plan.system.settings.locale.Locale;
import com.djrapitops.plan.system.settings.locale.Msg;
import com.djrapitops.plan.system.settings.theme.PlanColorScheme;
import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.command.ColorScheme;
import com.djrapitops.plugin.logging.L;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Main class for Bukkit that manages the plugin.
 *
 * @author Rsl1122
 * @since 1.0.0
 */
public class PlanBukkit extends BukkitPlugin implements PlanPlugin {

    private BukkitSystem system;

    /**
     * Used to get the plugin-instance singleton.
     *
     * @return this object.
     */
    @Deprecated
    public static PlanBukkit getInstance() {
        return (PlanBukkit) StaticHolder.getInstance(PlanBukkit.class);
    }

    /**
     * OnEnable method.
     * <p>
     * - Enables the plugin's subsystems.
     */
    @Override
    public void onEnable() {
        super.onEnable();

        PlanHelper.setInstance(this); //ToDo: Really dirty temporary hack

        try {
            Benchmark.start("Enable");
            system = new BukkitSystem(this);
            system.enable();

            ImporterManager.registerImporter(new OfflinePlayerImporter());

            BStats bStats = new BStats(this);
            bStats.registerMetrics();

            logger.debug("Verbose debug messages are enabled.");
            Benchmark.stop("Enable", "Enable");
            logger.info(Locale.get(Msg.ENABLED).toString());
        } catch (AbstractMethodError e) {
            logger.error("Plugin ran into AbstractMethodError - Server restart is required. Likely cause is updating the jar without a restart.");
        } catch (EnableException e) {
            logger.error("----------------------------------------");
            logger.error("Error: " + e.getMessage());
            logger.error("----------------------------------------");
            logger.error("Plugin Failed to Initialize Correctly. If this issue is caused by config settings you can use /plan reload");
            onDisable();
        } catch (Exception e) {
            logger.log(L.CRITICAL, this.getClass().getSimpleName() + "-v" + getVersion(), e);
            logger.error("Plugin Failed to Initialize Correctly. If this issue is caused by config settings you can use /plan reload");
            logger.error("This error should be reported at https://github.com/Rsl1122/PlanBukkit-PlayerAnalytics/issues");
            onDisable();
        }
        registerCommand("plan", new PlanCommand(this));
    }

    @Override
    public ColorScheme getColorScheme() {
        return PlanColorScheme.create();
    }

    /**
     * Disables the plugin.
     */
    @Override
    public void onDisable() {
        system.disable();

        logger.info(Locale.get(Msg.DISABLED).toString());
        Benchmark.pluginDisabled(PlanBukkit.class);
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public void onReload() {

    }

    @Override
    public boolean isReloading() {
        return reloading;
    }

    /**
     * @deprecated Deprecated due to use of APF Config
     */
    @Override
    @Deprecated
    public void reloadConfig() {
        throw new IllegalStateException("This method should be used on this plugin. Use onReload() instead");
    }

    /**
     * @deprecated Deprecated due to use of APF Config
     */
    @Override
    @Deprecated
    public FileConfiguration getConfig() {
        throw new IllegalStateException("This method should be used on this plugin. Use getMainConfig() instead");
    }

    /**
     * @deprecated Deprecated due to use of APF Config
     */
    @Override
    @Deprecated
    public void saveConfig() {
        throw new IllegalStateException("This method should be used on this plugin. Use getMainConfig().save() instead");
    }

    /**
     * @deprecated Deprecated due to use of APF Config
     */
    @Override
    @Deprecated
    public void saveDefaultConfig() {
        throw new IllegalStateException("This method should be used on this plugin.");
    }

    public BukkitSystem getSystem() {
        return system;
    }
}
