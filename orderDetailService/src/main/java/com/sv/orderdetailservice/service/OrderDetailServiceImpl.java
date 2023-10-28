package com.sv.orderdetailservice.service;

import com.sv.orderdetailservice.domain.OrderDetail;
import com.sv.orderdetailservice.domain.dto.OrderDetailDTO;
import com.sv.orderdetailservice.domain.dto.ProductDTO;
import com.sv.orderdetailservice.repository.OrderDetailRepository;
import com.sv.orderdetailservice.service.feign.ProductFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sv.orderdetailservice.domain.OrderDetail.toDto;
import static com.sv.orderdetailservice.domain.OrderDetail.toEntity;

@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService{

    private final OrderDetailRepository orderDetailRepository;

    private final ProductFeignService productFeignService;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, ProductFeignService productFeignService) {
        this.orderDetailRepository = orderDetailRepository;
        this.productFeignService = productFeignService;
    }


    @Override
    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
        Optional<ProductDTO> productDTO = productFeignService.getById(orderDetailDTO.productId());
        Optional<OrderDetailDTO> validatedOrderDetail = productDTO.map(product ->
                OrderDetailDTO.builder()
                .id(orderDetailDTO.id())
                .orderId(orderDetailDTO.orderId())
                .quantity(orderDetailDTO.quantity())
                .productId(product.id())
                .price(product.price())
                .build());
        return toDto(validatedOrderDetail.map(o -> orderDetailRepository.save(toEntity(o))).orElse(OrderDetail.builder().build()));

    }

    @Override
    public OrderDetailDTO update(OrderDetailDTO orderDetailDTO) {
        return this.save(orderDetailDTO);
    }

    @Override
    public Optional<OrderDetailDTO> partialUpdate(OrderDetailDTO orderDetailDTO) {
        return orderDetailRepository.findById(orderDetailDTO.id())
                .map(existingOrderDetail -> {
                    if (orderDetailDTO.orderId() != null) {
                        existingOrderDetail.setOrderId(orderDetailDTO.orderId());
                    }
                    if (orderDetailDTO.productId() != null) {
                        existingOrderDetail.setProductId(orderDetailDTO.productId());
                    }
                    if (orderDetailDTO.price() != null) {
                        existingOrderDetail.setPrice(orderDetailDTO.price());
                    }
                    if (orderDetailDTO.quantity() != null) {
                        existingOrderDetail.setQuantity(orderDetailDTO.quantity());
                    }

                    return existingOrderDetail;
                })
                .map(orderDetailRepository::save)
                .map(OrderDetail::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetailDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<OrderDetail> result = orderDetailRepository.findAll(pageable);
        return result.map(OrderDetail::toDto);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetailDTO> findOne(Long id) {
        return orderDetailRepository.findById(id).map(OrderDetail::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderDetaill : {}", id);
        orderDetailRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return orderDetailRepository.existsById(id);
    }
}
