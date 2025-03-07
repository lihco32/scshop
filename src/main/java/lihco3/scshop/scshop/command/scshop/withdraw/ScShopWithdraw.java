package lihco3.scshop.scshop.command.scshop.withdraw;

import lihco3.scshop.scshop.config.currency.CurrencyConfig;
import lihco3.scshop.scshop.config.currency.CurrencyStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScShopWithdraw {
    public static void run(@NotNull CommandSender sender) {
        if(!(sender instanceof Player player)) return;

        var currencies = CurrencyConfig.listCurrencyKeys();

        for(String currency : currencies) {
            var balance = CurrencyStorage.getBalance(player.getUniqueId(), currency);
            if(balance == null || balance == 0) continue;

            var data = CurrencyConfig.getCurrencyItem(currency);
            if(data == null) continue;

            var item = data.getItemStack(balance);
            if(item == null) continue;

            player.getWorld().dropItem(player.getLocation(), item);
            CurrencyStorage.setBalance(player.getUniqueId(), currency, 0);
        }
    }
}
