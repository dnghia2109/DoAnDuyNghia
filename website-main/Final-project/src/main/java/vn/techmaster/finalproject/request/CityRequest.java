package vn.techmaster.finalproject.request;

import lombok.Data;

@Data
public class CityRequest {
    private String name;
    private String code;
    private String division_type;
    private String codename;
    private String phone_code;
    private String[] districts;
}
