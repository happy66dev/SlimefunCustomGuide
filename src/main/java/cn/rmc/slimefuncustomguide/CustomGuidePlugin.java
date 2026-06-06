// SPDX-License-Identifier: GPL-3.0-or-later
// Copyright (C) 2025 happy (k666kkk666k@163.com)
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.
package cn.rmc.slimefuncustomguide;

import cn.rmc.slimefuncustomguide.command.CustomGuideCommand;
import cn.rmc.slimefuncustomguide.config.CategoryConfigLoader;
import cn.rmc.slimefuncustomguide.listener.CustomGuideListener;
import cn.rmc.slimefuncustomguide.model.CustomCategory;
import cn.rmc.slimefuncustomguide.web.WebApiHandler;
import cn.rmc.slimefuncustomguide.web.WebServer;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CustomGuidePlugin extends JavaPlugin implements SlimefunAddon {

    private static CustomGuidePlugin instance;
    private volatile List<CustomCategory> rootCategories;
    private WebServer webServer;
    private final Map<Player, ItemDetailReturn> itemDetailReturns = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveResource("categories.yml", false);

        File file = new File(getDataFolder(), "categories.yml");
        rootCategories = CategoryConfigLoader.load(file, getLogger());
        getLogger().info("Loaded " + rootCategories.size() + " top-level categories");

        getServer().getPluginManager().registerEvents(new CustomGuideListener(this), this);

        CustomGuideCommand cmd = new CustomGuideCommand(this);
        getCommand("slimefuncustomguide").setExecutor(cmd);
        getCommand("slimefuncustomguide").setTabCompleter(cmd);

        if (getConfig().getBoolean("web-editor.enabled", true)) {
            String bind = getConfig().getString("web-editor.bind", "127.0.0.1");
            int port = getConfig().getInt("web-editor.port", 8899);
            webServer = new WebServer();
            WebApiHandler handler = new WebApiHandler(this);
            try {
                webServer.start(bind, port, handler);
                getLogger().info("Web editor started at http://" + bind + ":" + port);
            } catch (Exception e) {
                getLogger().warning("Failed to start web editor: " + e.getMessage());
            }
        }
    }

    @Override
    public void onDisable() {
        if (webServer != null) {
            webServer.stop();
            getLogger().info("Web editor stopped");
        }
        instance = null;
    }

    public void reloadCategories() {
        File file = new File(getDataFolder(), "categories.yml");
        this.rootCategories = CategoryConfigLoader.load(file, getLogger());
        if (rootCategories.isEmpty()) {
            getLogger().warning("categories.yml is empty or missing");
        } else {
            getLogger().info("Reloaded " + rootCategories.size() + " root categories");
        }
    }

    public List<CustomCategory> getRootCategories() {
        return Collections.unmodifiableList(rootCategories);
    }

    public boolean isCustomGuideEnabled() {
        return getConfig().getBoolean("enable-custom-guide", true);
    }

    public Map<Player, ItemDetailReturn> getItemDetailReturns() { return itemDetailReturns; }

    public static CustomGuidePlugin getInstance() { return instance; }

    @Override public JavaPlugin getJavaPlugin() { return this; }
    @Override public String getBugTrackerURL() { return ""; }

    public static class ItemDetailReturn {
        public final CustomCategory category;
        public final int page;
        public final SlimefunGuideMode mode;
        public ItemDetailReturn(CustomCategory category, int page, SlimefunGuideMode mode) {
            this.category = category;
            this.page = page;
            this.mode = mode;
        }
    }
}
