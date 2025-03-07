package lihco3.scshop.scshop.command.scshop.transform;

import lihco3.scshop.scshop.config.currency.CurrencyStorage;
import lihco3.scshop.scshop.utility.ScWorldHelper;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ScShopTransform {
    static final int middleItemSlot = 13;
    static final int currencyItemSlot = 22;

    public static void run(@NotNull CommandSender sender, @NotNull String[] args) {
        // 1. Parse data
        TransformCommandData data;
        try {
            data = TransformCommandData.fromArgs(sender, args);
        }
        catch(Exception e) {
            sender.sendMessage("Failed to parse /scshop command data: " + e);
            return;
        }
        //sender.sendMessage(data.toString());

        // 2. Get container
        World world = ScWorldHelper.getSenderWorld(sender);
        if(world == null) {
            sender.sendMessage("Failed to get sender's world");
            return;
        }
        var block = world.getBlockAt(data.posX, data.posY, data.posZ);
        var container = ScWorldHelper.getContainerChest(block);
        if(container== null) {
            sender.sendMessage("Failed to get container at provided coordinates");
            return;
        }

        // 3. Get items
        var inventory = container.getInventory();
        var middleItem = inventory.getItem(middleItemSlot);
        var currencyItem = inventory.getItem(currencyItemSlot);
        if(middleItem == null) {
            sender.sendMessage("No item found to transform");
            return;
        }

        // 3.1. Get item id
        var itemId = middleItem.getType().getKey().getKey();
        if(!data.itemIds.contains(itemId)) {
            sender.sendMessage("Wrong item provided for transformation");
            return;
        }

        // 3.2. Get custom model data
        int index = data.itemIds.indexOf(itemId);
        if (index < 0 || index >= data.customModels.size()) {
            sender.sendMessage("Invalid item transformation data.");
            return;
        }
        int customModelData = data.customModels.get(index);

        // 4. Validate price if necessary
        if(data.price != null) {
            boolean response;
            try {
                response = data.validatePaymentItem(currencyItem);
            } catch (Exception e) {
                sender.sendMessage(e.toString());
                return;
            }

            if(!response) {
                sender.sendMessage("Failed price item validation");
                return;
            }
        }

        // 5. Transform middle item
        var meta = middleItem.getItemMeta();
        if(meta == null) {
            sender.sendMessage("Failed to modify item.");
            return;
        }

        meta.setCustomModelData(customModelData);
        middleItem.setItemMeta(meta);

        // 6. Delete currency item if necessary
        if(data.price != null && currencyItem != null) {
            inventory.clear(currencyItemSlot);
        }

        // 7. Save currency to receiver if necessary
        if(data.price != null) {
            var playerId = CurrencyStorage.ensurePlayerExists(data.price.receiver);
            CurrencyStorage.addBalance(playerId, data.price.currency, data.price.amount);
        }

        sender.sendMessage("Item transformed successfully!");

        return;
    }
}
