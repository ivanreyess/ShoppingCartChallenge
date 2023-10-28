package com.sv.orderservice.controller;

import com.sv.orderservice.dto.OrderDTO;
import com.sv.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sv.orderservice.config.AppConstants.*;


@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    /**
     * {@code POST  /orders} : Create a new order.
     *
     * @param orderDTO the orderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDTO, or with status {@code 400 (Bad Request)} if the order has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping({"", "/"})
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException {
        log.debug("REST request to save Order : {}", orderDTO);
        if (orderDTO.id() != null) return ResponseEntity.badRequest().build();
        OrderDTO result = orderService.save(orderDTO);
        return ResponseEntity
                .created(new URI("/api/orders/" + result.id()))
                .body(result);
    }


    /**
     * {@code PUT  /orders/:id} : Updates an existing order.
     *
     * @param id       the id of the orderDTO to save.
     * @param orderDTO the orderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDTO,
     * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OrderDTO orderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Order : {}, {}", id, orderDTO);
        if (orderDTO.id() == null) return ResponseEntity.badRequest().build();
        if (!Objects.equals(id, orderDTO.id())) return ResponseEntity.badRequest().build();

        if (!orderService.exists(id)) return ResponseEntity.badRequest().build();

        OrderDTO result = orderService.update(orderDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code PATCH  /orders/:id} : Partial updates given fields of an existing order, field will ignore if it is null
     *
     * @param id       the id of the orderDTO to save.
     * @param orderDTO the orderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDTO,
     * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<OrderDTO> partialUpdateOrder(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OrderDTO orderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Order partially : {}, {}", id, orderDTO);
        if (orderDTO.id() == null) return ResponseEntity.badRequest().build();

        if (!Objects.equals(id, orderDTO.id())) return ResponseEntity.badRequest().build();

        if (!orderService.exists(id)) return ResponseEntity.badRequest().build();

        Optional<OrderDTO> result = orderService.partialUpdate(orderDTO);

        return result.map(o -> ResponseEntity.ok().body(o)).orElse(ResponseEntity.badRequest().build());
    }


    /**
     * {@code GET  /order-details} : get all the orderDetails.
     *
     * @param pageNo the page number.
     * @param pageSize the page size.
     * @param sortBy property to be sorted.
     * @param sortDir direction: ASC or DESC of the sorted property.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDetails in body.
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        log.debug("REST request to get a page of OrderDetails");
        Page<OrderDTO> page = orderService.findAll(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /orders/:id} : get the "id" order.
     *
     * @param id the id of the orderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        log.debug("REST request to get Order : {}", id);
        Optional<OrderDTO> orderDTO = orderService.findOne(id);
        return orderDTO.map(order -> ResponseEntity.ok().body(order)).orElse(ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /orders/:id} : delete the "id" order.
     *
     * @param id the id of the orderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.debug("REST request to delete Order : {}", id);
        orderService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
