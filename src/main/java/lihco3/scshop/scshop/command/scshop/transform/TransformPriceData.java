package lihco3.scshop.scshop.command.scshop.transform;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class TransformPriceData {
    @NotNull
    String currency;
    @NotNull
    Integer amount;
    @NotNull
    String receiver;

    public TransformPriceData(
            @NotNull String currency,
            @NotNull Integer amount,
            @NotNull String receiver
    ) {
        this.currency = Objects.requireNonNull(currency, "currency can't be null");
        this.amount = Objects.requireNonNull(amount, "currencyAmount can't be null");
        this.receiver = Objects.requireNonNull(receiver, "currencyReceiver can't be null");
    }
}
