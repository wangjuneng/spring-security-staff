package org.springframework.security.crypto.codec;

import org.junit.Test;

public class HexTest {
    
   @Test
   public void encode(){
       char[] chars = Hex.encode(new byte[]{ (byte) 'A'});
   }
    
}
