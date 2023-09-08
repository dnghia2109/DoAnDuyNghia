package vn.techmaster.finalproject.request;

import vn.techmaster.finalproject.model.entity.CompanyType;

public record ChangeInfoEmployerRequest(String name, String companyName, String website, String city, String phoneNumber, String employeeSize, String addressDetail) {
}
