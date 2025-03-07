package lihco3.scshop.scshop;

import lihco3.scshop.scshop.command.scshop.ScShopCommand;
import org.bukkit.plugin.java.JavaPlugin;


public final class Scshop extends JavaPlugin {
    @Override
    public void onEnable() {
        var scShopCommand =  getCommand("scshop");
        if(scShopCommand != null) {
            scShopCommand.setExecutor(new ScShopCommand());
        }

        getLogger().info("SCShop Enabled");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Scshop getInstance() {
        return getPlugin(Scshop.class);
    }
}
