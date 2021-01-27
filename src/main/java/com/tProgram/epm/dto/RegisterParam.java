package com.tProgram.epm.dto;

import lombok.Data;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020/9/2.
 *
 * @author genglintong.
 */
@Data
public class RegisterParam {
    private String code;
    private String iv;
    private String encryptedData;
}
