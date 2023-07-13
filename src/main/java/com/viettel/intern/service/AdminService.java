package com.viettel.intern.service;

import com.viettel.intern.entity.base.Course;
import org.springframework.data.domain.Page;

public interface AdminService {
    Page<Course> searchAndSortCourses(String keyword, int page, int size, String sortBy, String sortOrder);
}
