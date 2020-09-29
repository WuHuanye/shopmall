layui.use(['table','layer','jquery','form'], function(){
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;
    var form = layui.form;

    table.render({
        elem: '#test'
        ,url:'/admin/record/loginRecordDate'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }]
        // ,id:"tables"
        ,title: '用户数据表'
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{type : "numbers", title : '序号',align : 'center',sort : true, fixed: 'left',width : 70}
            ,{field:'id', title:'ID', width:80, fixed: 'left', unresize: true, sort: true,hide:true}
            ,{field:'username', title:'用户名', width:150, edit: 'text'}
            ,{field:'nickName', title:'昵称', width:150, edit: 'text'}
            ,{field:'sex', title:'性别', width:80, edit: 'text', sort: true,
                templet: function(obj){if(obj.sex == 1){return '男'}else{return '女'}}
            }
            ,{field:'headUrl', title:'头像',width:150,height:200,
                templet:function (obj) {
                    return "<div><img style='width:90px;height:60px' src='" + obj.headUrl + "'/></div>";
                }
            }
            ,{field:'role', title:'角色', width:120,
                templet: function(obj){if(obj.role == 0){return '用户'}else{return '管理员'}}
            }
            ,{field:'loginDate', title:'登录时间', width:190, sort: true}
            // ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
        ]]
        ,limit : 20
        ,limits : [20,30,40,50]
        ,page: true
    });
    $(document).on("click",".search_btn",function () {
        table.reload("test",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                nickName: $("#nickName").val(),  //查询内容，代码生成后手动修改
            }
        })
    });
});