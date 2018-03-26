package org.springframework.security.crypto.bcrypt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BCryptPasswordEncoderTests {
    
    @Test
    public void matches(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String result = encoder.encode("password");
        
        System.out.println(result);
        
        assertThat(result.equals("password")).isFalse();
        assertThat(encoder.matches("password", result)).isTrue();
    }
    
    @Test
    public void unicode() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("passw\u9292rd");
        System.out.println(result);
        assertThat(encoder.matches("pass\u9292\u9292rd", result)).isFalse();
        assertThat(encoder.matches("passw\u9292rd", result)).isTrue();
    }
}
