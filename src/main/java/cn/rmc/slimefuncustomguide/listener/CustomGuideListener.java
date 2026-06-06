package cn.rmc.slimefuncustomguide.listener;

import cn.rmc.slimefuncustomguide.CustomGuidePlugin;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunGuideOpenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CustomGuideListener implements Listener {
    private final CustomGuidePlugin plugin;

    public CustomGuideListener(CustomGuidePlugin plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGuideOpen(SlimefunGuideOpenEvent event) {
        if (!plugin.isCustomGuideEnabled()) return;
    }
}
