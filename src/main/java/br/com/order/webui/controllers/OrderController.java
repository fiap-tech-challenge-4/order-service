package br.com.order.webui.controllers;

import br.com.order.app.usecases.OrderUseCase;
import br.com.order.webui.dtos.request.CreateOrderRequest;
import br.com.order.webui.dtos.response.ErrorResponse;
import br.com.order.webui.dtos.response.OrderResponse;
import br.com.order.webui.dtos.response.PaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.order.enums.OrderStatus.toOrderStatusString;
import static br.com.order.errors.Errors.MINIMAL_PAGE;
import static br.com.order.webui.constants.Descriptions.ID;
import static br.com.order.webui.constants.Descriptions.LIMIT;
import static br.com.order.webui.constants.Descriptions.PAGE;
import static br.com.order.webui.converters.OrderConverter.toOrderDTO;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Order")
@ApiResponse(responseCode = "400", description = "Bad Request",
  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@ApiResponse(responseCode = "422", description = "Unprocessable Entity",
  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
@RequestMapping(value = "/api/v1/order")
public class OrderController {

  private static final String ASC_DESC = "ASC/DESC";

  private final OrderUseCase orderUseCase;

  @Operation(summary = "Create order")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Order was created",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))})})
  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(
    @RequestBody
    @Valid
    CreateOrderRequest request) {
    return ResponseEntity
      .status(CREATED)
      .body(orderUseCase.createOrder(toOrderDTO(request)));
  }

  @Operation(summary = "Find order by id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Order found successfully",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))})})
  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> findOrderById(
    @Parameter(description = ID)
    @PathVariable(name = "id") final String id) {
    return new ResponseEntity<>(orderUseCase.findOrderById(id), OK);
  }


  @Operation(summary = "Update order status")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Order status updated successfully",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))})})
  @PostMapping("/status/{id}")
  public ResponseEntity<OrderResponse> updateOrderStatus(
    @Parameter(description = ID)
    @PathVariable(name = "id")
    final String id) {
    var response = orderUseCase.updateOrderStatus(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Operation(summary = "List orders")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Orders found successfully",
      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))})})
  @GetMapping("/list")
  public ResponseEntity<PaginationResponse<OrderResponse>> listOrders(
    @Parameter(description = PAGE)
    @RequestParam(required = false, defaultValue = "1")
    @Min(value = 1, message = MINIMAL_PAGE) final Integer page,
    @Parameter(description = LIMIT)
    @RequestParam(required = false, defaultValue = "25")
    final Integer limit,
    @Parameter(description = "Order status to filter by")
    @RequestParam(required = false)
    final String orderStatus) {
    var response = orderUseCase.listOrders(page, limit, toOrderStatusString(orderStatus));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
