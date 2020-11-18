<%--
  Created by IntelliJ IDEA.
  User: tianj
  Date: 2020/11/13
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  import="com.hld.bean.User" language="java" %>
<%
    Object o=session.getAttribute("user");
    User user=(User)o;
    String name=user.getUser_name();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>恒利德公司漏扫设备管理系统</title>
    <link rel="icon" href="../static/themes/icons/bell.png" type="image/x-icon">
    <link rel="stylesheet" href="../static/themes/dafult/easyui.css">
    <link rel="stylesheet" href="../static/themes/dafult/wu.css">
    <link rel="stylesheet" href="../static/themes/icon.css">
    <script src="../static/js/jquery-1.8.0.min.js"></script>
    <script src="../static/js/jquery.easyui.min.js"></script>
    <script src="../static/js/easyui-lang-zh_CN.js"></script>

</head>
<body class="easyui-layout">
<!-- begin of header -->
<div class="wu-header" data-options="region:'north',border:false,split:true">
    <div class="wu-header-left">
        <h1>系统管理</h1>
    </div>
    <div class="wu-header-right">
        <p><%=name %></strong>，欢迎您！</p>
        <p><a href="#" onclick="openpasswd()">修改密码</a>|<a href="#" onclick="layout()">安全退出</a></p>
    </div>
</div>
<!-- end of header -->
<!-- begin of sidebar -->
<div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'">
    <div class="easyui-accordion" data-options="border:false,fit:true">

        <div title="用户管理" data-options="iconCls:'icon-application-form-edit'" style="padding:5px;">

        </div>


    </div>
</div>

<!-- 修改密码 -->
<div id="passswd-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px; padding:10px;">
    <form id="passwd-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">修改密码:</td>
                <td><input type="text" id="passwd" name="name" class="wu-text"  /></td>
            </tr>

        </table>
    </form>
</div>
<!-- end of sidebar -->
<!-- begin of main -->
<div class="wu-main" data-options="region:'center'">
    <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">
        <div title="首页" data-options="href:'/admin/main.jsp',closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
    </div>
</div>
<!-- end of main -->
<!-- begin of footer -->
<div class="wu-footer" data-options="region:'south',border:true,split:true">
    &copy; 2020LiangBin All Rights Reserved
</div>
<!-- end of footer -->
<script type="text/javascript">
    $(function(){
        $('.wu-side-tree a').bind("click",function(){
            var title = $(this).text();
            var url = $(this).attr('data-link');
            var iconCls = $(this).attr('data-icon');
            var iframe = $(this).attr('iframe')==1?true:false;
            addTab(title,url,iconCls,iframe);
        });
    })

    // /**
    //  * Name 载入树形菜单
    //  */
    // $('#wu-side-tree').tree({
    //     url:'temp/menu.php',
    //     cache:false,
    //     onClick:function(node){
    //         var url = node.attributes['url'];
    //         if(url==null || url == ""){
    //             return false;
    //         }
    //         else{
    //             addTab(node.text, url, '', node.attributes['iframe']);
    //         }
    //     }
    // });

    /**
     * Name 选项卡初始化
     */
    $('#wu-tabs').tabs({
        tools:[{
            iconCls:'icon-reload',
            border:false,
            handler:function(){
                $('#wu-datagrid').datagrid('reload');
            }
        }]
    });

    /**
     * Name 添加菜单选项
     * Param title 名称
     * Param href 链接
     * Param iconCls 图标样式
     * Param iframe 链接跳转方式（true为iframe，false为href）
     */
    function addTab(title, href, iconCls, iframe){
        var tabPanel = $('#wu-tabs');
        if(!tabPanel.tabs('exists',title)){
            var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
            if(iframe){
                tabPanel.tabs('add',{
                    title:title,
                    content:content,
                    iconCls:iconCls,
                    fit:true,
                    cls:'pd3',
                    closable:true
                });
            }
            else{
                tabPanel.tabs('add',{
                    title:title,
                    href:href,
                    iconCls:iconCls,
                    fit:true,
                    cls:'pd3',
                    closable:true
                });
            }
        }
        else
        {
            tabPanel.tabs('select',title);
        }
    }
    /**
     * Name 移除菜单选项
     */
    function removeTab(){
        var tabPanel = $('#wu-tabs');
        var tab = tabPanel.tabs('getSelected');
        if (tab){
            var index = tabPanel.tabs('getTabIndex', tab);
            tabPanel.tabs('close', index);
        }
    }

    /**
     * Name 打开添加窗口
     */
    function openpasswd(){
        $('#passwd-form').form('clear');
        $('#passswd-dialog').dialog({
            closed: false,
            modal:true,
            title: "修改密码",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    $.ajax({
                        type: "POST",
                        url: "/manage/changPwd",
                        data: "password="+$('#passwd').val(),
                        success: function(msg){
                            $.messager.alert('消息',msg);
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#passswd-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 退出
     */
   function layout(){
        $.ajax({
            type: "POST",
            url: "/manage/layout",
            success: function(msg){
                window.location="/login.jsp";
            }
        });
   }

</script>
</body>
</html>
