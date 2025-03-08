package lihco3.scshop.scshop.command.scshop;

import lihco3.scshop.scshop.command.scshop.help.ScShopHelp;
import lihco3.scshop.scshop.command.scshop.reload.ScShopReload;
import lihco3.scshop.scshop.command.scshop.stats.ScShopStats;
import lihco3.scshop.scshop.command.scshop.transform.ScShopTransform;
import lihco3.scshop.scshop.command.scshop.withdraw.ScShopWithdraw;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ScShopCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if(args.length == 0) {
            sender.sendMessage("Usage: /scshop help");
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "transform":
                if(!sender.hasPermission("scshop.transform")) {
                    sender.sendMessage( "You don't have permission to use this command.");
                    return true;
                }

                String[] subArray = Arrays.copyOfRange(args, 1, args.length);
                ScShopTransform.run(sender, subArray);
                return true;
            case "stats":
                if(!sender.hasPermission("scshop.stats")) {
                    sender.sendMessage( "You don't have permission to use this command.");
                    return true;
                }
                ScShopStats.run(sender);
                return true;
            case "help":
                if(!sender.hasPermission("scshop.help")) {
                    sender.sendMessage( "You don't have permission to use this command.");
                    return true;
                }
                ScShopHelp.run(sender);
                return true;
            case "withdraw":
                if(!sender.hasPermission("scshop.withdraw")) {
                    sender.sendMessage( "You don't have permission to use this command.");
                    return true;
                }
                ScShopWithdraw.run(sender);
                return true;
            case "reload":
                if(!sender.hasPermission("scshop.reload")) {
                    sender.sendMessage( "You don't have permission to use this command.");
                    return true;
                }
                ScShopReload.run(sender);
                return true;
        }

        sender.sendMessage("Usage: /scshop help");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender commandSender,
            @NotNull Command command,
            @NotNull String s,
            @NotNull String[] strings
    ) {
        List<String> options = List.of("help", "stats", "withdraw", "transform", "reload");
        if (strings.length == 1) {
            return options.stream()
                    .filter(option -> option.startsWith(strings[0].toLowerCase()))
                    .toList();
        }

        return List.of();
    }
}
