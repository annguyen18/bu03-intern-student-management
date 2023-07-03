package com.viettel.intern.service.impl;

import com.viettel.intern.service.ErrorService;
import org.springframework.stereotype.Service;

@Service
public class ErrorServiceImpl implements ErrorService {
    @Override
    @SuppressWarnings("all")
    public String getErrorDetail(String errorCode, String language) {
        // sử dụng nếu bạn muốn sử lý một error với một msg được lấy từ ở bên ngoài, ví dụ database...etc..
        switch (language) {
            case "vi": {
                switch (errorCode) {
                    case "00": {
                        return "Thành công";
                    }
                    default: {
                        return null;
                    }
                }
            }
            default: {
                switch (errorCode) {
                    case "00": {
                        return "success";
                    }
                    default: {
                        return null;
                    }
                }
            }
        }
    }
}
