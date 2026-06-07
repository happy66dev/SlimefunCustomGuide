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
package cn.rmc.slimefunweaver.model;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomPlaceholderEntry implements GuideTreeNode {
    private final IconSource icon;
    private final String display;
    private final List<String> lore;
    private final int page;
    private final int slot;
    private final boolean glow;

    public CustomPlaceholderEntry(IconSource icon, String display,
                                  List<String> lore, int page, int slot, boolean glow) {
        this.icon = icon;
        this.display = display != null ? display : "";
        this.lore = lore != null ? new ArrayList<>(lore) : Collections.<String>emptyList();
        this.page = page;
        this.slot = slot;
        this.glow = glow;
    }

    public IconSource getIconSource() { return icon; }

    @Override public TreeNodeType getType() { return TreeNodeType.PLACEHOLDER; }
    @Override public String getDisplay() { return display; }

    @Override
    public ItemStack getIcon(Player player) { return null; }

    @Override public List<String> getLore() { return Collections.unmodifiableList(lore); }
    @Override public int getPage() { return page; }
    @Override public int getSlot() { return slot; }
    public boolean isGlow() { return glow; }
}
