package org.springframework.security.crypto.keygen;

public interface BytesKeyGenerator {

    /**
     * Get the length, in bytes, of keys created by this generator. Most unique keys are at least 8 bytes in length.
     * 
     * @author wang.jun<br>
     * @taskId <br>
     * @return <br>
     */
    int getKeyLength();

    /**
     * Generate a new key.: <br>
     * 
     * @author wang.jun<br>
     * @taskId <br>
     * @return <br>
     */
    byte[] generateKey();
}
