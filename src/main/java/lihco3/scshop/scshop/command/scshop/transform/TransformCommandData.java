package lihco3.scshop.scshop.command.scshop.transform;

import lihco3.scshop.scshop.config.currency.CurrencyConfig;
import lihco3.scshop.scshop.utility.ScCommandParser;
import lihco3.scshop.scshop.utility.ScWorldHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TransformCommandData {
    @NotNull
    Integer posX;
    @NotNull
    Integer posY;
    @NotNull
    Integer posZ;
    @NotNull
    List<String> itemIds;
    @NotNull
    List<Integer> customModels;

    @Nullable
    TransformPriceData price;

    @Override
    public String toString() {
        return "ScShopCommandData{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", posZ=" + posZ +
                ", itemIds=" + itemIds +
                ", customModels=" + customModels +
                ", currency=" + (price == null ? "null" : price.currency) +
                ", currencyAmount=" + (price == null ? "null" : price.amount) +
                ", currencyReceiver=" + (price == null ? "null" : price.receiver) +
                '}';
    }

    public TransformCommandData(@NotNull Integer posX,
                                @NotNull Integer posY,
                                @NotNull Integer posZ,
                                @NotNull List<String> itemIds,
                                @NotNull List<Integer> customModels,
                                @Nullable String currency,
                                @Nullable Integer currencyAmount,
                                @Nullable String currencyReceiver) {
        this.posX = Objects.requireNonNull(posX, "posX cannot be null");
        this.posY = Objects.requireNonNull(posY, "posY cannot be null");
        this.posZ = Objects.requireNonNull(posZ, "posZ cannot be null");
        this.itemIds = Objects.requireNonNull(itemIds, "itemIds cannot be null");
        this.customModels = Objects.requireNonNull(customModels, "customModels cannot be null");

        if(currency == null || currencyAmount == null || currencyReceiver == null) {
            this.price = null;
        }
        else {
            this.price = new TransformPriceData(currency, currencyAmount, currencyReceiver);
        }
    }

    // Constructor from args
    public static @NotNull TransformCommandData fromArgs(@NotNull CommandSender sender, @NotNull String[] args) throws Exception {
        if(args.length != 5 && args.length != 8) {
            throw new Exception("Wrong number of arguments");
        }

        var location = ScWorldHelper.getSenderLocation(sender);
        if(location == null) {
            throw new Exception("Failed to get location");
        }

        // 1. Get position
        var posX = ScCommandParser.parseCoordinate(location.getBlockX(), args[0]);
        var posY = ScCommandParser.parseCoordinate(location.getBlockY(), args[1]);
        var posZ = ScCommandParser.parseCoordinate(location.getBlockZ(), args[2]);
        if(posX == null || posY == null || posZ == null) {
            throw new Exception("Failed to parse coordinates");
        }

        // 2. Get item ids
        var listOfItems = ScCommandParser.parseListOfStrings(args[3]);
        if(listOfItems == null) {
            throw new Exception("Failed to parse list");
        }

        // 3. Get custom model data list
        var listOfCustomModels = ScCommandParser.parseListOfIntegers(args[4]);
        if(listOfCustomModels == null) {
            var singleId = ScCommandParser.parseIntOrNull(args[4]);
            if(singleId == null) {
                throw new Exception("Failed to parse custom model data");
            }
            listOfCustomModels = new ArrayList<>(Collections.nCopies(listOfItems.size(), singleId));
        }

        // 3.5 Make sure the length is the same
        if(listOfItems.size() != listOfCustomModels.size()) {
            throw new Exception("Failed to parse data");
        }

        // 4. Get currency item
        @Nullable String currencyItem = null;
        if(args.length >= 6) {
            currencyItem = args[5];
        }

        // 5. Get currency
        @Nullable Integer currencyAmount = null;
        if(args.length >= 7) {
            currencyAmount = ScCommandParser.parseIntOrNull(args[6]);
        }

        // 6. Get currency receiver
        @Nullable String currencyReceiver = null;
        if(args.length == 8) {
            currencyReceiver = args[7];
        }

        return new TransformCommandData(
                posX,
                posY,
                posZ,
                listOfItems,
                listOfCustomModels,
                currencyItem,
                currencyAmount,
                currencyReceiver
        );
    }

    // Returns true if user's payment item matches the ones required by command
    public @NotNull Boolean validatePaymentItem(@Nullable ItemStack playerItem) throws Exception {
        if(price == null) throw new Exception("No price is required");
        if(playerItem == null) throw new Exception("No currency item provided");

        // 1. Find currency in config
        var configCurrency = CurrencyConfig.getCurrencyItem(price.currency);
        if(configCurrency == null) throw new Exception("Did not find a corresponding currency");

        // 2. Verify currency id
        var playersCurrencyId = playerItem.getType().getKey().getKey().toUpperCase();
        if(!configCurrency.itemId.equals(playersCurrencyId)) {
            throw new Exception("Currency id doesn't match " + configCurrency.itemId);
        }

        // 3. Verify amount
        if(price.amount != playerItem.getAmount()) throw new Exception("Wrong currency amount");

        // 4. Verify custom model data
        if(configCurrency.customModelData != null) {
            if(!playerItem.getItemMeta().hasCustomModelData()) {
                throw new Exception("Wrong currency custom model data");
            }
            if(playerItem.getItemMeta().getCustomModelData() != configCurrency.customModelData) {
                throw new Exception("Wrong currency custom model data");
            }
        }

        return true;
    }
}
