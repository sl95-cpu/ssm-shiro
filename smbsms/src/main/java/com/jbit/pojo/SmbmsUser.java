package com.jbit.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmbmsUser {
  private Integer id;
  private String userCode;
  private String userName;
  private String userPassword;
  private Integer gender;
  private Date birthday;
  private String phone;
  private String address;
  private Integer userRole;
  private Integer createdBy;
  private Date creationDate;
  private Integer modifyBy;
  private Date modifyDate;
  private List<SmbmsAddress> smbmsAddressList;
  private SmbmsRole smbmsRole;
  private String userRoleName;
  private String roles;
  }
