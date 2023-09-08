package vn.techmaster.finalproject.service.serviceinterface;

import feign.Param;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.request.CityRequest;

import java.util.List;

@Service
public interface CityService {
    List<CityRequest> getCity();
}
