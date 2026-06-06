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
        if (last != null && last.category == category) {
            last.page = page;
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
        public final CustomCategory category;
        public int page;
        public CategoryEntry(CustomCategory category, int page) {
            this.category = category;
            this.page = page;
        }
    }
}
