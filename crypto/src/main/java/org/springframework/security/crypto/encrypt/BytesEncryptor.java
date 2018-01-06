package org.springframework.security.crypto.encrypt;

/**
 * Service interface for symmetric data encryption. <br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年1月6日 <br>
 * @since V7.3<br>
 * @see org.springframework.security.crypto.encrypt <br>
 */
public interface BytesEncryptor {

    byte[] encrypt(byte[] byteArray);

    byte[] decrypt(byte[] encryptedBytedArray);
}
