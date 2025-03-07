package lihco3.scshop.scshop.config.currency;


import lihco3.scshop.scshop.Scshop;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class CurrencyConfig {
    private static final FileConfiguration config;

    static {
        config = loadCurrencyConfig();
    }

    private static FileConfiguration loadCurrencyConfig() {
        final Plugin plugin = Scshop.getInstance();
        File currencyConfigFile = new File(plugin.getDataFolder(), "currency_config.yml");

        if(currencyConfigFile.exists()) {
            return YamlConfiguration.loadConfiguration(currencyConfigFile);
        }

        // Default config
        var config = new YamlConfiguration();
        setDefaultCurrencyConfig();
        try {
            config.save(currencyConfigFile);
            plugin.getLogger().warning("SCShop saved default currency config");
        } catch (IOException e) {
            plugin.getLogger().warning("SCShop failed to save default config");
        }

        return config;
    }

    private static void setDefaultCurrencyConfig() {
        config.set("currencyitem1.id", "DIAMOND");
        config.set("currencyitem1.custom_model_data", 1);

        config.set("currencyitem2.id", "COAL");
        config.set("currencyitem2.custom_model_data", 22);
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

    static public @NotNull String[] listCurrencyKeys() {
        return config.getKeys(false).toArray(String[]::new);
    }
}
