package com.jbit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmbmsBill {

  private Integer id;
  private String billCode;
  private String productName;
  private String productDesc;
  private String productUnit;
  private double productCount;
  private double totalPrice;
  private Integer isPayment;
  private long createdBy;
  private Date creationDate;
  private Integer modifyBy;
  private Date modifyDate;
  private Integer providerId;
  private SmbmsProvider smbmsProvider;

}
