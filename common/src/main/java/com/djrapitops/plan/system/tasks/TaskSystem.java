/*
 * License is provided in the jar as LICENSE also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/LICENSE
 */
package com.djrapitops.plan.system.tasks;

import com.djrapitops.plan.system.SubSystem;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

/**
 * TaskSystem that registers tasks that were previously registered inside Plugin classes.
 * <p>
 * Subclasses register actual tasks.
 *
 * @author Rsl1122
 */
public abstract class TaskSystem implements SubSystem {

    protected final RunnableFactory runnableFactory;
    protected TPSCountTimer tpsCountTimer;

    public TaskSystem(RunnableFactory runnableFactory, TPSCountTimer tpsCountTimer) {
        this.runnableFactory = runnableFactory;
        this.tpsCountTimer = tpsCountTimer;
    }

    protected PluginRunnable registerTask(String name, AbsRunnable runnable) {
        return runnableFactory.createNew(name, runnable);
    }

    @Override
    public void disable() {
        runnableFactory.cancelAllKnownTasks();
    }

    public TPSCountTimer getTpsCountTimer() {
        return tpsCountTimer;
    }
}
