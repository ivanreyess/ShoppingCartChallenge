package com.sv.orderdetailservice.controller;

import com.sv.orderdetailservice.domain.dto.OrderDetailDTO;
import com.sv.orderdetailservice.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sv.orderdetailservice.config.AppConstants.*;

@RestController
@RequestMapping("/api/order-details")
@Slf4j
public class OrderDetailController {

    private final OrderDetailService orderDetailService;


    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    /**
     * {@code POST  /order-details} : Create a new orderDetail.
     *
     * @param orderDetailDTO the orderDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDetailDTO, or with status {@code 400 (Bad Request)} if the orderDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) throws URISyntaxException {
        log.debug("REST request to save OrderDetail : {}", orderDetailDTO);
        if (orderDetailDTO.id() != null) {
            return ResponseEntity.badRequest().build();
        }
        OrderDetailDTO result = orderDetailService.save(orderDetailDTO);
        if (result.id() == null)  return ResponseEntity.badRequest().build();
        return ResponseEntity
                .created(new URI("/order-details/" + result.id()))
                .body(result);
    }

    /**
     * {@code PUT  /order-details/:id} : Updates an existing orderDetail.
     *
     * @param id the id of the orderDetailDTO to save.
     * @param orderDetailDTO the orderDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailDTO,
     * or with status {@code 400 (Bad Request)} if the orderDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        log.debug("REST request to update OrderDetail : {}, {}", id, orderDetailDTO);
        if (orderDetailDTO.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(id, orderDetailDTO.id())) {
            return ResponseEntity.badRequest().build();

        }

        if (!orderDetailService.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        OrderDetailDTO result = orderDetailService.update(orderDetailDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code PATCH  /order-details/:id} : Partial updates given fields of an existing orderDetail, field wil ignore if it is null
     *
     * @param id the id of the orderDetailDTO to save.
     * @param orderDetailDTO the orderDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailDTO,
     * or with status {@code 400 (Bad Request)} if the orderDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderDetailDTO> partialUpdateOrderDetail (
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderDetail partially : {}, {}", id, orderDetailDTO);
        if (orderDetailDTO.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(id, orderDetailDTO.id())) {
            return ResponseEntity.badRequest().build();
        }

        if (!orderDetailService.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        Optional<OrderDetailDTO> result = orderDetailService.partialUpdate(orderDetailDTO);

        return result.map(orderDetailDTOUpdated -> ResponseEntity.ok().body(orderDetailDTOUpdated)).orElse(ResponseEntity.notFound().build());

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
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        log.debug("REST request to get a page of OrderDetails");
        Page<OrderDetailDTO> page = orderDetailService.findAll(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /order-details/:id} : get the "id" orderDetail.
     *
     * @param id the id of the orderDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(@PathVariable Long id) {
        log.debug("REST request to get OrderDetail : {}", id);
        Optional<OrderDetailDTO> orderDetailDTO = orderDetailService.findOne(id);
        return orderDetailDTO.map(orderDetailDTOUpdated -> ResponseEntity.ok().body(orderDetailDTOUpdated)).orElse(ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /order-details/:id} : delete the "id" orderDetail.
     *
     * @param id the id of the orderDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetail : {}", id);
        orderDetailService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
