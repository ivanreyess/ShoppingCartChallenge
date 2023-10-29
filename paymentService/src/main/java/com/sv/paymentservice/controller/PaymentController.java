package com.sv.paymentservice.controller;

import com.sv.paymentservice.dto.PaymentDTO;
import com.sv.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sv.paymentservice.config.AppConstants.*;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * {@code POST  /payments} : Create a new payment.
     *
     * @param paymentDTO the paymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentDTO, or with status {@code 400 (Bad Request)} if the payment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping({"/", ""})
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
        log.debug("REST request to save Payment : {}", paymentDTO);
        if (paymentDTO.id() != null) {
            return ResponseEntity.badRequest().build();
        }
        PaymentDTO result = paymentService.save(paymentDTO);
        return ResponseEntity
                .created(new URI("/api/payments/" + result.id()))
                .body(result);
    }

    /**
     * {@code PUT  /payments/:id} : Updates an existing payment.
     *
     * @param id         the id of the paymentDTO to save.
     * @param paymentDTO the paymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody PaymentDTO paymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Payment : {}, {}", id, paymentDTO);
        if (paymentDTO.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(id, paymentDTO.id())) {
            return ResponseEntity.badRequest().build();
        }

        if (!paymentService.exist(id)) {
            return ResponseEntity.badRequest().build();
        }

        PaymentDTO result = paymentService.update(paymentDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * {@code PATCH  /payments/:id} : Partial updates given fields of an existing payment, field will ignore if it is null
     *
     * @param id         the id of the paymentDTO to save.
     * @param paymentDTO the paymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<PaymentDTO> partialUpdatePayment(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody PaymentDTO paymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payment partially : {}, {}", id, paymentDTO);
        if (paymentDTO.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!Objects.equals(id, paymentDTO.id())) {
            return ResponseEntity.badRequest().build();
        }

        if (!paymentService.exist(id)) {
            return ResponseEntity.notFound().build();
        }

        Optional<PaymentDTO> result = paymentService.partialUpdate(paymentDTO);

        return result.map(p -> ResponseEntity.ok().body(p)).orElse(ResponseEntity.badRequest().build());
    }

    /**
     * {@code GET  /payments} : get all the payments.
     *
     * @param pageNo the page number.
     * @param pageSize the page size.
     * @param sortBy property to be sorted.
     * @param sortDir direction: ASC or DESC of the sorted property.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payments in body.
     */
    @GetMapping({"/", ""})
    public ResponseEntity<List<PaymentDTO>> getAllPayments( @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                                            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        log.debug("REST request to get a page of Payments");
        Page<PaymentDTO> page = paymentService.findAll(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payment.
     *
     * @param id the id of the paymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment : {}", id);
        Optional<PaymentDTO> paymentDTO = paymentService.findOne(id);
        return paymentDTO.map(p -> ResponseEntity.ok().body(p)).orElse(ResponseEntity.badRequest().build());
    }

    /**
     * {@code DELETE  /payments/:id} : delete the "id" payment.
     *
     * @param id the id of the paymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.debug("REST request to delete Payment : {}", id);
        paymentService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
