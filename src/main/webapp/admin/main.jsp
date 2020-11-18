<%--
  Created by IntelliJ IDEA.
  User: tianj
  Date: 2020/11/13
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout" data-options="fit:true">

    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="wu-toolbar">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="openresetpasswd()" plain="true">重置密码</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" plain="true">刷新</a>

            </div>

        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid" toolbar="#wu-toolbar"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->
<div id="wu-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px; padding:10px;">
    <form id="wu-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">登录名:</td>
                <td><input type="text" name="login_name" class="wu-text"  /></td>
            </tr>
            <tr>
                <td width="60" align="right">姓 名:</td>
                <td><input type="text" name="user_name" class="wu-text"  /></td>
            </tr>
            <tr>
                <td align="right">密 码:</td>
                <td><input type="text" name="password" class="wu-text"  /></td>
            </tr>

        </table>
    </form>
</div>
<!-- 重置密码 -->
<div id="reset-passwd-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px; padding:10px;">
    <form id="reset-passwd-form" method="post">
        <table>
            <tr>
                <td align="right">用户ID:</td>
                <td><input type="text" name="reset-password-id" id="reset-passwd-id" class="wu-text" /></td>
            </tr>

            <tr>
                <td align="right">新密码:</td>
                <td><input type="text" name="reset-password" id="reset-password" class="wu-text"  /></td>
            </tr>

        </table>
    </form>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    /**
     * Name 载入菜单树
     */
    $('#wu-category-tree').tree({
        url:'temp/menu.php',
        onClick:function(node){
            alert(node.text);
        }
    });

    /**
     * Name 添加记录
     */
    function add(){
        $('#wu-form').form('submit', {
            url:'/manage/add',
            success:function(data){
                if(data){
                    $.messager.alert('信息提示',data,'info');
                    $('#wu-dialog').dialog('close');
                    reload();
                }
                else
                {
                    $.messager.alert('信息提示','提交失败！','info');
                }
            }
        });
    }

    /**
     * Name 打开添加窗口
     */
    function openAdd(){
        $('#wu-form').form('clear');
        $('#wu-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加用户",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: add
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#wu-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 删除记录
     */
    function remove(){
        $.messager.confirm('信息提示','确定要删除该用户？', function(result){
            if(result){
                var items = $('#wu-datagrid').datagrid('getSelected');
                var deleteId=items.id;
                $.ajax({
                    url:'/manage/deleteUser',
                    data:{"deleteId":deleteId},
                    success:function(data){
                        if(data){
                            $.messager.alert('信息提示','删除成功！','info');
                            reload();
                        }
                        else
                        {
                            $.messager.alert('信息提示','删除失败！','info');
                        }
                    }
                });
            }
        });
    }
    /**
     * Name 打开重置密码
     */
    function openresetpasswd(){
        $('#reset-passwd-form').form('clear');
         var items = $('#wu-datagrid').datagrid('getSelected');
         $('#reset-passwd-id').val(items.id);
        $('#reset-passwd-dialog').dialog({
            closed: false,
            modal:true,
            title: "重置密码",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: resetpasswd
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#reset-passwd-dialog').dialog('close');
                }
            }]
        });
    }
    /**
     * Name 重置密码
     */
    function resetpasswd(){
        $('#reset-passwd-form').form('submit', {
            url:'/manage/resetpwd',
            success:function(data){
                if(data){
                    $.messager.alert('信息提示',data,'info');
                    $('#wu-dialog').dialog('close');
                }
                else
                {
                    $.messager.alert('信息提示','提交失败！','info');
                }
            }

        });
    }
    /**
     * Name 刷新
     */
    function reload(){
        $('#wu-datagrid').datagrid('reload');    // 重新载入当前页面数据
    }



    /**
     * Name 分页过滤器
     */
    function pagerFilter(data){
        if (typeof data.length == 'number' && typeof data.splice == 'function'){// is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage:function(pageNum, pageSize){
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh',{pageNumber:pageNum,pageSize:pageSize});
                dg.datagrid('loadData',data);
            }
        });
        if (!data.originalRows){
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }

    /**
     * Name 载入数据
     */
    $('#wu-datagrid').datagrid({
        url:'/manage/getUsers',
        loadFilter:pagerFilter,
        rownumbers:true,
        singleSelect:true,
        pageSize:20,
        pagination:true,
        //multiSort:true,
        fitColumns:true,
        fit:true,
        columns:[[
            { checkbox:true},
            { field:'id',title:'ID',width:100,sortable:true},
            { field:'login_name',title:'登录名',width:100,sortable:true},
            { field:'user_name',title:'姓名',width:500}
        ]]
    });
</script>
