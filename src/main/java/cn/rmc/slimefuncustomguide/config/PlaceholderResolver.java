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
