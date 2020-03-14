package com.jbit.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jbit.pojo.SmbmsBill;
import com.jbit.pojo.SmbmsProvider;

import com.jbit.service.Bill.BillService;
import com.jbit.service.Provider.ProviderService;

import com.jbit.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class ProviderController {

     @Autowired
     private ProviderService providerService;
     @Autowired
     private BillService billService;
    /**
     * 前往供应商页面
     * 进行分页展示
     * @return
     */
    @RequestMapping("/toProvider")
    public String toProvider(Model model){
        Page<SmbmsProvider> page =
                PageHelper.startPage(1,5).doSelectPage(() -> providerService.getProviderList(null));
        List<SmbmsProvider> providerList = page.getResult();
        int sumNum=1;
        if (page.getPages() > 0 ) {
            sumNum = page.getPages();
        }
        model.addAttribute("totalPageCount",page.getPageSize());
        model.addAttribute("totalCount",page.getTotal());
        model.addAttribute("currentPageNo",page.getPageNum());
        model.addAttribute("totalPageCount",sumNum);
        model.addAttribute("providerList",providerList);
        return "/jsp/provider/providerlist";
    }

    /**
     * 分页请求数据
     */
    @RequestMapping ("/providerList")
     public String providerList(HttpServletRequest request, HttpServletResponse response){
        String queryProCode = request.getParameter("queryProCode");
        String queryProName = request.getParameter("queryProName");
        String pageIndex = request.getParameter("pageIndex");
         Map map = new HashMap();
         map.put("proCode",queryProCode);
         map.put("proName",queryProName);
        //当前页码
        int currentPageNo = 1;
        //查询用户列表

        if (pageIndex != null) {
            currentPageNo = Integer.valueOf(pageIndex);
        }
        System.out.println(pageIndex);
        Page<SmbmsProvider> page =
                PageHelper.startPage(currentPageNo,5).doSelectPage(() -> providerService.getProvideLike(map));
        List<SmbmsProvider> providerList = page.getResult();
        int sumNum=1;
        if (page.getPages() > 0 ) {
            sumNum = page.getPages();
        }
        request.setAttribute("totalPageCount",page.getPageSize());
        request.setAttribute("totalCount",page.getTotal());
        request.setAttribute("currentPageNo",currentPageNo);
        request.setAttribute("totalPageCount",sumNum);
        request.setAttribute("providerList",providerList);
        request.setAttribute("queryProCode",queryProCode);
        request.setAttribute("queryProName",queryProName);
        return "/jsp/provider/providerlist";
    }

    /**
     * go  添加供应商页面
     * @return
     */
    @RequestMapping("/toProviderAdd")
    public String toProviderAdd(){
        return "/jsp/provider/provideradd";
    }

    /**
     * 验证Code是否可用
     * @return
     */
    @RequestMapping("/pCode")
    public void pCode(String proCode,HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        Map map = new HashMap();
        map.put("proCode",proCode);
        if (proCode != null && proCode != "") {
            if (providerService.getProviderList(map).size()>0){
                json.put("proCode","false");
            }else{
                json.put("proCode","true");
            }
        }else{
            json.put("proCode","error");
        }
        out.println(json);
        out.close();
    }
    /**
     * 验证proName是否可用
     * @return
     */
    @RequestMapping("/proName")
    public void proName(String proName,HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        Map map = new HashMap();
        map.put("proName",proName);
        if (proName != null && proName != "") {
            if (providerService.getProviderList(map).size()>0){
                json.put("proName","false");
            }else{
                json.put("proName","true");
            }
        }else{
            json.put("proName","error");
        }
        out.println(json);
        out.close();
    }


    /**
     * 添加 供应商
     * @return
     */
    @RequestMapping("/providerAdd")
    public String providerAdd(HttpServletRequest request) throws UnsupportedEncodingException {
        SmbmsProvider smbmsProvider = pU(request);
        Integer id = Integer.valueOf(request.getParameter("id"));
        smbmsProvider.setCreatedBy(id);
        smbmsProvider.setCreationDate(new Date());
         if (providerService.add(smbmsProvider)>0){
             return "redirect:/toProvider";
         }else {
             return "redirect:/jsp/provider/provideradd";
         }
    }

    /**
     * go 修改供应商页面
     * @param proid
     * @param model
     * @return
     */
    @RequestMapping("/toProviderUpdate")
    public String toProviderUpdate(String proid,Model model){
          pV(proid, model);
        return "/jsp/provider/providermodify";
    }
    /**
     * 修改
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/providerUpdate")
    public String providerUpdate(HttpServletRequest request) throws UnsupportedEncodingException {
        SmbmsProvider smbmsProvider = pU(request);
        Integer id =  Integer.valueOf(request.getParameter("id"));
        Integer modifyBy =  Integer.valueOf(request.getParameter("modifyBy"));
        smbmsProvider.setId(id);
        smbmsProvider.setModifyBy(modifyBy);
        smbmsProvider.setModifyDate(new Date());
        providerService.modify(smbmsProvider);
        return "redirect:/toProvider";
    }
    /**
     * go 供应商详情
     * @return
     */
    @RequestMapping("/toProviderView")
    public String toProviderView(String proid,Model model){
         pV(proid, model);
        return "/jsp/provider/providerview";
     }

    /**
     * 删除供应商
     * @param proid
     * @param response
     * @return
     * @throws IOException
     */
     @RequestMapping("/deleteProvider")
     public String deleteProvider(String proid,HttpServletResponse response) throws IOException {
         PrintWriter out = response.getWriter();
         JSONObject jsonObject = new JSONObject();
         Map m = new HashMap();
         m.put("id",proid);
         List<SmbmsProvider> providerList = providerService.getProviderList(m);
         SmbmsProvider smbmsProvider = providerList.get(0);
         if (smbmsProvider != null) {
             List<SmbmsBill> smbmsBills = billService.smbmsBillByProId(proid);
             if (smbmsBills.size()< 1 ){
                 if ( providerService.deleteProviderById(proid)>0) {
                     jsonObject.put(Constants.DElRESULT, "true");
                 }
                 else {
                     jsonObject.put(Constants.DElRESULT, "false");
                 }
             }else {
                 jsonObject.put("size",smbmsBills.size());
                 jsonObject.put(Constants.DElRESULT,"notexist");
             }
         }else {
             jsonObject.put(Constants.DElRESULT,"null");
         }
         out.println(jsonObject);
         out.close();
        return "redirect:/toProvider";
     }


    /**
     * 共用模板 Provider
     * @param request
     * @return SmbmsProvider
     */
    public SmbmsProvider pU(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String proCode =  request.getParameter("proCode");
        String proName =  request.getParameter("proName");
        String proContact =  request.getParameter("proContact");
        String proPhone = request.getParameter("proPhone");
        String proAddress = request.getParameter("proAddress");
        String proFax = request.getParameter("proFax");
        String proDesc = request.getParameter("proDesc");
        SmbmsProvider smbmsProvider =  new SmbmsProvider();
        smbmsProvider.setProCode(proCode);
        smbmsProvider.setProName(proName);
        smbmsProvider.setProContact(proContact);
        smbmsProvider.setProPhone(proPhone);
        smbmsProvider.setProFax(proFax);
        smbmsProvider.setProAddress(proAddress);
        smbmsProvider.setProDesc(proDesc);
        return smbmsProvider;
    }

    /**
     *  id对象返回视图 共享
     * @param proid
     * @param model
     * @return
     */
     private void pV(String proid,Model model){
         Map m = new HashMap();
         m.put("id",proid);
         List<SmbmsProvider> providerList = providerService.getProviderList(m);
         SmbmsProvider smbmsProvider = providerList.get(0);
         model.addAttribute("provider",smbmsProvider);
     }

}
