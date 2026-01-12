package com.bookshop.security;

import com.bookshop.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    //사용자의 특정한 권한을 리턴해주는 메서드 -> role 값을 리턴해주는 거임
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    //사용자의 계정이 만료됐는지 -> 이거 사용하고 싶으면 테이블의 옵션 값 넣고 가져오면 됨
    @Override
    public boolean isAccountNonExpired() {
        return true; //강제로 만료가 되지 않았다는걸로 설정
    }

    //사용자의 계정이 잠겼는지 체크
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //지금 사용가능한 계정인지 체크
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
