package org.springframework.security.crypto.keygen;

public class KeyGenerators {
    /**
     * Create a {@link BytesKeyGenerator} that uses a {@link SecureRandom} to generate keys of 8 bytes in length.
     */
    public static BytesKeyGenerator secureRandom() {
        return new SecureRandomBytesKeyGenerator();
    }

    /**
     * Create a {@link BytesKeyGenerator} that uses a {@link SecureRandom} to generate keys of a custom length.
     * 
     * @param keyLength the key length in bytes, e.g. 16, for a 16 byte key.
     */
    public static BytesKeyGenerator secureRandom(int keyLength) {
        return new SecureRandomBytesKeyGenerator(keyLength);
    }

    /**
     * Create a {@link BytesKeyGenerator} that returns a single, shared {@link SecureRandom} key of a custom length.
     * 
     * @param keyLength the key length in bytes, e.g. 16, for a 16 byte key.
     */
    public static BytesKeyGenerator shared(int keyLength) {
        return new SharedKeyGenerator(secureRandom(keyLength).generateKey());
    }

    /**
     * Creates a {@link StringKeyGenerator} that hex-encodes {@link SecureRandom} keys of 8 bytes in length. The
     * hex-encoded string is keyLength * 2 characters in length.
     */
    public static StringKeyGenerator string() {
        return new HexEncodingStringKeyGenerator(secureRandom());
    }

    // internal helpers

    private KeyGenerators() {
    }
}
