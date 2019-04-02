package org.kylin.authcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 3906532779928494919L;

    private Set<String> roles = new HashSet<>();
}
