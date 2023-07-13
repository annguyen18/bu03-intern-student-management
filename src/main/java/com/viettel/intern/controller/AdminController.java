package com.viettel.intern.controller;

import com.viettel.intern.config.factory.response.GeneralResponse;
import com.viettel.intern.config.factory.response.ResponseFactory;
import com.viettel.intern.entity.base.Course;
import com.viettel.intern.service.impl.AdminServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@AllArgsConstructor
public class AdminController {
    private final ResponseFactory responseFactory;
    private final AdminServiceImpl adminService;

    /**
     * api to find course containing keyword, use pagination and sort
     * authority: admin/teacher
     *
     * @param page: the number of the page that need to be checked out
     * @param size: size of each page
     * @param sortBy: sort by which category
     * @param sortOrder: order asc or desc
     */
    @GetMapping("/courses")
    public ResponseEntity<GeneralResponse<Object>> getCourses(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size,
                                                              @RequestParam(defaultValue = "name") String sortBy,
                                                              @RequestParam(defaultValue = "asc") String sortOrder,
                                                              @RequestParam(defaultValue = "") String keyword) {
        Page<Course> coursePage = adminService.searchAndSortCourses(keyword, page, size, sortBy, sortOrder);
        return responseFactory.success(coursePage);
    }
}
