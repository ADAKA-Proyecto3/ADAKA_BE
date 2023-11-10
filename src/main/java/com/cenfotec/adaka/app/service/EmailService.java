package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.User;

public interface EmailService {
   void  sendMessage(User user,String password);
}
