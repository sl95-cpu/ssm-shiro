package com.jbit.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jbit.pojo.SmbmsRole;
import com.jbit.pojo.SmbmsUser;
import com.jbit.service.Role.SmbmsRoleServie;
import com.jbit.service.User.SmbmsUserService;
import com.jbit.util.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//redirect
/*@SuppressWarnings("all")*/
@Controller
public class UserController {

    @Autowired
    @Qualifier("SmbmsUserServiceImpl")
    private SmbmsUserService smbmsUserService;
    @Autowired
    private SmbmsRoleServie smbmsRoleServie;


    /**
     * 登录
     * shiro 负责校验用户信息
     * @param userCode
     * @param userPassword
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(String userCode, String userPassword, Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录数据 获取令牌
        UsernamePasswordToken token = new UsernamePasswordToken(userCode, userPassword);
        try{
            subject.login(token); //使用令牌登录 ,没有异常执行
            return "jsp/frame";
        }catch (UnknownAccountException e){ //用户名不存在
            model.addAttribute(Constants.SYS_MESSAGE,"用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){//密码不存在
            model.addAttribute(Constants.SYS_MESSAGE,"密码错误");
            return "login";
        }
    }

    /**
     * shiro负责
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    /**
     * go 前往用户裂变页面
     * 并且携带信息
     * @param model
     * @return
     */
    @RequestMapping("/toUser")
    public String toUser(Model model){
        Map m = new HashMap();
        Page<SmbmsUser> page =
                PageHelper.startPage(1,5).doSelectPage(() -> smbmsUserService.alUser(m));
        List<SmbmsRole> smbmsRoles = smbmsRoleServie.allRole();
       //page.getPageNum()    当前页码
       //page.getPageSize()   页面大小
       // page.getPages()     总页数
       //page.getTotal()      总条数
        System.out.println(page.getResult());
        model.addAttribute("totalPageCount",page.getPageSize());
        model.addAttribute("totalCount",page.getTotal());
        model.addAttribute("currentPageNo",page.getPageNum());
        model.addAttribute("totalPageCount",page.getPages());
        model.addAttribute("userList",page.getResult());
        model.addAttribute("roleList",smbmsRoles);
        return "/jsp/user/userlist";
    }

    /**
     * 用户列表页面 查询功能
     * 分页
     * 职位
     * 名字模糊查询
     * 名字:null 职位:请选择 查询全部
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userlist")
    public String userlist(HttpServletRequest request, HttpServletResponse response){
        //当前页码
        int currentPageNo = 1;
        //查询用户列表
        String queryUserName = request.getParameter("queryname");
        String temp = request.getParameter("queryUserRole");
        String pageIndex = request.getParameter("pageIndex");

        if (pageIndex != null) {
            currentPageNo = Integer.valueOf(pageIndex);
        }
        if(queryUserName == "" || queryUserName ==null){
            queryUserName = null;
        }
        Integer userRole  = 0;
        if(temp != null && !temp.equals("")){
            userRole = Integer.parseInt(temp);
        }
        if (userRole == 0){
            userRole=null;
        }
        Map m = new HashMap();
        m.put("userName",queryUserName);
        m.put("userRole",userRole);
        Page<SmbmsUser> page =
                PageHelper.startPage(currentPageNo,5).doSelectPage(() -> smbmsUserService.alUser(m));
        List<SmbmsRole> smbmsRoles = smbmsRoleServie.allRole();
        int sumNum=1;
        if (page.getPages() > 0 ) {
            sumNum = page.getPages();
        }
        request.setAttribute("totalPageCount",page.getPageSize());
        request.setAttribute("totalCount",page.getTotal());
        request.setAttribute("currentPageNo",currentPageNo);
        request.setAttribute("totalPageCount",sumNum);
        request.setAttribute("userList",page.getResult());
        request.setAttribute("roleList",smbmsRoles);
        request.setAttribute("queryUserRole",userRole);
        request.setAttribute("queryUserName",queryUserName);
        return "/jsp/user/userlist";
    }


    /**
     * go 添加用户页面
     * @param model
     * @return
     */
    @RequestMapping("/toAddUser")
    public String toAddUser(Model model){
        List<SmbmsRole> smbmsRoles = smbmsRoleServie.allRole();
        return "/jsp/user/useradd";
    }

