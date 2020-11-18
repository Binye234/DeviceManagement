<%--
  Created by IntelliJ IDEA.
  User: tianj
  Date: 2020/11/13
  Time: 13:46
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
<%--            <tr>--%>
<%--                <td width="60" align="right">姓 名:</td>--%>
<%--                <td><input type="text" name="name" class="wu-text"  /></td>--%>
<%--            </tr>--%>
            <tr>
                <td align="right">借出时间:</td>
                <td><input class="easyui-datebox" style="width: 268px" name="begin_time" ></td>
            </tr>
            <tr>
                <td valign="top" align="right">内 容:</td>
                <td><textarea name="reason" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    /**
     * Name 载入菜单树
     */
    // $('#wu-category-tree').tree({
    //     url:'temp/menu.php',
    //     onClick:function(node){
    //         alert(node.text);
    //     }
    // });

    /**
     * Name 添加记录
     */
    function add(){
        $('#wu-form').form('submit', {
            url:'/data/addInfo',
            success:function(data){
                if(data){
                    $.messager.alert('信息提示',data,'info');
                    $('#wu-dialog').dialog('close');
                }
                else
                {
                    $.messager.alert('信息提示','提交失败！','info');
                }
                $('#wu-datagrid').datagrid('reload');
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
            title: "添加信息",
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
        $.messager.confirm('信息提示','确定要删除该记录？', function(result){
            if(result){
                var items = $('#wu-datagrid').datagrid('getSelected');
                var ids = [];
                $(items).each(function(){
                    ids.push(this.productid);
                });
                //alert(ids);return;
                $.ajax({
                    url:'/data/deleteInfo',
                    data:{'deleteId':items.id},
                    success:function(data){
                        if(data){
                            $.messager.alert('信息提示','删除成功！','info');
                        }
                        else
                        {
                            $.messager.alert('信息提示','删除失败！','info');
                        }
                        $('#wu-datagrid').datagrid('reload');
                    }
                });
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
        url:'/data/getInfo',
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
            { field:'userName',title:'姓名',width:100,sortable:true},
            { field:'reason',title:'预约内容',width:500},
            { field:'date',title:'借出时间',width:200},

        ]]
    });
</script>