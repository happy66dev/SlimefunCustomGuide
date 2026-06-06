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

        List<String> resolved = new ArrayList<>(raw.size());
        for (String line : raw) {
            String result = line;
            result = result.replace("{page}", String.valueOf(currentPage));
            result = result.replace("{total_pages}", String.valueOf(totalPages));
            result = result.replace("{slot}", String.valueOf(node.getSlot()));

            if (node instanceof CustomCategory) {
                CustomCategory cat = (CustomCategory) node;
                result = result.replace("{children_count}", String.valueOf(cat.getChildrenCount()));
                result = result.replace("{items_count}", String.valueOf(cat.getDirectItemsCount()));
                result = result.replace("{total_items_count}", String.valueOf(cat.getTotalItemsCount()));
            }

            resolved.add(result);
        }
        return resolved;
    }
}
