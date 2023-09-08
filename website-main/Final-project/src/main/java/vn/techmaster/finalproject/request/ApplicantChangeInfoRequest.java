package vn.techmaster.finalproject.request;

import vn.techmaster.finalproject.model.entity.Skill;

import java.util.List;

public record ApplicantChangeInfoRequest(String user_id,String name, String city, String phone, List<Skill> skills) {
}
