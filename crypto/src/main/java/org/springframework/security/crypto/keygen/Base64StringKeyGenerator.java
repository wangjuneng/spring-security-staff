package org.springframework.security.crypto.keygen;

import java.util.Base64;

public class Base64StringKeyGenerator implements StringKeyGenerator {

    private static final int DEFAULT_KEY_LENGTH = 32;
    private final BytesKeyGenerator keyGenerator;
    private final Base64.Encoder encoder;
    
    /**
     * Creates an instance with keyLength of 32 bytes and standard Base64 encoding.
     */
    public Base64StringKeyGenerator() {
        this(DEFAULT_KEY_LENGTH);
    }
    
    /**
     * Creates an instance with the provided key length in bytes and standard Base64
     * encoding.
     * @param keyLength the key length in bytes
     */
    public Base64StringKeyGenerator(int keyLength) {
        this(Base64.getEncoder(), keyLength);
    }
    
    /**
     * Creates an instance with keyLength of 32 bytes and the provided encoder.
     * @param encoder the encoder to use
     */
    public Base64StringKeyGenerator(Base64.Encoder encoder) {
        this(encoder, DEFAULT_KEY_LENGTH);
    }
    
    /**
     * Creates an instance with the provided key length and encoder.
     * @param encoder the encoder to use
     * @param keyLength the key length to use
     */
    public Base64StringKeyGenerator(Base64.Encoder encoder, int keyLength) {
        if(encoder == null) {
            throw new IllegalArgumentException("encode cannot be null");
        }
        if(keyLength < DEFAULT_KEY_LENGTH) {
            throw new IllegalArgumentException("keyLength must be greater than or equal to" + DEFAULT_KEY_LENGTH);
        }
        this.encoder = encoder;
        this.keyGenerator = KeyGenerators.secureRandom(keyLength);
    }


    
    @Override
    public String generateKey() {
        byte[] key = this.keyGenerator.generateKey();
        byte[] base64EncodedKey = this.encoder.encode(key);
        return new String(base64EncodedKey);
    }

}
