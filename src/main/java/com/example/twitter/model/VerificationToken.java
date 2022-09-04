package com.example.twitter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "verification_tokens")
public class VerificationToken {
    private static final int EXPIRATION = 60*24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

//    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
////    @JoinColumn(nullable = false, name = "user_id")
//    private User user;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public VerificationToken(final String token, final User user){
        super();
        this.token = token;
//        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

}
