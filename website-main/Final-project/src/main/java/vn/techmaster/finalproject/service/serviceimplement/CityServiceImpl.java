package vn.techmaster.finalproject.service.serviceimplement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.techmaster.finalproject.exception.NotFoundException;
import vn.techmaster.finalproject.request.CityRequest;
import vn.techmaster.finalproject.service.serviceinterface.CityService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityServiceImpl implements CityService {

    @Override
    public List<CityRequest> getCity() {
        String uri ="https://provinces.open-api.vn/api/p/";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ArrayList<CityRequest> city = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            city.addAll(mapper.readValue(result, new TypeReference<ArrayList<CityRequest>>(){}));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return city;
    };
}