    /**
     * 添加用户前
     * 1.异步获取role信息
     * 检测 userCode是否存在
     */
    @RequestMapping("/uCode")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String userCode = request.getParameter("userCode");
        PrintWriter out = response.getWriter();
        JSONObject json =  new JSONObject();
        List<SmbmsRole> smbmsRoles = smbmsRoleServie.allRole();
        SmbmsUser user = smbmsUserService.getLoginUser(userCode);
        if(userCode != null){
            if (user!=null){
                json.put(Constants.USER_CODE,"false");
            }else{
                json.put(Constants.USER_CODE,"true");
            }
        }else{
            json.put(Constants.USER_CODE,"null");
        }

        json.put("data",smbmsRoles);
        out.println(json);
        out.close();
    }

    /**
     * 添加用户
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws ParseException
     */
    @RequestMapping("/addUser")
    public String addUser(HttpServletRequest request) throws UnsupportedEncodingException, ParseException {
        SmbmsUser smbmsUser = userAU(request);
        SmbmsUser user = (SmbmsUser)request.getSession().getAttribute("user");
        smbmsUser.setCreatedBy(user.getId());
        smbmsUser.setCreationDate(new Date());
        if(smbmsUserService.add(smbmsUser)>0) {
          return "redirect:/toUser";
      }else{
          return "/toAddUser";
      }
    }

    /**
     * 公用user属性操作 基本信息
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public SmbmsUser userAU(HttpServletRequest request) throws UnsupportedEncodingException, ParseException {
        request.setCharacterEncoding("UTF-8");
        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        Integer gender = Integer.valueOf(request.getParameter("gender"));
        //  SimpleDateFormat sdf =  new SimpleDateFormat( " yyyy-MM-dd" );
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        Integer userRole = Integer.valueOf(request.getParameter("userRole"));
        String address = request.getParameter("address");
        SmbmsUser smbmsUser = new SmbmsUser();
        smbmsUser.setUserCode(userCode);
        smbmsUser.setUserName(userName);
        smbmsUser.setUserPassword(userPassword);
        smbmsUser.setGender(gender);
        smbmsUser.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        smbmsUser.setPhone(phone);
        smbmsUser.setUserRole(userRole);
        smbmsUser.setAddress(address);
        return smbmsUser;
    }

   @RequestMapping("/userView")
    public String userView(String uid,Model model){
      user(uid,model);
      return "/jsp/user/userview";
  }

    /**
     * 公用视图
     * @param uid
     * @param model
     */
    public void user(String  uid , Model model){
        Integer uuid = 1;
        System.out.println(uid);
        if (uid != null){
            uuid = Integer.valueOf(uid);
        }
        SmbmsUser user = smbmsUserService.getUserById(uuid);
        List<SmbmsRole> smbmsRoles = smbmsRoleServie.allRole();
        model.addAttribute("roleList",smbmsRoles);
        model.addAttribute("users",user);
    }


    /**
     * 删除页面
     * @param uid
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/delUser")
    public String delUser(String uid,HttpServletResponse response) throws IOException {
       PrintWriter out = response.getWriter();
       JSONObject jsonObject = new JSONObject();
       Integer uuid = 0;
       System.out.println(uid);
        if (uid != null){
            uuid = Integer.valueOf(uid);
        }
        SmbmsUser user = smbmsUserService.getUserById(uuid);
       if (user != null) {
           if (smbmsUserService.deleteUserById(uuid)>0){
               jsonObject.put(Constants.DElRESULT,"true");
           }else {
               jsonObject.put(Constants.DElRESULT,"false");
           }
       }else {
           jsonObject.put(Constants.DElRESULT,"notexist");
       }
        out.println(jsonObject);
        out.flush();
        out.close();
        return "redirect:/toUser";
   }

    /**
     * go 用户修改页面
     * @param uid
     * @param model
     * @return
     */
     @RequestMapping("/toUpdateUser")
     public String toUpdateUser(String uid,Model model){
         user(uid,model);
         return "/jsp/user/usermodify";
     }

    /**
     * 修改用户
     * 并返回用户列表
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws ParseException
     */
     @RequestMapping("/updateUser")
     public String updaupdateUser(HttpServletRequest request) throws UnsupportedEncodingException, ParseException {
         SmbmsUser smbmsUser = userAU(request);
         Integer id = Integer.valueOf(request.getParameter("id"));
         Integer uid = Integer.valueOf(request.getParameter("uid"));
         smbmsUser.setId(id);
         smbmsUser.setModifyBy(uid);
         smbmsUser.setModifyDate(new Date());
         if ( smbmsUserService.modify(smbmsUser) > 0) {
             return "redirect:/toUser";
         }else{
             return "redirect:/updateUser";
         }
     }


    /**
     * go 密码修改页面
     * @return
     */
    @RequestMapping("/toPwd")
    public String toPwd(){
        return "/jsp/pwdmodify";
    }

    /**
     * 异步
     * 密码校验
     * 身份校验
     */
    @RequestMapping("/olpwd")
    public void olPwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String oldpassword = request.getParameter("oldpassword");
        System.out.println(oldpassword+1);
        PrintWriter out = response.getWriter();
        JSONObject json =  new JSONObject();
        SmbmsUser user = (SmbmsUser)request.getSession().getAttribute("user");
        String pwd = user.getUserPassword();
        if (user != null) {
            System.out.println(oldpassword != null);
            System.out.println(oldpassword != "");
            if (oldpassword != null && oldpassword != "") {
                if (oldpassword.equals(pwd)){
                    json.put(Constants.RESULT,"true");
                }else{
                    json.put(Constants.RESULT,"false");
                }
            }else {
                json.put(Constants.RESULT,"error");
            }
        }else{
            json.put(Constants.RESULT,Constants.USER_SESSION);
        }
        out.println(json);
        out.flush();
        out.close();
    }

    /**
     * 修改密码
     * 成功时候返回登录页面
     * @return
     */
    @RequestMapping("/updatePwd")
    public String updatePwd(HttpServletRequest request){
        String newpassword = request.getParameter("newpassword");
        SmbmsUser user = (SmbmsUser)request.getSession().getAttribute("user");
        user.setUserPassword(newpassword);
        smbmsUserService.modify(user);
        return  "redirect:/logout";
    }



















/*@RequestMapping("/userlist")
public String userlist(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //当前页码
    int currentPageNo = 1;
    //查询用户列表
    String queryUserName = request.getParameter("queryname");
    String temp = request.getParameter("queryUserRole");
    String pageIndex = request.getParameter("pageIndex");
    if (pageIndex != null) {
        currentPageNo = Integer.valueOf(pageIndex);
    }

    Page<SmbmsProvider> page =
            PageHelper.startPage(currentPageNo,5).doSelectPage(() -> smbmsUserService.alUser());
  //  List<SmbmsRole> smbmsRoles = smbmsRoleServie.allRole();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("totalPageCount",page.getPageSize());
    jsonObject.put("totalCount",page.getTotal());
    jsonObject.put("currentPageNo",page.getPageNum());
    jsonObject.put("totalPageCount",page.getPages());
    jsonObject.put("userList",page.getResult());
    PrintWriter out = response.getWriter();
    out.println(jsonObject);
    out.close();
    //jsonObject.put("roleList",smbmsRoles);
    return "/jsp/userlist";
}*/


}
