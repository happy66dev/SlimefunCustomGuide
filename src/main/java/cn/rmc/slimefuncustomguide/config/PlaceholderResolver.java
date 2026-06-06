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
package cn.rmc.slimefuncustomguide.config;

import cn.rmc.slimefuncustomguide.model.CustomCategory;
import cn.rmc.slimefuncustomguide.model.GuideTreeNode;

import java.util.ArrayList;
import java.util.List;

public final class PlaceholderResolver {

    private PlaceholderResolver() {}

    public static List<String> resolve(GuideTreeNode node, int currentPage, int totalPages) {
        List<String> raw = node.getLore();
        if (raw.isEmpty()) return raw;

        String pageStr = String.valueOf(currentPage);
        String totalPagesStr = String.valueOf(totalPages);
        String slotStr = String.valueOf(node.getSlot());

        String childrenCountStr = null;
        String directItemsCountStr = null;
        String totalItemsCountStr = null;
        if (node instanceof CustomCategory) {
            CustomCategory cat = (CustomCategory) node;
            childrenCountStr = String.valueOf(cat.getChildrenCount());
            directItemsCountStr = String.valueOf(cat.getDirectItemsCount());
            totalItemsCountStr = String.valueOf(cat.getTotalItemsCount());
        }

        List<String> resolved = new ArrayList<>(raw.size());
        for (String line : raw) {
            String result = line;
            result = result.replace("{page}", pageStr);
            result = result.replace("{total_pages}", totalPagesStr);
            result = result.replace("{slot}", slotStr);

            if (childrenCountStr != null) {
                result = result.replace("{children_count}", childrenCountStr);
                result = result.replace("{items_count}", directItemsCountStr);
                result = result.replace("{total_items_count}", totalItemsCountStr);
            }

            resolved.add(result);
        }
        return resolved;
    }
}
