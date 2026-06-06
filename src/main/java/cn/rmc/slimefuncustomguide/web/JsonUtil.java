package cn.rmc.slimefuncustomguide.web;

import cn.rmc.slimefuncustomguide.model.*;

import java.util.List;

public final class JsonUtil {

    private JsonUtil() {}

    public static String categoriesToJson(List<CustomCategory> categories) {
        StringBuilder sb = new StringBuilder("{\"categories\":[");
        boolean first = true;
        for (CustomCategory cat : categories) {
            if (!first) sb.append(',');
            categoryToJson(cat, sb);
            first = false;
        }
        sb.append("]}");
        return sb.toString();
    }

    private static void categoryToJson(CustomCategory cat, StringBuilder sb) {
        sb.append('{');
        appendField(sb, "key", cat.getKey());
        sb.append(',');
        appendField(sb, "display", cat.getDisplay());
        sb.append(',');
        IconSource icon = cat.getIconSource();
        sb.append("\"icon\":{");
        if (icon != null) {
            appendField(sb, "type", icon.getType().name());
            sb.append(',');
            appendField(sb, "id", icon.getId());
        } else {
            appendField(sb, "type", "VANILLA");
            sb.append(',');
            appendField(sb, "id", "BOOK");
        }
        sb.append('}');
        sb.append(",\"page\":").append(cat.getPage());
        sb.append(",\"slot\":").append(cat.getSlot());
        sb.append(",\"glow\":").append(cat.isGlow());
        sb.append(",\"childrenCount\":").append(cat.getChildrenCount());
        sb.append(",\"directItemsCount\":").append(cat.getDirectItemsCount());
        sb.append(",\"totalItemsCount\":").append(cat.getTotalItemsCount());

        List<String> lore = cat.getLore();
        if (lore != null && !lore.isEmpty()) {
            sb.append(",\"lore\":[");
            boolean firstLore = true;
            for (String line : lore) {
                if (!firstLore) sb.append(',');
                appendString(sb, line);
                firstLore = false;
            }
            sb.append(']');
        }

        List<GuideTreeNode> children = cat.getChildren();
        if (children != null && !children.isEmpty()) {
            sb.append(",\"children\":[");
            boolean firstChild = true;
            for (GuideTreeNode child : children) {
                if (!firstChild) sb.append(',');
                if (child.getType() == TreeNodeType.CATEGORY) {
                    categoryToJson((CustomCategory) child, sb);
                } else if (child.getType() == TreeNodeType.ITEM) {
                    itemToJson((CustomItemEntry) child, sb);
                } else if (child.getType() == TreeNodeType.PLACEHOLDER) {
                    placeholderToJson((CustomPlaceholderEntry) child, sb);
                }
                firstChild = false;
            }
            sb.append(']');
        }

        List<GuideTreeNode> directItems = getDirectItems(children);
        if (directItems != null && !directItems.isEmpty()) {
            sb.append(",\"items\":[");
            boolean firstItem = true;
            for (GuideTreeNode node : directItems) {
                if (!firstItem) sb.append(',');
                if (node.getType() == TreeNodeType.ITEM) {
                    itemToJson((CustomItemEntry) node, sb);
                } else if (node.getType() == TreeNodeType.PLACEHOLDER) {
                    placeholderToJson((CustomPlaceholderEntry) node, sb);
                }
                firstItem = false;
            }
            sb.append(']');
        }

        sb.append('}');
    }

    private static void itemToJson(CustomItemEntry entry, StringBuilder sb) {
        sb.append("{\"type\":\"ITEM\",");
        appendField(sb, "id", entry.getSlimefunId());
        sb.append(",\"page\":").append(entry.getPage());
        sb.append(",\"slot\":").append(entry.getSlot());
        sb.append('}');
    }

    private static void placeholderToJson(CustomPlaceholderEntry entry, StringBuilder sb) {
        sb.append("{\"type\":\"PLACEHOLDER\",");
        IconSource icon = entry.getIconSource();
        sb.append("\"icon\":{");
        if (icon != null) {
            appendField(sb, "type", icon.getType().name());
            sb.append(',');
            appendField(sb, "id", icon.getId());
        }
        sb.append('}');
        if (entry.getDisplay() != null) {
            sb.append(',');
            appendField(sb, "display", entry.getDisplay());
        }
        sb.append(",\"glow\":").append(entry.isGlow());
        List<String> lore = entry.getLore();
        if (lore != null && !lore.isEmpty()) {
            sb.append(",\"lore\":[");
            boolean first = true;
            for (String line : lore) {
                if (!first) sb.append(',');
                appendString(sb, line);
                first = false;
            }
            sb.append(']');
        }
        sb.append(",\"page\":").append(entry.getPage());
        sb.append(",\"slot\":").append(entry.getSlot());
        sb.append('}');
    }

    private static List<GuideTreeNode> getDirectItems(List<GuideTreeNode> children) {
        if (children == null) return null;
        java.util.List<GuideTreeNode> items = new java.util.ArrayList<>();
        for (GuideTreeNode child : children) {
            if (child.getType() != TreeNodeType.CATEGORY) {
                items.add(child);
            }
        }
        return items.isEmpty() ? null : items;
    }

    private static void appendField(StringBuilder sb, String key, String value) {
        sb.append('"').append(escapeJson(key)).append("\":\"");
        if (value != null) sb.append(escapeJson(value));
        sb.append('"');
    }

    private static void appendString(StringBuilder sb, String value) {
        sb.append('"');
        if (value != null) sb.append(escapeJson(value));
        sb.append('"');
    }

    public static String escapeJson(String s) {
        if (s == null) return "";
        StringBuilder out = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"':  out.append("\\\""); break;
                case '\\': out.append("\\\\"); break;
                case '\n': out.append("\\n"); break;
                case '\r': out.append("\\r"); break;
                case '\t': out.append("\\t"); break;
                default:   out.append(c);
            }
        }
        return out.toString();
    }
}
