package br.com.order.webui.controllers;

import br.com.order.app.exception.handler.ExceptionAdvice;
import br.com.order.app.usecases.OrderUseCase;
import br.com.order.webui.dtos.request.CreateOrderRequest;
import br.com.order.webui.dtos.request.ItemRequest;
import br.com.order.webui.dtos.response.OrderItemResponse;
import br.com.order.webui.dtos.response.OrderResponse;
import br.com.order.webui.dtos.response.PaginationResponse;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static br.com.order.enums.OrderStatus.COMPLETED;
import static br.com.order.enums.OrderStatus.RECEIVED;
import static br.com.order.utils.JsonUtils.toJson;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = {OrderController.class, ExceptionAdvice.class})
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderUseCase orderUseCase;

  @Test
  @DisplayName("Should create order successfully")
  void shouldCreateOrder() throws Exception {
    CreateOrderRequest request = CreateOrderRequest.builder()
      .clientId("client123")
      .items(List.of(ItemRequest.builder()
        .productId(1L)
        .description("item")
        .quantity(2)
        .value(BigDecimal.valueOf(25))
        .build()))
      .additionalInfo("Obs")
      .build();

    when(orderUseCase.createOrder(any())).thenReturn(getOrderResponse());

    mockMvc.perform(post("/api/v1/order")
        .contentType(MediaType.APPLICATION_JSON)
        .content(Objects.requireNonNull(toJson(request))))
      .andExpect(status()
        .isCreated())
      .andExpect(jsonPath("$.clientId", is("123123")));
  }

  @Test
  @DisplayName("Should find order by id")
  void shouldFindOrderById() throws Exception {
    String orderId = new ObjectId().toHexString();

    when(orderUseCase.findOrderById(orderId)).thenReturn(getOrderResponse());

    mockMvc.perform(get("/api/v1/order/" + orderId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", notNullValue()));
  }

  @Test
  @DisplayName("Should update order status")
  void shouldUpdateOrderStatus() throws Exception {
    String orderId = new ObjectId().toHexString();

    when(orderUseCase.updateOrderStatus(orderId)).thenReturn(getOrderResponse());

    mockMvc.perform(post("/api/v1/order/status/" + orderId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status", is("RECEIVED")));
  }

  @Test
  @DisplayName("Should list orders with pagination")
  void shouldListOrders() throws Exception {
    PaginationResponse<OrderResponse> pagedResponse = new PaginationResponse<>();
    pagedResponse.setItems(List.of(getOrderResponse()));
    pagedResponse.setPageNumber(1);
    pagedResponse.setPageSize(1);
    pagedResponse.setHasNext(false);
    pagedResponse.setHasPrevious(false);

    when(orderUseCase.listOrders(1, 25, null)).thenReturn(pagedResponse);

    mockMvc.perform(get("/api/v1/order/list?page=1&limit=25"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.items", hasSize(1)))
      .andExpect(jsonPath("$.pageNumber", is(1)));
  }

  @Test
  @DisplayName("Should list orders with pagination")
  void shouldListOrdersWithStatusCompleted() throws Exception {
    PaginationResponse<OrderResponse> pagedResponse = new PaginationResponse<>();
    pagedResponse.setItems(List.of(getOrderResponse()));
    pagedResponse.setPageNumber(1);
    pagedResponse.setPageSize(1);
    pagedResponse.setHasNext(false);
    pagedResponse.setHasPrevious(false);

    when(orderUseCase.listOrders(1, 25, COMPLETED)).thenReturn(pagedResponse);

    mockMvc.perform(get("/api/v1/order/list?page=1&limit=25&orderStatus=COMPLETED"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.items", hasSize(1)))
      .andExpect(jsonPath("$.pageNumber", is(1)));
  }

  private OrderResponse getOrderResponse() {
    return OrderResponse.builder()
      .id("12312312")
      .clientId("123123")
      .status(RECEIVED)
      .additionalInfo("Teste")
      .date(LocalDateTime.now())
      .totalValue(BigDecimal.valueOf(100.00))
      .items(List.of(
        OrderItemResponse.builder()
          .productId(1L)
          .description("Produto 1")
          .quantity(2)
          .value(BigDecimal.valueOf(50))
          .build(),
        OrderItemResponse.builder()
          .productId(2L)
          .description("Produto 2")
          .quantity(1)
          .value(BigDecimal.valueOf(50))
          .build()
      ))
      .build();
  }
}