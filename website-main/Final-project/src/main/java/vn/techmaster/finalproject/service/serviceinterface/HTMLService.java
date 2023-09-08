package vn.techmaster.finalproject.service.serviceinterface;

import org.springframework.stereotype.Service;

@Service
public interface HTMLService {
    String markdownToHtml(String markdown);
}
