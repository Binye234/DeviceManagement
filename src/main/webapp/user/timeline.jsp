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
        <div id="time-toolbar">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="timereload()" plain="true">刷新</a>
            </div>

        </div>
        <!-- End of toolbar -->
        <table id="time-datagrid" toolbar="#time-toolbar"></table>
    </div>
</div>

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
     * Name 刷新
     */
    function timereload(){
        $('#time-datagrid').datagrid('reload');    // 重新载入当前页面数据
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
    $('#time-datagrid').datagrid({
        url:'/data/getAllData',
        loadFilter:pagerFilter,
        rownumbers:true,
        singleSelect:true,
        pageSize:20,
        pagination:true,
        //multiSort:true,
        fitColumns:true,
        fit:true,
        columns:[[
            { field:'id',title:'ID',width:100,sortable:true},
            { field:'userName',title:'姓名',width:100,sortable:true},
            { field:'reason',title:'预约内容',width:500},
            { field:'date',title:'借出时间',width:200},

        ]]
    });
</script>