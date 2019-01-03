package xyz.hyunto.clientauthorizationcode.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class ClientUser {

    private Long id;

    private String username;

    private String password;

    private String accessToken;

    private Calendar accessTokenValidity;

    private String refreshToken;

    private List<Entry> entries = new ArrayList<>();
}
