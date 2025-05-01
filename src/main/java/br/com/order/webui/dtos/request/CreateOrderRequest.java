package br.com.order.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static br.com.order.errors.Errors.FIELD_IS_REQUIRED;
import static br.com.order.errors.Errors.ITEMS_LIST_SHOULD_HAVE_AT_LEAST_ONE_ITEM;
import static br.com.order.webui.constants.Descriptions.ADDITIONAL_INFO;
import static br.com.order.webui.constants.Descriptions.CLIENT_ID;
import static br.com.order.webui.constants.Descriptions.ITEMS_LIST;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @Schema(description = CLIENT_ID)
    private String clientId;

    @Valid
    @Schema(description = ITEMS_LIST)
    @NotNull(message = FIELD_IS_REQUIRED)
    @Size(min = 1, message = ITEMS_LIST_SHOULD_HAVE_AT_LEAST_ONE_ITEM)
    private List<ItemRequest> items;

    @Schema(description = ADDITIONAL_INFO)
    private String additionalInfo;
}
