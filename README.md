# official-account-demo
docker打包时imageName不能包含大写字母 否则报错：
 I/O exception (java.io.IOException) caught when processing request to {}->unix://localhost:80: Broken pipe
 
 
##  菜单信息
···
 {
     "button":[
     {	
          "type":"click",
          "name":"vpn",
          "key":"vpn_get"
      },
      {
           "name":"测试菜单",
           "sub_button":[
           {	
               "type":"view",
               "name":"soso",
               "url":"http://www.soso.com/"
            },
            {
               "type":"view",
               "name":"baidu",
               "url":"http://www.baidu.com/"
             }]
       }]
 }
 ···
