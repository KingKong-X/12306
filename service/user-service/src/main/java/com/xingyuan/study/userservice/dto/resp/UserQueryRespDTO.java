package com.xingyuan.study.userservice.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xingyuan.study.userservice.serialize.IdCardDesensitizationSerializer;
import com.xingyuan.study.userservice.serialize.PhoneDesensitizationSerializer;
import lombok.Data;

/**
 * @author Xingyuan Huang
 * @since 2023/9/19 15:57
 */
@Data
public class UserQueryRespDTO {
    /**
     * username
     */
    private String username;

    /**
     * user real name
     */
    private String realName;

    /**
     * country or area
     */
    private String region;

    /**
     * certificate type
     */
    private Integer idType;

    /**
     * certificate number
     */
    @JsonSerialize(using = IdCardDesensitizationSerializer.class)
    private String idCard;

    /**
     * phone
     */
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    /**
     * telephone
     */
    private String telephone;

    /**
     * mail
     */
    private String mail;

    /**
     * user type
     */
    private Integer userType;

    /**
     * audit status
     */
    private Integer verifyStatus;

    /**
     * postcode
     */
    private String postCode;

    /**
     * address
     */
    private String address;
}
