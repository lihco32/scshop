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
import java.util.List;

public class CurrencyConfig {
    private static FileConfiguration config;

    static {
        loadCurrencyConfig();
    }

    private static void loadCurrencyConfig() {
        final Plugin plugin = Scshop.getInstance();
        File currencyConfigFile = new File(plugin.getDataFolder(), "currency_config.yml");

        if(currencyConfigFile.exists()) {
            config = YamlConfiguration.loadConfiguration(currencyConfigFile);
        }

        // Default config
        config = new YamlConfiguration();
        setDefaultCurrencyConfig();
        try {
            config.save(currencyConfigFile);
            plugin.getLogger().warning("SCShop saved default currency config");
        } catch (IOException e) {
            plugin.getLogger().warning("SCShop failed to save default config");
        }
    }

    private static void setDefaultCurrencyConfig() {
        config.set("currencyitem1.id", "DIAMOND");
        config.set("currencyitem1.custom_model_data", 1);
        config.set("currencyitem1.name", "<!i><gradient:#DCE5F1:#8DA3DF>Diamond</gradient>");
        config.set("currencyitem1.lore", List.of("<!i><gray>This is a special item</gray>", "<!i><gradient:#DCE5F1:#8DA3DF>Very valuable</gradient>"));

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

        @Nullable String name = config.getString(currencyKey + ".name");
        @Nullable List<String> lore = config.getStringList(currencyKey + ".lore");

        return new CurrencyData(
                currencyKey,
                materialName,
                customModelData == 0 ? null : customModelData,
                name,
                lore
        );
    }

    static public @NotNull String[] listCurrencyKeys() {
        return config.getKeys(false).toArray(String[]::new);
    }
}
