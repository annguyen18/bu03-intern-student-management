package com.viettel.intern.service;

import com.viettel.intern.entity.base.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;


public interface NotificationService {

    /**
     * Saves the specified notification to the database
     *
     * @param notification The notification to be saved
     */
    void save(Notification notification);

    /**
     * Tim kiem va tra ve mot trang chua danh sach cac thong bao dua tren ngay tao(cratedDate) va duoc sap xep, phan trang.
     * @param createdDate Ngay_tao(createdDate) de loc thong bao (Neu null, khong duoc ap dung).
     * @param pageable Thong tin phan trang va sap xep ket qua tra ve.
     */
//    Page<Notification> findAll(Date createdDate, Pageable pageable);
}

