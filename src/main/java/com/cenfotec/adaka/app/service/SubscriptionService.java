package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.Subscription;
import com.cenfotec.adaka.app.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository sr;
    public Subscription saveSubscription(Subscription sb) {
        return sr.save(sb);
    }

    public Subscription getSubscriptionById(int id) {

        return sr.findById(id).get();
    }
}
