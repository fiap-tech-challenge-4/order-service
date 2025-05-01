package br.com.order.app.usecases;

import br.com.order.app.entities.Order;
import br.com.order.app.exception.BusinessException;
import br.com.order.app.repositories.OrderRepository;
import br.com.order.enums.OrderStatus;
import br.com.order.webui.dtos.ItemDTO;
import br.com.order.webui.dtos.OrderDTO;
import br.com.order.webui.dtos.response.OrderResponse;
import br.com.order.webui.dtos.response.PaginationResponse;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.order.enums.OrderStatus.RECEIVED;
import static br.com.order.errors.Errors.ORDER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderUseCaseTest {

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private OrderUseCase orderUseCase;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private OrderDTO getOrderDTO() {
    return OrderDTO.builder()
      .clientId("client123")
      .items(List.of(ItemDTO.builder()
        .productId(1L)
        .quantity(2)
        .value(BigDecimal.valueOf(10.0))
        .build()))
      .additionalInfo("obs")
      .status(RECEIVED)
      .build();
  }

  private Order getOrderEntity() {
    return Order.builder()
      .id(new ObjectId())
      .clientId("client123")
      .date(LocalDateTime.now())
      .status(RECEIVED)
      .totalValue(BigDecimal.valueOf(20.0))
      .items(List.of())
      .build();
  }

  @Test
  void shouldCreateOrder() {
    var dto = getOrderDTO();
    var order = getOrderEntity();

    when(orderRepository.save(any()))
      .thenReturn(order);

    OrderResponse response = orderUseCase.createOrder(dto);

    assertThat(response).isNotNull();
    assertThat(response.getClientId()).isEqualTo("client123");

    verify(orderRepository).save(any());
  }

  @Test
  void shouldFindOrderById() {
    var order = getOrderEntity();
    when(orderRepository.findById(any())).thenReturn(Optional.of(order));

    var result = orderUseCase.findOrderById(order.getId().toHexString());

    assertThat(result.getClientId()).isEqualTo(order.getClientId());
  }

  @Test
  void shouldThrowWhenOrderNotFound() {
    when(orderRepository.findById(any())).thenReturn(Optional.empty());

    String fakeId = new ObjectId().toHexString();

    assertThatThrownBy(() -> orderUseCase.findOrderById(fakeId))
      .isInstanceOf(BusinessException.class)
      .hasMessageContaining(ORDER_NOT_FOUND);
  }

  @Test
  void shouldUpdateOrderStatus() {
    var order = getOrderEntity();
    order.setStatus(OrderStatus.PREPARING);
    var updated = order.toBuilder().status(OrderStatus.READY).build();

    when(orderRepository.findById(any())).thenReturn(Optional.of(order));
    when(orderRepository.save(any())).thenReturn(updated);

    var response = orderUseCase.updateOrderStatus(order.getId().toHexString());

    assertThat(response.getStatus()).isEqualTo(OrderStatus.READY);
  }

  @Test
  void shouldListOrdersWithPagination() {
    var order = getOrderEntity();
    var pageable = PageRequest.of(0, 10);
    var page = new PageImpl<>(List.of(order), pageable, 1);

    when(orderRepository.findByStatusNotOrderByDateDesc(eq("COMPLETED"), any()))
      .thenReturn(page);

    PaginationResponse<OrderResponse> response = orderUseCase.listOrders(1, 10);

    assertThat(response.getItems()).hasSize(1);
    assertThat(response.getPageNumber()).isEqualTo(1);
  }

  @Test
  void shouldCalculateTotalValueCorrectly() {
    List<ItemDTO> items = List.of(
      ItemDTO.builder().quantity(2).value(BigDecimal.valueOf(10)).build(),
      ItemDTO.builder().quantity(1).value(BigDecimal.valueOf(5)).build()
    );

    BigDecimal total = orderUseCase.calculateTotalValue(items);

    assertThat(total).isEqualByComparingTo("25.00");
  }
}