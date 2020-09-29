layui.use(['table','layer','jquery','form'], function(){
        var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;
        var form = layui.form;

        table.render({
            elem: '#test'
            ,url:'/admin/user/data'
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
                ,{type : "numbers", title : '序号',align : 'center',sort : true, fixed: 'left',width : 50}
                ,{field:'userId', title:'ID', width:80, fixed: 'left', unresize: true, sort: true,hide:true}
                ,{field:'username', title:'用户名', width:120, edit: 'text'}
                ,{field:'sex', title:'性别', width:80, edit: 'text',
                    templet: function(obj){if(obj.sex == 1){return '男'}else{return '女'}}
                }
                ,{field:'headUrl', title:'头像',width:150,height:200,
                    templet:function (obj) {
                        return "<div><img style='width:90px;height:60px' src='" + obj.headUrl + "'/></div>";
                    }
                }
                ,{field:'nickName', title:'昵称', width:100}
                ,{field:'email', title:'邮箱', width:150, edit: 'text', templet: function(res){
                        return '<em>'+ res.email +'</em>'
                    }}
                ,{field:'roleId', title:'角色', width:100,
                    templet: function(obj){if(obj.roleId == 0){return '用户'}else{return '<font style="color: #ff6507">管理员</font>'}}
                }
                ,{field:'birthday', title:'生日', width:120}
                ,{field:'address', title:'地址', width:80}
                ,{field:'isDeleted', title:'是否被删除', width:120,hide:true}
                ,{field:'createTime', title:'创建时间', width:100, sort: true}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]
            ,limit : 20
            ,limits : [10,15,20,25]
            ,page: true
        });
        $(document).on("click",".search_btn",function () {
            table.reload("test",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    username: $("#username2").val(),  //查询内容，代码生成后手动修改
                    nickName:$("#nickName2").val()
                }
            })

        });
        $(document).on("click","#addUser",function () {
            var index = layer.open({
                type : 2,
                area: ['50%', '60%'],
                title: "添加用户",
                closeBtn: 0,
                shadeClose: true,
                skin: 'yourclass',
                content: '/admin/user/form'
            });
        });

        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;

                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的要删除吗?', function(index){
                    $.ajax({
                        url:"/admin/user/delete",
                        data:{userId:data.userId},
                        type:'post',
                        dataType: "text", //return dataType: text or json
                        success:function (res) {
                            if (res=="success"){
                                obj.del();
                                table.render();
                            } else {
                                alert("删除失败")
                            }
                        },
                        error:function (json) {
                            alert("网络出错")
                        }
                    });
                    layer.close(index);
                });
            } else if(obj.event === 'resetPsw'){
                $.ajax({
                    url:"/admin/user/resetPsw",
                    data:{userId:data.userId},
                    type:'post',
                    dataType: "text", //return dataType: text or json
                    success:function (res) {
                        if (res=="success"){
                            alert("重置成功")
                            table.render();
                        } else {
                            alert("重置失败")
                        }
                    }
                });
            }
        });
    });