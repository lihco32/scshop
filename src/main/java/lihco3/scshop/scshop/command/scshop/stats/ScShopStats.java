package lihco3.scshop.scshop.command.scshop.stats;

import lihco3.scshop.scshop.config.currency.CurrencyConfig;
import lihco3.scshop.scshop.config.currency.CurrencyStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScShopStats {
    public static void run(@NotNull CommandSender sender) {
        if(!(sender instanceof Player player)) return;

        var currencies = CurrencyConfig.listCurrencyKeys();

        sender.sendMessage(player.getName() + " stats:");
        for (String currency : currencies) {
            var balance = CurrencyStorage.getBalance(player.getUniqueId(), currency);
            sender.sendMessage(currency + " - " + (balance == null ? 0 : balance));
        }
    }
}
