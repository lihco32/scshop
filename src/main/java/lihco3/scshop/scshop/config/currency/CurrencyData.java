package lihco3.scshop.scshop.config.currency;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurrencyData {
    @NotNull
    public String ymlId;

    @NotNull
    public String itemId;

    @Nullable
    public Integer customModelData;

    @Nullable
    public String name;

    @Nullable
    public List<String> lore;

    public CurrencyData(
            @NotNull String ymlId,
            @NotNull String itemId,
            @Nullable Integer customModelData,
            @Nullable String name,
            @Nullable List<String> lore
            ) {
        this.ymlId = ymlId;
        this.itemId = itemId;
        this.customModelData = customModelData;
        this.name = name;
        this.lore = lore;
    }

    public @Nullable ItemStack getItemStack(int amount) {
        Material material = Material.matchMaterial(itemId);
        if (material == null)  return null;

        ItemStack item = new ItemStack(material);
        item.setAmount(amount);

        if (customModelData != null) {
            var meta = item.getItemMeta();
            if (meta != null) {
                // Custom Model Data
                meta.setCustomModelData(customModelData);
                var miniMessage = MiniMessage.miniMessage();

                if(name != null) {
                    var text = miniMessage.deserialize(name);
                    meta.displayName(text);
                }

                if(lore != null) {
                    meta.lore(lore.stream()
                            .map(miniMessage::deserialize)
                            .toList());
                }

                item.setItemMeta(meta);
            }
        }

        return item;
    }
}
