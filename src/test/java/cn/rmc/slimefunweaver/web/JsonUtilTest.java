package cn.rmc.slimefunweaver.web;

import cn.rmc.slimefunweaver.model.CustomCategory;
import cn.rmc.slimefunweaver.model.IconSource;
import cn.rmc.slimefunweaver.model.IconType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonUtilTest {

    @Test
    public void allowsSameCategoryKeyInDifferentBranches() {
        CustomCategory first = new CustomCategory("same", "A", new IconSource(IconType.VANILLA, "BOOK"), Collections.<String>emptyList(), 1, 0, false);
        CustomCategory second = new CustomCategory("same", "B", new IconSource(IconType.VANILLA, "BOOK"), Collections.<String>emptyList(), 1, 1, false);

        String json = JsonUtil.categoriesToJson(Arrays.asList(first, second));

        assertFalse(json.contains("_cycle"));
        assertTrue(json.contains("\"display\":\"A\""));
        assertTrue(json.contains("\"display\":\"B\""));
    }
}
