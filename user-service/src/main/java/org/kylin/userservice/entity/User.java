package org.kylin.userservice.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 5668495949680291248L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 128)
    private String id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    // if user is authorized by auth-center, active is true.
    private boolean active;

    @Column(name = "phone_number")
    private String phoneNumber;
}
