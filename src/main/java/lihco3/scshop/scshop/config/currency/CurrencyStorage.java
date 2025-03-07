package lihco3.scshop.scshop.config.currency;

import lihco3.scshop.scshop.Scshop;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CurrencyStorage {
    private static final FileConfiguration storage;
    private static final File storageFile;

    static {
        storageFile = new File(Scshop.getInstance().getDataFolder(), "currency_storage.yml");
        storage = loadStorage();
    }

    private static FileConfiguration loadStorage() {
        final Plugin plugin = Scshop.getInstance();
        File configFile = new File(plugin.getDataFolder(), "currency_storage.yml");

        if(configFile.exists()) {
            return YamlConfiguration.loadConfiguration(configFile);
        }

        // Default empty storage
        var config = new YamlConfiguration();
        try {
            config.save(configFile);
            plugin.getLogger().warning("SCShop created a new currency storage");
        } catch (IOException e) {
            plugin.getLogger().warning("SCShop failed to save default config");
        }

        return config;
    }

    private static UUID getPlayerUuid(String playerName) {
        return Bukkit.getOfflinePlayer(playerName).getUniqueId();
    }

    public static UUID ensurePlayerExists(String playerName) {
        var playerId = getPlayerUuid(playerName);
        String path = "players." + playerId;

        if (!storage.contains(path)) {
            storage.set(path + ".nickname", playerName);
            saveStorage();
        }

        return playerId;
    }

    // Get player's balance for a specific currency
    public static @Nullable Integer getBalance(UUID playerId, String currencyType) {
        var currency = CurrencyConfig.getCurrencyItem(currencyType);
        if(currency == null) return null;

        return storage.getInt("players." + playerId + "." + currencyType, 0);
    }

    // Set player's balance for a specific currency
    public static Boolean setBalance(UUID playerId, String currencyType, int amount) {
        var currency = CurrencyConfig.getCurrencyItem(currencyType);
        if(currency == null) return false;

        storage.set("players." + playerId + "." + currencyType, amount);
        saveStorage();
        return true;
    }

    // Add to player's balance
    public static Boolean addBalance(UUID playerId, String currencyType, int amount) {
        var balance = getBalance(playerId, currencyType);
        if(balance == null) return false;

        setBalance(playerId, currencyType, balance + amount);
        return true;
    }

    // Clear player's balance
    public static @Nullable Integer clearBalance(UUID playerId, String currencyType, int amount) {
        var balance = getBalance(playerId, currencyType);
        if(balance == null) return null;

        setBalance(playerId, currencyType, 0);
        return balance;
    }

    // Save file
    private static Boolean saveStorage() {
        try {
            storage.save(storageFile);
            return true;
        } catch (IOException e) {
            Scshop.getInstance().getLogger().warning("ScShop Failed to save currency storage: " + e);
            return false;
        }
    }
}