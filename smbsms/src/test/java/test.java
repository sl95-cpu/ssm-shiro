import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jbit.mapper.SmbmsBillMapper;
import com.jbit.mapper.SmbmsProviderMapper;
import com.jbit.mapper.SmbmsRoleMapper;
import com.jbit.mapper.SmbmsUserMapper;
import com.jbit.pojo.SmbmsBill;
import com.jbit.pojo.SmbmsProvider;
import com.jbit.pojo.SmbmsRole;
import com.jbit.pojo.SmbmsUser;
import com.jbit.service.User.SmbmsUserService;
import com.jbit.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;


public class test {

    @Test
    public void t(){
        System.out.println(new Date());
    }
/*
    @Test
    public void ts(){
        SqlSession sqlSession = SqlSessionUtil.createSqlSession();
        Map map = new HashMap();
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        map.put("ids",list);
        SmbmsRoleMapper mapper = sqlSession.getMapper(SmbmsRoleMapper.class);
        List<SmbmsRole> smbmsRoles = mapper.selectRole(map);
        for (SmbmsRole smbmsRole : smbmsRoles) {
            System.out.println(smbmsRole.getRoleName());
            for (SmbmsUser smbmsUser : smbmsRole.getSmbmsUsers()) {
                System.out.println(smbmsUser.getUserName());
            }
        }
    }

   @Test
     public void test3(){
       SqlSession sqlSession = SqlSessionUtil.createSqlSession();
      // SmbmsBillMapper mapper = sqlSession.getMapper(SmbmsBillMapper.class);
       // Map map = new HashMap();
     //   map.put("id",2);
      //map.put("name","洗");
       // map.put("isPayment",2);
       */
/*List<SmbmsBill> smbmsBills = mapper.allBill2(1);
       for (SmbmsBill bill : smbmsBills) {
           System.out.println(bill.getProductName());
       }*//*

*/
/*     SmbmsProviderMapper mapper = sqlSession.getMapper(SmbmsProviderMapper.class);
    *//*
*/
/*   //第二种，Mapper接口方式的调用，推荐这种使用方式
       Page<Object> objects = PageHelper.startPage(2, 3);*//*
*/
/*
       //jdk8 lambda用法
       Map m = new HashMap();
      // m.put("id",1);
       m.put("name","北");
       List<SmbmsProvider> smbmsProviders = mapper.allProviderBill(1);
       for (SmbmsProvider smbmsProvider : smbmsProviders) {
           System.out.println(smbmsProvider.getProName());
       }*//*

      SmbmsBillMapper mapper = sqlSession.getMapper(SmbmsBillMapper.class);
       //SmbmsProviderMapper mapper = sqlSession.getMapper(SmbmsProviderMapper.class);
       Map map = new HashMap();
    */
/*List <Integer> list = new ArrayList<Integer>();
          list.add(1);
          list.add(2);
          list.add(3);
       map.put("list",list);*//*

       map.put("from",0);
       map.put("size",5);
       List<SmbmsBill> smbmsBills = mapper.page(map);
       for (SmbmsBill smbmsBill : smbmsBills) {
           System.out.println(smbmsBill);
       }
   }

   @Test
     public void  t4(){
       SqlSession sqlSession = SqlSessionUtil.createSqlSession();
       SmbmsBillMapper mapper = sqlSession.getMapper(SmbmsBillMapper.class);
       List<SmbmsBill> mm = mapper.mm("1");
       System.out.println(mm);
   }

   @Test
    public void t5(){
      */
/* int[] array={30,32,36,38,40,62};
       int [] b = new int[6];
       int sum = 0;
       for (int i = 0; i <array.length ; i++) {
           sum += array[i];
       }
       for (int i = 0; i < b.length; i++) {
            b[i] =sum-array[i];
       }
       for (int d: b) {
           if(d%3==0)
               System.out.println(sum-d);
       }*//*

   }

   @Test
   public void  t6(){
       System.out.println(strStr("sssss", ""));
   }
 //实现 strStr();
 public  int strStr(String haystack, String needle) {
          int a  = haystack.indexOf(needle);
       return a;
 }

   //爱心
    public void like(){
        //爱心
        for(float y = (float) 1.5;y>-1.5;y -=0.1)  {
            for(float x= (float) -1.5;x<1.5;x+= 0.05){
                float a = x*x+y*y-1;
                if((a*a*a-x*x*y*y*y)<=0.0)  {
                    System.out.print("*");
                }
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
   //整数反转
   public int reverse(int x) {
       int ans = 0;
       while (x != 0) {
           int pop = x % 10;
         */
/*  if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7))
               return 0;
           if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8))
               return 0;*//*

           ans = ans * 10 + pop;
           x /= 10;
       }
       return ans;
   }
   //罗马数字转转正数
    public int romanToInt(String s) {
        Map<String, Integer> map = new HashMap();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);
        int ans = 0;
        for(int i = 0;i < s.length();) {
            if(i + 1 < s.length() && map.containsKey(s.substring(i, i+2))) {
                ans += map.get(s.substring(i, i+2));
                i += 2;
            } else {
                ans += map.get(s.substring(i, i+1));
                i ++;
            }
        }
        return ans;
    }
   //两数之和
   public int[] twoSum(int[] nums, int target) {
       int [] index = new int[2];
       for(int i=0;i<nums.length-1;i++){
           for(int j=i+1;j<nums.length;j++){
               int sum = nums[i]+nums[j];
               System.out.println(sum);
               if(sum==target){
                   index[0]=i;
                   index[1]=j;
               }
           }
       }
       return index;
   }
   //整数反转
   public  static  boolean s(String s){
       char [] arr = s.toCharArray();
       String ss = "";
       for (int i = arr.length-1; i >=0; i--) {
           System.out.println(i);
           ss+=arr[i];
       }
       System.out.println(ss);
       if (ss.equals(s)){
           return true;
       }else{
           return false;
       }
   }
*/

}