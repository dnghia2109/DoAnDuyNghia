package vn.techmaster.finalproject.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.techmaster.finalproject.exception.NotFoundException;
import vn.techmaster.finalproject.model.entity.State;
import vn.techmaster.finalproject.model.entity.User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailCustom implements UserDetails  {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() throws NotFoundException{
        if(user.getState().equals(State.DISABLED)){
            throw new NotFoundException("Tài khoản đã bị khóa");
        }
       return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() throws NotFoundException{
        if(user.getState().equals(State.PENDING)) {
            throw new NotFoundException("Tài khoản chưa được kích hoạt");
        }else return user.getState().equals(State.ACTIVE);
    }
}
