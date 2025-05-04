package br.com.order.webui.converters;

import br.com.order.app.entities.Order;
import br.com.order.app.entities.OrderItem;
import br.com.order.webui.dtos.ItemDTO;
import br.com.order.webui.dtos.OrderDTO;
import br.com.order.webui.dtos.request.CreateOrderRequest;
import br.com.order.webui.dtos.request.ItemRequest;
import br.com.order.webui.dtos.response.OrderItemResponse;
import br.com.order.webui.dtos.response.OrderResponse;
import br.com.order.webui.dtos.response.PaginationResponse;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderConverter {

  public static OrderDTO toOrderDTO(CreateOrderRequest request) {
    return OrderDTO.builder()
      .clientId(request.getClientId())
      .items(toItemDTOList(request.getItems()))
      .additionalInfo(request.getAdditionalInfo())
      .build();
  }

  public static Order toOrderEntity(OrderDTO dto) {
    return Order.builder()
      .id(dto.getId())
      .clientId(dto.getClientId())
      .date(dto.getDate())
      .status(dto.getStatus())
      .additionalInfo(dto.getAdditionalInfo())
      .items(toOrderItemEntityList(dto.getItems()))
      .build();
  }

  public static OrderResponse toOrderResponse(Order order) {
    return OrderResponse.builder()
      .id(order.getId() != null ? order.getId().toHexString() : null)
      .clientId(order.getClientId())
      .date(order.getDate())
      .status(order.getStatus())
      .totalValue(order.getTotalValue())
      .additionalInfo(order.getAdditionalInfo())
      .items(toOrderItemResponseList(order.getItems()))
      .build();
  }

  public static ItemDTO toItemDTO(ItemRequest request) {
    return ItemDTO.builder()
      .productId(request.getProductId())
      .quantity(request.getQuantity())
      .value(request.getValue())
      .description(request.getDescription())
      .build();
  }

  public static OrderItem toOrderItemEntity(ItemDTO dto) {
    return OrderItem.builder()
      .productId(dto.getProductId())
      .quantity(dto.getQuantity())
      .value(dto.getValue())
      .description(dto.getDescription())
      .build();
  }

  public static List<ItemDTO> toItemDTOList(List<ItemRequest> requests) {
    return requests.stream().map(OrderConverter::toItemDTO).toList();
  }

  public static List<OrderItem> toOrderItemEntityList(List<ItemDTO> dtos) {
    return dtos.stream().map(OrderConverter::toOrderItemEntity).toList();
  }

  public static List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> items) {
    return items.stream().map(item -> OrderItemResponse.builder()
      .productId(item.getProductId())
      .quantity(item.getQuantity())
      .value(item.getValue())
      .description(item.getDescription())
      .build()
    ).toList();
  }

  public static PaginationResponse<OrderResponse> toOrderResponseList(Page<Order> page) {
    return PaginationResponse.<OrderResponse>builder()
      .hasNext(!page.isLast())
      .hasPrevious(!page.isFirst())
      .pageNumber(page.getNumber() + 1)
      .pageSize(page.getSize())
      .items(page.stream()
        .map(OrderConverter::toOrderResponse)
        .toList())
      .build();
  }
}