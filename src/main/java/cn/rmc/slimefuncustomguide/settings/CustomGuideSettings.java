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
package cn.rmc.slimefuncustomguide.settings;

import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class CustomGuideSettings {

    private static final CustomGuideModeOption modeOption = new CustomGuideModeOption();
    private static final int[] BG = {
        1, 2, 3, 4, 5, 6, 7, 8,
        9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35,
        36, 37, 38, 39, 40, 41, 42, 43, 44,
        45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    private CustomGuideSettings() {}

    public static void openSettings(Player p, ItemStack guide) {
        ChestMenu menu = new ChestMenu(ChatColor.BLACK + "Slimefun 指南设置");
        menu.setEmptySlotsClickable(false);

        for (int slot : BG) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(0, new CustomItemStack(ChestMenuUtils.getBackButton(p, "",
                ChatColor.GRAY + Slimefun.getLocalization().getMessage(p, "guide.back.settings"))));
        menu.addMenuClickHandler(0, (pl, s, is, action) -> {
            pl.closeInventory();
            return false;
        });

        menu.addItem(4, new CustomItemStack(
                ChestMenuUtils.getSearchButton(p),
                ChatColor.WHITE + Slimefun.getLocalization().getMessage(p, "guide.title.settings"),
                "",
                ChatColor.YELLOW + "自定义指南书设置"
        ));
        menu.addMenuClickHandler(4, ChestMenuUtils.getEmptyClickHandler());

        ItemStack modeItem = modeOption.getDisplayItem(p, guide).orElse(new ItemStack(Material.BARRIER));
        menu.addItem(20, modeItem);
        menu.addMenuClickHandler(20, (pl, s, is, action) -> {
            modeOption.onClick(pl, guide);
            return false;
        });

        boolean isCheat = SlimefunUtils.isItemSimilar(guide, SlimefunGuide.getItem(SlimefunGuideMode.CHEAT_MODE), true, false);
        ItemStack cheatItem;
        if (isCheat) {
            cheatItem = new ItemStack(Material.COMMAND_BLOCK);
        } else {
            cheatItem = new ItemStack(Material.CHEST);
        }
        ItemMeta cheatMeta = cheatItem.getItemMeta();
        cheatMeta.setDisplayName(ChatColor.GRAY + "指南类型: " + ChatColor.YELLOW + (isCheat ? "作弊模式" : "生存模式"));
        List<String> cheatLore = new ArrayList<>();
        cheatLore.add("");
        cheatLore.add(ChatColor.GRAY + "使用 /sf cheat 获取作弊指南");
        cheatMeta.setLore(cheatLore);
        cheatItem.setItemMeta(cheatMeta);
        menu.addItem(24, cheatItem);
        menu.addMenuClickHandler(24, ChestMenuUtils.getEmptyClickHandler());

        menu.open(p);
    }
}
