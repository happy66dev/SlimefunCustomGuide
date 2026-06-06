package cn.rmc.slimefuncustomguide.settings;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class CustomGuideSettings {

    private static final CustomGuideModeOption modeOption = new CustomGuideModeOption();
    private static final int[] BG = {
        1, 2, 3, 4, 5, 6, 7, 8,
        9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35,
        36, 37, 38, 39, 40, 41, 42, 43, 44,
        45, 46, 47, 48, 50, 51, 52, 53
    };

    private CustomGuideSettings() {}

    public static void openSettings(Player p, ItemStack guide) {
        ChestMenu menu = new ChestMenu("\u81ea\u5b9a\u4e49\u6307\u5357\u8bbe\u7f6e");
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
                ChatColor.GRAY + "\u6307\u5357\u5206\u7c7b\u8bbe\u7f6e",
                "",
                ChatColor.YELLOW + "\u5728\u6b64\u5207\u6362\u81ea\u5b9a\u4e49\u5206\u7c7b\u6837\u5f0f"
        ));
        menu.addMenuClickHandler(4, ChestMenuUtils.getEmptyClickHandler());

        int optionSlot = 22;
        ItemStack modeItem = modeOption.getDisplayItem(p, guide).orElse(new ItemStack(org.bukkit.Material.BARRIER));
        menu.addItem(optionSlot, modeItem);
        menu.addMenuClickHandler(optionSlot, (pl, s, is, action) -> {
            modeOption.onClick(pl, guide);
            return false;
        });

        menu.open(p);
    }
}
