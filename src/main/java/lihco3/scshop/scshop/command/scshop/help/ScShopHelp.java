package lihco3.scshop.scshop.command.scshop.help;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ScShopHelp {
    public static void run(@NotNull CommandSender sender) {
        sender.sendMessage("ScShop Help:");
        sender.sendMessage("/scshop stats - View your current balance");
        sender.sendMessage("/scshop withdraw - Withdraw all available funds from your account");
    }
}
