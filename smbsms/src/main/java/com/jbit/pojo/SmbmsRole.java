package com.jbit.pojo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SmbmsRole {

  private Integer id;
  private String  roleCode;
  private String  roleName;
  private Integer createdBy;
  private Date    creationDate;
  private long    modifyBy;
  private Date    modifyDate;
  private List<SmbmsUser> smbmsUsers;
}
