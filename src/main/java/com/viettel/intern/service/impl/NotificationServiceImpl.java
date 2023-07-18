package com.viettel.intern.service.impl;

import com.viettel.intern.entity.base.Notification;
import com.viettel.intern.repository.base.NotificationRepository;
import com.viettel.intern.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Page<Notification> findAll(Date createdDate, Pageable pageable) {
        if (createdDate != null) {
            return notificationRepository.findByCreatedDate(createdDate, pageable);
        } else {
            return notificationRepository.findAll(pageable);
        }
    }
}
