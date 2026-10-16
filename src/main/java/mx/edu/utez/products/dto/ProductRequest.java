package mx.edu.utez.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import mx.edu.utez.products.model.Category;

public record ProductRequest(
        @NotBlank // Validacion
        String name,

        @NotNull
        @Positive
        Double price,

        @NotNull
        @PositiveOrZero
        int stock,

        @NotNull
        Long categoryId
) {
}
