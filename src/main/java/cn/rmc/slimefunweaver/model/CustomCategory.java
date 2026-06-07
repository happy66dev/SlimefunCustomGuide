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

public class CustomCategory implements GuideTreeNode {
    private final String key;
    private final String display;
    private final IconSource icon;
    private final List<String> lore;
    private final int page;
    private final int slot;
    private final boolean glow;
    private final List<GuideTreeNode> children;

    private int childrenCount;
    private int directItemsCount;
    private int totalItemsCount;

    public CustomCategory(String key, String display, IconSource icon,
                          List<String> lore, int page, int slot, boolean glow) {
        this.key = key;
        this.display = display != null ? display : key;
        this.icon = icon;
        this.lore = lore != null ? new ArrayList<>(lore) : Collections.<String>emptyList();
        this.page = page;
        this.slot = slot;
        this.glow = glow;
        this.children = new ArrayList<>();
    }

    public String getKey() { return key; }

    @Override public TreeNodeType getType() { return TreeNodeType.CATEGORY; }
    @Override public String getDisplay() { return display; }

    @Override
    public ItemStack getIcon(Player player) { return null; }

    @Override public List<String> getLore() { return Collections.unmodifiableList(lore); }
    @Override public int getPage() { return page; }
    @Override public int getSlot() { return slot; }

    public IconSource getIconSource() { return icon; }
    public boolean isGlow() { return glow; }
    public void addChild(GuideTreeNode child) { children.add(child); }
    public List<GuideTreeNode> getChildren() { return Collections.unmodifiableList(children); }

    public int getChildrenCount() { return childrenCount; }
    public void setChildrenCount(int childrenCount) { this.childrenCount = childrenCount; }
    public int getDirectItemsCount() { return directItemsCount; }
    public void setDirectItemsCount(int directItemsCount) { this.directItemsCount = directItemsCount; }
    public int getTotalItemsCount() { return totalItemsCount; }
    public void setTotalItemsCount(int totalItemsCount) { this.totalItemsCount = totalItemsCount; }
}
