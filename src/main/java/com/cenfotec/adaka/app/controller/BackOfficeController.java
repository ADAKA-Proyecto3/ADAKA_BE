package com.cenfotec.adaka.app.controller;

import com.cenfotec.adaka.app.domain.Subscription;
import com.cenfotec.adaka.app.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")// controller
@RequiredArgsConstructor
public class BackOfficeController {
    @Autowired
    private SubscriptionService subscriptionService;
    private Logger log = LoggerFactory.getLogger(BackOfficeController .class);


    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable int id) {
        log.debug("getSubscriptionById method  started");
        Subscription s  = subscriptionService.getSubscriptionById(id);
        if (s != null) {
            return ResponseEntity.ok(s);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveSubscription(@RequestBody Subscription sb) {
        log.debug("saveSubscription method  started");
        subscriptionService.saveSubscription(sb);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
