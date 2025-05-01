package br.com.order.app.entities;

import br.com.order.app.exception.BusinessException;
import br.com.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.order.enums.OrderStatus.COMPLETED;
import static br.com.order.enums.OrderStatus.PREPARING;
import static br.com.order.enums.OrderStatus.READY;
import static br.com.order.errors.Errors.STATUS_INVALIDO;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

  @Id
  private ObjectId id;
  private LocalDateTime date;
  private String clientId;
  private BigDecimal totalValue;
  private OrderStatus status;
  private String additionalInfo;

  @Builder.Default
  private List<OrderItem> items = new ArrayList<>();

  public OrderStatus getNextStatus() {
    return switch (this.status) {
      case RECEIVED -> PREPARING;
      case PREPARING -> READY;
      case READY -> COMPLETED;
      default -> throw new BusinessException(STATUS_INVALIDO, UNPROCESSABLE_ENTITY);
    };
  }

}
