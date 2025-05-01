package br.com.order.webui.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static br.com.order.webui.constants.Descriptions.ITEM_ID;
import static br.com.order.webui.constants.Descriptions.ITEM_DESCRIPTION;
import static br.com.order.webui.constants.Descriptions.ITEM_VALUE;
import static br.com.order.webui.constants.Descriptions.QUANTITY_ITEMS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OrderItemResponse {

    @Schema(description = ITEM_ID)
    private Long productId;

    @Schema(description = QUANTITY_ITEMS)
    private Integer quantity;

    @Schema(description = ITEM_VALUE)
    private BigDecimal value;

    @Schema(description = ITEM_DESCRIPTION)
    private String description;
}
