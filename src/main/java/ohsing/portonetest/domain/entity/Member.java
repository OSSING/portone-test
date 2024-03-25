package ohsing.portonetest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    private String address;


    @Builder
    public Member (String username, String email, String address) {
        this.username = username;
        this.email = email;
        this.address = address;
    }
}
