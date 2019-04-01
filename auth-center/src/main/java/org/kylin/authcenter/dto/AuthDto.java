package org.kylin.authcenter.dto;

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
    private static final long serialVersionUID = 4340438311175597783L;

    private String id;
    private String username;
    private String password;
}
