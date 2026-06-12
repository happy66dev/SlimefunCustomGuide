// SPDX-License-Identifier: GPL-3.0-or-later
// Copyright (C) 2025 happy (k666kkk666k@163.com)
package cn.rmc.slimefunweaver.util;

/**
 * 颜色代码处理工具类
 */
public class ColorUtil {

    private static final String COLOR_CHARS = "0123456789abcdefABCDEFrRkKlLmMnNoO";

    /**
     * 移除文本中的所有颜色代码（同时支持 § 和 & 两种格式，包括 &x###### 6位十六进制）
     * 只处理合法的颜色代码格式，不影响名字中普通的 & 字符（如 "AT&T"）
     *
     * @param text 原始文本
     * @return 去除颜色代码后的纯文本
     */
    public static String stripColorCodes(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder out = new StringBuilder(text.length());
        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);
            if ((c == '\u00a7' || c == '&') && i + 1 < text.length()) {
                char next = text.charAt(i + 1);

                // 处理 6 位十六进制颜色：&x&r&r&g&g&b&b（共 14 个字符）
                if ((next == 'x' || next == 'X') && i + 13 < text.length()) {
                    boolean validHex = true;
                    for (int h = 2; h <= 13; h += 2) {
                        if (text.charAt(i + h) != c) { validHex = false; break; }
                        char hex = text.charAt(i + h + 1);
                        if ((hex < '0' || hex > '9') && (hex < 'a' || hex > 'f') && (hex < 'A' || hex > 'F')) {
                            validHex = false; break;
                        }
                    }
                    if (validHex) { i += 14; continue; }
                }

                // 处理普通颜色代码：&0-9a-fA-Frklmno
                if (COLOR_CHARS.indexOf(next) >= 0) {
                    i += 2;
                    continue;
                }
            }
            out.append(c);
            i++;
        }

        return out.toString();
    }
}
