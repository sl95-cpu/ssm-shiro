package com.jbit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmbmsProvider {
  private Integer id;
  private String  proCode;
  private String  proName;
  private String  proDesc;
  private String  proContact;
  private String  proPhone;
  private String  proAddress;
  private String  proFax;
  private Integer createdBy;
  private Date    creationDate;
  private Date    modifyDate;
  private Integer modifyBy;
  private List<SmbmsBill> smbmsBills;
}
