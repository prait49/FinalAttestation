package com.example.finalattestation.services;


import com.example.finalattestation.enumm.Status;
import com.example.finalattestation.models.Order;
import com.example.finalattestation.models.Product;
import com.example.finalattestation.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //Данный метод создает лист заказов
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }


    //Данный метод позволяет получить заказ по id
    public Order getOrderId(int id){
        Optional<Order> optionalOrder=orderRepository.findById(id);
        return  optionalOrder.orElse(null);
    }
    //Данный метод позволяет обновить данные о статусе заказа
    @Transactional
    public  void updateOrder(int id, Order order){
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ не найден"));
        existingOrder.setStatus(order.getStatus());
        orderRepository.save(existingOrder);
    }

    @Transactional
    //Данный метод позволяет удалить заказ по ID
    public  void deleteOrder(int id){
        orderRepository.deleteById(id);
    }
}
