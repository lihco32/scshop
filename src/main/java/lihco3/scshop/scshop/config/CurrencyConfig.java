package lihco3.scshop.scshop.config;


import lihco3.scshop.scshop.Scshop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class CurrencyConfig {
    private static final FileConfiguration config;

    static {
        config = Scshop.getInstance().getCurrencyConfig();
    }

    static public @Nullable CurrencyData getCurrencyItem(String currencyKey) {
        if (!config.contains(currencyKey)) return null;

        String materialName = config.getString(currencyKey + ".id");
        if(materialName == null) return null;

        int customModelData = config.getInt(currencyKey + ".custom_model_data", 0);

        Material material = Material.matchMaterial(materialName);
        if (material == null) return null;

        return new CurrencyData(
                currencyKey,
                materialName,
                customModelData == 0 ? null : customModelData
        );
    }
}
