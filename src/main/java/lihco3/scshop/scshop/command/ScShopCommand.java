package lihco3.scshop.scshop.command;

import lihco3.scshop.scshop.config.CurrencyConfig;
import lihco3.scshop.scshop.utility.ScWorldHelper;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ScShopCommand implements CommandExecutor {
    static final int middleItemSlot = 13;
    static final int currencyItemSlot = 22;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        // 1. Parse data
        ScShopCommandData data;
        try {
            data = ScShopCommandData.fromArgs(sender, args);
        }
        catch(Exception e) {
            sender.sendMessage("Failed to parse /scshop command data: " + e);
            return true;
        }
        //sender.sendMessage(data.toString());

        // 2. Get container
        World world = ScWorldHelper.getSenderWorld(sender);
        if(world == null) {
            sender.sendMessage("Failed to get sender's world");
            return true;
        }
        var block = world.getBlockAt(data.posX, data.posY, data.posZ);
        var container = ScWorldHelper.getContainerChest(block);
        if(container== null) {
            sender.sendMessage("Failed to get container at provided coordinates");
            return true;
        }

        // 3. Get items
        var inventory = container.getInventory();
        var middleItem = inventory.getItem(middleItemSlot);
        var currencyItem = inventory.getItem(currencyItemSlot);
        if(middleItem == null) {
            sender.sendMessage("No item found to transform");
            return true;
        }

        // 4. Transform items
        // Stop if item shouldn't be transformed
        var itemId = middleItem.getType().getKey().getKey();
        if(!data.itemIds.contains(itemId)) {
            sender.sendMessage("Wrong item provided for transformation");
            return true;
        }

        // 4.1. Get custom model data
        int index = data.itemIds.indexOf(itemId);
        if (index < 0 || index >= data.customModels.size()) {
            sender.sendMessage("Invalid item transformation data.");
            return true;
        }
        int customModelData = data.customModels.get(index);

        // Just modify the item if no currency is involved
        if(data.currency == null) {
            var meta = middleItem.getItemMeta();
            if(meta == null) {
                sender.sendMessage("Failed to modify item.");
                return true;
            }

            meta.setCustomModelData(customModelData);
            middleItem.setItemMeta(meta);
            sender.sendMessage("Item transformed successfully!");
            return true;
        }

        if(currencyItem == null) {
            sender.sendMessage("No currency item provided");
            return true;
        }

        // Handle currency and modifications
        if(data.currencyAmount == null || data.currencyReceiver == null) {
            sender.sendMessage("Did not receive full currency data");
            return true;
        }

        // Find currency in config
        var configCurrency = CurrencyConfig.getCurrencyItem(data.currency);
        if(configCurrency == null) {
            sender.sendMessage("Did not find a corresponding currency");
            return true;
        }

        // Check id
        var playersCurrencyId = currencyItem.getType().getKey().getKey().toUpperCase();
        if(!configCurrency.itemId.equals(playersCurrencyId)) {
            sender.sendMessage("Currency id doesn't match " + configCurrency.itemId);
            return true;
        }

        // Check custom model data
        //TODO: handle currency with no cmd
        //TODO: REFACTOR
        if(configCurrency.customModelData != null) {
            if(!currencyItem.getItemMeta().hasCustomModelData()) {
                sender.sendMessage("Currency has no custom model data");
                return true;
            }
            if(configCurrency.customModelData != currencyItem.getItemMeta().getCustomModelData()) {
                sender.sendMessage("Wrong currency custom model data");
                return true;
            }
        }

        // Check amount
        if(data.currencyAmount != currencyItem.getAmount()) {
            sender.sendMessage("Wrong currency amount");
            return true;
        }

        // Check receiver
        if(data.currencyReceiver == null) {
            sender.sendMessage("No currency receiver");
            return true;
        }

        //TODO: Save currency data to receiver

        // Delete currency item
        inventory.clear(currencyItemSlot);

        // Modify item
        var meta = middleItem.getItemMeta();
        if(meta == null) {
            sender.sendMessage("Failed to modify item.");
            return true;
        }

        meta.setCustomModelData(customModelData);
        middleItem.setItemMeta(meta);
        sender.sendMessage("Item transformed successfully!");

        return true;
    }
}
