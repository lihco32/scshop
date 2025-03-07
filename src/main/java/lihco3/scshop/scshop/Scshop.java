package lihco3.scshop.scshop;

import lihco3.scshop.scshop.command.ScShopCommand;
import lihco3.scshop.scshop.config.CurrencyConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import java.io.IOException;

public final class Scshop extends JavaPlugin {
    private FileConfiguration currencyConfig;

    public FileConfiguration getCurrencyConfig() {
        return currencyConfig;
    }

    @Override
    public void onEnable() {
        var scShopCommand =  getCommand("scshop");
        if(scShopCommand != null) {
            scShopCommand.setExecutor(new ScShopCommand());
        }
        loadCurrencyConfig();
        getLogger().info("SCShop Enabled");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Scshop getInstance() {
        return getPlugin(Scshop.class);
    }

    private void loadCurrencyConfig() {
        File currencyConfigFile = new File(getDataFolder(), "currency_config.yml");

        if(currencyConfigFile.exists()) {
            currencyConfig = YamlConfiguration.loadConfiguration(currencyConfigFile);
            return;
        }

        // Default config
        currencyConfig = new YamlConfiguration();
        setDefaultCurrencyConfig();
        try {
            currencyConfig.save(currencyConfigFile);
            getLogger().warning("SCShop saved default config");
        } catch (IOException e) {
            getLogger().warning("SCShop failed to save default config");
        }
    }

    private void setDefaultCurrencyConfig() {
        currencyConfig.set("currencyitem1.id", "DIAMOND");
        currencyConfig.set("currencyitem1.custom_model_data", 1);

        currencyConfig.set("currencyitem2.id", "COAL");
        currencyConfig.set("currencyitem2.custom_model_data", 22);
    }
}
