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

import cn.rmc.slimefuncustomguide.model.CustomCategory;

import java.util.Deque;
import java.util.LinkedList;

public class CustomGuideHistory {

    private final Deque<CategoryEntry> stack = new LinkedList<>();
    private int mainMenuPage = 1;

    public void clear() { stack.clear(); }
    public void setMainMenuPage(int page) { this.mainMenuPage = Math.max(1, page); }
    public int getMainMenuPage() { return mainMenuPage; }

    public void push(CustomCategory category, int page) {
        CategoryEntry last = stack.peekLast();
        if (last != null && last.getCategory().getKey().equals(category.getKey())) {
            last.setPage(page);
        } else {
            stack.addLast(new CategoryEntry(category, page));
        }
    }

    public boolean hasHistory() { return !stack.isEmpty(); }

    public CategoryEntry goBack() {
        if (!stack.isEmpty()) stack.removeLast();
        return stack.peekLast();
    }

    public CategoryEntry getCurrent() { return stack.peekLast(); }

    public static class CategoryEntry {
        private final CustomCategory category;
        private int page;

        public CategoryEntry(CustomCategory category, int page) {
            this.category = category;
            this.page = page;
        }

        public CustomCategory getCategory() { return category; }
        public int getPage() { return page; }
        public void setPage(int page) { this.page = page; }
    }
}
