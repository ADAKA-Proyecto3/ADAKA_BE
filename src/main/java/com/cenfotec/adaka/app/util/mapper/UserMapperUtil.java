package com.cenfotec.adaka.app.util.mapper;

import com.cenfotec.adaka.app.domain.Role;
import com.cenfotec.adaka.app.domain.Status;
import com.cenfotec.adaka.app.domain.Subscription;
import com.cenfotec.adaka.app.domain.User;
import com.cenfotec.adaka.app.dto.AdminDto;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

@Component
public class UserMapperUtil {

    public static User  getAdminUserFromDto(AdminDto dto){

        User  user = new User();
        user.setRole(Role.ADMIN);
        user.setStatus(Status.ACTIVE);
        user.setSubscription(getSubscritionFromDto(dto));
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        return user;
    }

    public static Subscription getSubscritionFromDto(AdminDto dto) {
    Subscription subscription = new Subscription();
    subscription.setPaymentAmount(dto.getPaymentAmount());
    subscription.setPaymentCurrency(dto.getPaymentCurrency());
    subscription.setPaymentId(dto.getPaymentId());
    subscription.setShippingAddress(dto.getShippingAddress());
    subscription.setPlanName(dto.getPlanName());
    return  subscription;

    }

}
