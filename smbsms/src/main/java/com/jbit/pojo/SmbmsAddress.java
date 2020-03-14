package com.jbit.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class SmbmsAddress {

  private Integer id;
  private String contact;
  private String addressDesc;
  private String postCode;
  private String tel;
  private Integer createdBy;
  private Date creationDate;
  private long modifyBy;
  private Date modifyDate;
  private long userId;

}
