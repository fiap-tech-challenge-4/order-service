package br.com.order.webui.dtos.response;

import br.com.order.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static br.com.order.webui.constants.Constants.DATE_TIME_FORMAT;
import static br.com.order.webui.constants.Descriptions.ADDITIONAL_INFO;
import static br.com.order.webui.constants.Descriptions.CLIENT_ID;
import static br.com.order.webui.constants.Descriptions.ID;
import static br.com.order.webui.constants.Descriptions.ITEMS_LIST;
import static br.com.order.webui.constants.Descriptions.ORDER_DATE;
import static br.com.order.webui.constants.Descriptions.ORDER_STATUS;
import static br.com.order.webui.constants.Descriptions.VLR_TOTAL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OrderResponse {

    @Schema(description = ID)
    private String id;

    @Schema(description = ORDER_DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT, timezone = "UTC")
    private LocalDateTime date;

    @Schema(description = CLIENT_ID)
    private String clientId;

    @Schema(description = VLR_TOTAL)
    private BigDecimal totalValue;

    @Schema(description = ORDER_STATUS)
    private OrderStatus status;

    @Schema(description = ITEMS_LIST)
    private List<OrderItemResponse> items;

    @Schema(description = ADDITIONAL_INFO)
    private String additionalInfo;

}
