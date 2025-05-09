package br.com.order.app.repositories;

import br.com.order.app.entities.Order;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId> {

  Page<Order> findByStatusNotOrderByDateDesc(String status, Pageable pageable);

  Page<Order> findByStatusOrderByDateDesc(String status, Pageable pageable);

}
