package org.kylin.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto implements Serializable {

    private static final long serialVersionUID = -2332453350869686020L;

    private String id; // user id
    private String username;
    private String password;
}
