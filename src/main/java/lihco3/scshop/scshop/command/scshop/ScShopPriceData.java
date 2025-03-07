package lihco3.scshop.scshop.command.scshop;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class ScShopPriceData {
    @NotNull
    String currency;
    @NotNull
    Integer amount;
    @NotNull
    String receiver;

    public ScShopPriceData(
            @NotNull String currency,
            @NotNull Integer amount,
            @NotNull String receiver
    ) {
        this.currency = Objects.requireNonNull(currency, "currency can't be null");
        this.amount = Objects.requireNonNull(amount, "currencyAmount can't be null");
        this.receiver = Objects.requireNonNull(receiver, "currencyReceiver can't be null");
    }
}
