package lihco3.scshop.scshop.config.currency;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CurrencyData {
    @NotNull
    public String ymlId;

    @NotNull
    public String itemId;

    @Nullable
    public Integer customModelData;

    public CurrencyData(
            @NotNull String ymlId,
            @NotNull String itemId,
            @Nullable Integer customModelData) {
        this.ymlId = ymlId;
        this.itemId = itemId;
        this.customModelData = customModelData;
    }

    public @Nullable ItemStack getItemStack(int amount) {
        Material material = Material.matchMaterial(itemId);
        if (material == null)  return null;

        ItemStack item = new ItemStack(material);
        item.setAmount(amount);

        if (customModelData != null) {
            var meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(customModelData);
                //meta.displayName(Component.text(""));
                item.setItemMeta(meta);
            }
        }

        return item;
    }
}
