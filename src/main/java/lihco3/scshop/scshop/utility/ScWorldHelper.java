package lihco3.scshop.scshop.utility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ScWorldHelper {
    public static @Nullable Location getSenderLocation(@NotNull CommandSender sender) {
        if(sender instanceof Player) {
            return ((Player) sender).getLocation();
        }
        if(sender instanceof BlockCommandSender) {
            return ((BlockCommandSender) sender).getBlock().getLocation();
        }
        return null;
    }

    public static @Nullable World getSenderWorld(@NotNull CommandSender sender) {
        if(sender instanceof Player) {
            return ((Player) sender).getWorld();
        }
        if(sender instanceof BlockCommandSender) {
            return ((BlockCommandSender) sender).getBlock().getWorld();
        }
        return null;
    }

    public static @Nullable Container getContainerChest(@NotNull Block block) {
        if(!(block.getState() instanceof Container)) {
            return null;
        }
        if(block.getType() == Material.CHEST || block.getType() == Material.BARREL) {
            return (Container) block.getState();
        }

        return null;
    }
}
