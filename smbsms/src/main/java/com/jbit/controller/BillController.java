package com.jbit.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
;
import com.jbit.pojo.SmbmsBill;
import com.jbit.pojo.SmbmsProvider;
import com.jbit.service.Bill.BillService;
import com.jbit.service.Provider.ProviderService;
import com.jbit.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;

     /**
     * go 订单管理页面
     * @return
     */
    @GetMapping("/toBill")
    public String toBill(Model model){
        Page<SmbmsBill> page = PageHelper.startPage(1, 5).doSelectPage(() -> billService.allBill());
        int sumNum=1;
        if (page.getPages() > 0 ) {
            sumNum = page.getPages();
        }
        List<SmbmsProvider> providerList = providerService.getProviderList(null);
        model.addAttribute("totalPageCount",page.getPageSize());
        model.addAttribute("totalCount",page.getTotal());
        model.addAttribute("currentPageNo",page.getPageNum());
        model.addAttribute("totalPageCount",sumNum);
        model.addAttribute("billList",page.getResult());
        model.addAttribute("providerList",providerList);
        return "/jsp/bill/billlist";
    }

    /**
     * 分页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/billList")
    public String billList(HttpServletRequest request, Model model){
        String queryProductName = request.getParameter("queryProductName");
        String pageIndex = request.getParameter("pageIndex");
        Integer queryProviderId = Integer.valueOf(request.getParameter("queryProviderId"));
        Integer queryIsPayment = Integer.valueOf(request.getParameter("queryIsPayment"));
        Map map = new HashMap();
        map.put("queryProviderId",queryProviderId);
        map.put("queryProductName",queryProductName);
        map.put("queryIsPayment",queryIsPayment);
        //当前页码
        int currentPageNo = 1;
        //查询用户列表

        if (pageIndex != null) {
            currentPageNo = Integer.valueOf(pageIndex);
        }
        System.out.println(pageIndex);

        Page<SmbmsBill> page =
                PageHelper.startPage(currentPageNo,5).doSelectPage(() -> billService.getBillList(map));
        List<SmbmsBill> SmbmsBillList = page.getResult();
        int sumNum=1;
        if (page.getPages() > 0 ) {
            sumNum = page.getPages();
        }
        List<SmbmsProvider> providerList = providerService.getProviderList(null);
        model.addAttribute("totalPageCount",page.getPageSize());
        model.addAttribute("totalCount",page.getTotal());
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("totalPageCount",sumNum);
        model.addAttribute("billList",SmbmsBillList);
        model.addAttribute("providerList",providerList);
        model.addAttribute("queryProviderId",queryProviderId);
        model.addAttribute("queryIsPayment",queryIsPayment);
        model.addAttribute("queryProductName",queryProductName);
        return "/jsp/bill/billlist";
    }

    /**
     * 查看定单详细情况
     */
    @RequestMapping("billView/{billid}")
    public String bilView(@PathVariable("billid") String id,Model model){
        SmbmsBill bill = billService.getBillById(id);
        List<SmbmsProvider> providerList = providerService.getProviderList(null);
        model.addAttribute("providerList",providerList);
        model.addAttribute("bill",bill);
        return "/jsp/bill/billview";
    }


    /**
     * 添加 订单
     */
    @RequestMapping("/toAddBill")
    public String toAddBill(){
        return "/jsp/bill/billadd";
    }

    /**
     * 异步获取供应商,列表
     */
    @RequestMapping("/getProList")
    public void getProList(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        List<SmbmsProvider> providerList = providerService.getProviderList(null);
        jsonObject.put("providerList",providerList);
        out.println(jsonObject);
    }

    /**
     * 添加 订单
     * @return
     */
    @RequestMapping("/addBill")
    public String addBill(HttpServletRequest request) throws UnsupportedEncodingException {
        SmbmsBill smbmsBill = bU(request);
        smbmsBill.setCreatedBy(Integer.valueOf(request.getParameter("id")));
        smbmsBill.setCreationDate(new Date());
        billService.add(smbmsBill);
        return "redirect:/toBill";
    }

    /**
     * go 修改订单
     */
    @RequestMapping("toBillUpdate/{billid}")
    public String toBillUpdate(@PathVariable("billid") String id,Model model){
        SmbmsBill bill = billService.getBillById(id);
        model.addAttribute("bill",bill);
        return "/jsp/bill/billmodify";
    }

    /**
     * 修改
     * @param request
     * @return
     */
    @RequestMapping("/billUpdate")
     public String billUpdate(HttpServletRequest request) throws UnsupportedEncodingException {
        SmbmsBill smbmsBill = bU(request);
        Integer bid = Integer.valueOf(request.getParameter("bid"));
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        smbmsBill.setId(bid);
        smbmsBill.setModifyBy(uid);
        smbmsBill.setModifyDate(new Date());
        billService.modify(smbmsBill);
        return "redirect:/toBill";
     }

    /**
     * 删除订单
     * @param id
     * @return
     */
     @RequestMapping("/deleteBill/{id}")
     public String deleteBill(@PathVariable("id") String id,HttpServletResponse response) throws IOException {
         PrintWriter out = response.getWriter();
         JSONObject jsonObject = new JSONObject();
         SmbmsBill billById = billService.getBillById(id);
         if (billById != null) {
             if (billById.getIsPayment() == 2) {
                 if ( billService.deleteBillById(id)>0) {
                     jsonObject.put(Constants.DElRESULT,"true");
                 }else {
                     jsonObject.put(Constants.DElRESULT,"false");
                 }
             }else {
                 jsonObject.put(Constants.DElRESULT,"isNo");
             }
         }else {
             jsonObject.put(Constants.DElRESULT,"notexist");
         }
         out.println(jsonObject);
         out.close();
        return "redirect:/toBill";
     }

    /**
     * 验证 该billCode 是否可用
     */
    @RequestMapping("/billCode/{billCode}")
     public void billCode(@PathVariable("billCode") String billCode ,HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        Map ma = new HashMap();
         ma.put("billCode",billCode);
        List<SmbmsBill> billList = billService.getBillList(ma);
        if (billList.size()>0){
           jsonObject.put("billCode","true");
        }
        out.println(jsonObject);
        out.close();
    }

    /**
     * bill 公用属性
     * @param request
     * @return
     */
    public SmbmsBill bU(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String billCode = request.getParameter("billCode");
        String productName = request.getParameter("productName");
        String productUnit = request.getParameter("productUnit");
        String productDesc = request.getParameter("productDesc");
        Double productCount = Double.valueOf(request.getParameter("productCount"));
        Double totalPrice = Double.valueOf(request.getParameter("totalPrice"));
        Integer providerId = Integer.valueOf(request.getParameter("providerId"));
        Integer isPayment = Integer.valueOf(request.getParameter("isPayment"));

        SmbmsBill smbmsBill = new SmbmsBill();
        smbmsBill.setBillCode(billCode);
        smbmsBill.setProductDesc(productDesc);
        smbmsBill.setProductName(productName);
        smbmsBill.setProductUnit(productUnit);
        smbmsBill.setProductCount(productCount);
        smbmsBill.setTotalPrice(totalPrice);
        smbmsBill.setProviderId(providerId);
        smbmsBill.setIsPayment(isPayment);
        return smbmsBill;
    }

}
