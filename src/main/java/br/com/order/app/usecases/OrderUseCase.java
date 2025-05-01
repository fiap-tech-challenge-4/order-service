package br.com.order.app.usecases;

import br.com.order.app.entities.Order;
import br.com.order.app.exception.BusinessException;
import br.com.order.app.repositories.OrderRepository;
import br.com.order.utils.Pagination;
import br.com.order.webui.dtos.ItemDTO;
import br.com.order.webui.dtos.OrderDTO;
import br.com.order.webui.dtos.response.OrderResponse;
import br.com.order.webui.dtos.response.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static br.com.order.enums.OrderStatus.COMPLETED;
import static br.com.order.errors.Errors.ORDER_NOT_FOUND;
import static br.com.order.webui.converters.OrderConverter.toOrderEntity;
import static br.com.order.webui.converters.OrderConverter.toOrderResponse;
import static br.com.order.webui.converters.OrderConverter.toOrderResponseList;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class OrderUseCase {

  private final OrderRepository orderRepository;

  public OrderResponse createOrder(OrderDTO orderDTO) {
    var order = toOrderEntity(orderDTO);
    order.setTotalValue(calculateTotalValue(orderDTO.getItems()));

    var orderResponse = orderRepository.save(order);

    return toOrderResponse(orderResponse);
  }

  public OrderResponse findOrderById(String id) {
    var entity = getOrder(id);
    return toOrderResponse(entity);
  }

  public OrderResponse updateOrderStatus(String id) {
    var order = getOrder(id);
    order.setStatus(order.getNextStatus());
    return toOrderResponse(orderRepository.save(order));
  }

  public PaginationResponse<OrderResponse> listOrders(Integer page, Integer limit) {
    var pageable = Pagination.getPageRequest(limit, page, "DESC", "id");
    var orders = orderRepository.findByStatusNotOrderByDateDesc(COMPLETED.name(), pageable);
    return toOrderResponseList(orders);
  }

  private Order getOrder(String id) {
    return orderRepository.findById(new ObjectId(id))
      .orElseThrow(() -> new BusinessException(ORDER_NOT_FOUND, UNPROCESSABLE_ENTITY));
  }

  public BigDecimal calculateTotalValue(List<ItemDTO> items) {
    return items.stream()
      .map(item -> item.getValue().multiply(BigDecimal.valueOf(item.getQuantity())))
      .reduce(BigDecimal.ZERO, BigDecimal::add)
      .setScale(2, RoundingMode.HALF_UP);
  }

}
