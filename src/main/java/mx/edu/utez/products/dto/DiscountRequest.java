package mx.edu.utez.products.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DiscountRequest(

        @NotNull
        @Min(1)
        @Max(100)
        Double percentage
) {
}
