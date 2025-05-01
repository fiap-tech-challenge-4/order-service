package br.com.order.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static br.com.order.errors.Errors.FIELD_IS_REQUIRED;
import static br.com.order.webui.constants.Descriptions.ITEM_DESCRIPTION;
import static br.com.order.webui.constants.Descriptions.ITEM_ID;
import static br.com.order.webui.constants.Descriptions.ITEM_VALUE;
import static br.com.order.webui.constants.Descriptions.QUANTITY_ITEMS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    @Schema(description = ITEM_ID)
    @NotNull(message = FIELD_IS_REQUIRED)
    private Long productId;

    @Schema(description = ITEM_DESCRIPTION)
    @NotBlank(message = FIELD_IS_REQUIRED)
    private String description;

    @Schema(description = QUANTITY_ITEMS)
    @NotNull(message = FIELD_IS_REQUIRED)
    private Integer quantity;

    @Schema(description = ITEM_VALUE)
    @NotNull(message = FIELD_IS_REQUIRED)
    private BigDecimal value;

}
