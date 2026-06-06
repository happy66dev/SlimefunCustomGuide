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
package cn.rmc.slimefuncustomguide.guide;

import cn.rmc.slimefuncustomguide.CustomGuidePlugin;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideImplementation;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class CustomGuideImplementation implements SlimefunGuideImplementation {

    private final CustomGuidePlugin plugin;

    public CustomGuideImplementation(CustomGuidePlugin plugin) {
        this.plugin = plugin;
    }

    @Nonnull
    @Override
    public SlimefunGuideMode getMode() {
        return SlimefunGuideMode.SURVIVAL_MODE;
    }

    @Nonnull
    @Override
    public ItemStack getItem() {
        return SlimefunGuide.getItem(SlimefunGuideMode.SURVIVAL_MODE);
    }

    // Guide opening is intercepted by CustomGuideListener.onGuideOpen
    // This implementation acts as a pass-through/fallback and is intentionally empty
    @Override
    @ParametersAreNonnullByDefault
    public void openMainMenu(PlayerProfile profile, int page) {
        Player player = profile.getPlayer();
        if (player == null) return;
    }

    // Guide opening is intercepted by CustomGuideListener.onGuideOpen
    // This implementation acts as a pass-through/fallback and is intentionally empty
    @Override
    @ParametersAreNonnullByDefault
    public void openItemGroup(PlayerProfile profile, ItemGroup group, int page) {
    }

    @Override
    @ParametersAreNonnullByDefault
    public void openSearch(PlayerProfile profile, String input, boolean addToHistory) {
        SlimefunGuide.openSearch(profile, input, SlimefunGuideMode.SURVIVAL_MODE, addToHistory);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void displayItem(PlayerProfile profile, ItemStack item, int index, boolean addToHistory) {
        Slimefun.getRegistry().getSlimefunGuide(SlimefunGuideMode.SURVIVAL_MODE)
                .displayItem(profile, item, index, addToHistory);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void displayItem(PlayerProfile profile, SlimefunItem item, boolean addToHistory) {
        Slimefun.getRegistry().getSlimefunGuide(SlimefunGuideMode.SURVIVAL_MODE)
                .displayItem(profile, item, addToHistory);
    }
}
