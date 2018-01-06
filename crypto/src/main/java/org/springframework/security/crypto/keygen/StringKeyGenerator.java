package org.springframework.security.crypto.keygen;

/**
 * 
 * <Description> A generator for unique string keys. <br> 
 *  
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年1月6日 <br>
 * @since V7.3<br>
 * @see org.springframework.security.crypto.keygen <br>
 */
public interface StringKeyGenerator {
    String generateKey();
}
