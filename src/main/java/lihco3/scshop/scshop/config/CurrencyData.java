package lihco3.scshop.scshop.config;

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
}
