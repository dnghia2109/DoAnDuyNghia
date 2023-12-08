package com.example.blog.utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class Utils {
    public static List<Integer> createList(Integer size) {
        List<Integer> range = IntStream.rangeClosed(1, size)
                .boxed().toList();

        return range;
    }

    public static LocalDateTime convertTimeStringToLocalDateTime(String time) {
        // Viết mã chuyển đổi chuỗi thời gian thành LocalDateTime tại đây
        // Bạn có thể sử dụng DateTimeFormatter hoặc các thư viện khác để chuyển đổi
        // Đây chỉ là một ví dụ đơn giản, bạn cần điều chỉnh phù hợp với định dạng chuỗi thời gian của bạn
        if (time.equalsIgnoreCase("1day")) {
            return LocalDateTime.now().minusDays(1);
        } else if (time.equalsIgnoreCase("1week")) {
            return LocalDateTime.now().minusWeeks(1);
        } else if (time.equalsIgnoreCase("1month")) {
            return LocalDateTime.now().minusMonths(1);
        } else if (time.equalsIgnoreCase("1year")) {
            return LocalDateTime.now().minusYears(1);
        } else {
            return null; // Hoặc throw một exception nếu định dạng không hợp lệ
        }
    }
}
