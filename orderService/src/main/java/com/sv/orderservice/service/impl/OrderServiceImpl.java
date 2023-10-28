package com.sv.orderservice.service.impl;

import com.sv.orderservice.domain.Order;
import com.sv.orderservice.domain.dto.OrderDTO;
import com.sv.orderservice.repository.OrderRepository;
import com.sv.orderservice.service.OrderService;
import com.sv.orderservice.service.feign.OrderDetailFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sv.orderservice.domain.Order.toDto;
import static com.sv.orderservice.domain.Order.toEntity;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final OrderDetailFeignService orderDetailService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailFeignService orderDetailService) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
    }


    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Double total = orderDetailService.getTotal();
        Order order = toEntity(OrderDTO.builder()
                .id(orderDTO.id())
                .name(orderDTO.name())
                .lastName(orderDTO.lastName())
                .total(total)
                .discount(orderDTO.discount())
                .city(orderDTO.city())
                .country(orderDTO.country())
                .email(orderDTO.email())
                .shippingAddress(orderDTO.shippingAddress())
                .build());
        order = orderRepository.save(order);
        return toDto(order);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        return save(orderDTO);
    }

    @Override
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
                .findById(orderDTO.id())
                .map(existingOrder -> {
                    if (orderDTO.name() != null) existingOrder.setName(orderDTO.name());
                    if (orderDTO.lastName() != null) existingOrder.setLastName(orderDTO.lastName());
                    if (orderDTO.total() != null) existingOrder.setTotal(orderDTO.total());
                    if (orderDTO.city() != null) existingOrder.setCity(orderDTO.city());
                    if (orderDTO.country() != null) existingOrder.setCountry(orderDTO.country());
                    if (orderDTO.email() != null) existingOrder.setEmail(orderDTO.email());
                    if (orderDTO.discount() != null) existingOrder.setDiscount(orderDTO.discount());
                    if (orderDTO.shippingAddress() != null) existingOrder.setShippingAddress(orderDTO.shippingAddress());
                    return existingOrder;
                })
                .map(orderRepository::save)
                .map(Order::toDto);
    }

    @Override
    public Page<OrderDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Order> result = orderRepository.findAll(pageable);
        return result.map(Order::toDto);
    }

    @Override
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(Order::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        log.debug("Request to check if order exists : {}", id);
        return orderRepository.existsById(id);
    }
}
