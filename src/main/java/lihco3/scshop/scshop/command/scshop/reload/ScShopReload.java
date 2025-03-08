package lihco3.scshop.scshop.command.scshop.reload;

import lihco3.scshop.scshop.config.currency.CurrencyConfig;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ScShopReload {
    public static void run(@NotNull CommandSender sender) {
        CurrencyConfig.loadCurrencyConfig();
        sender.sendMessage("Reloaded ScShop config");
    }
}
