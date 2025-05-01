package br.com.order.webui.dtos;

import br.com.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.order.enums.OrderStatus.RECEIVED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private ObjectId id;

    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    private String clientId;

    @Builder.Default
    private OrderStatus status = RECEIVED;

    @Builder.Default
    private List<ItemDTO> items = new ArrayList<>();

    private String additionalInfo;

}
