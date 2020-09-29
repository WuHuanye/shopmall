layui.use(['table','layer','upload','form'], function(){
    var table = layui.table;
    var $ = layui.jquery,
        form = layui.form,
        upload = layui.upload,
        layer = layui.layer; //独立版的layer无需执行这一句

    table.render({
        elem: '#test'
        ,url:'/admin/indexconf/indexgoodsConfig/data'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }]
        ,title: '数据表'
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field:'configId', title:'ID', width:80, fixed: 'left', unresize: true, sort: true,hide:true}
            ,{field:'configName', title:'配置名称', width:160, edit: 'text'}
            ,{field:'configType', title:'配置类型', width:150, edit: 'text',hide:true}
            ,{field:'goodsId', title:'关联商品', width:80, edit: 'text', sort: true}
            ,{field:'redirectUrl', title:'跳转地址', width:100,hide:true}
            ,{field:'configRank', title:'排序',width:150, sort: true}
            ,{field:'createUser', title:'创建人', width:160}
            ,{field:'createTime', title:'创建时间', width:120}
            ,{field:'updateUser', title:'更新人', width:120}
            ,{field:'updateTime', title:'更新时间', width:120}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}

        ]]
        ,page: true
    });
    $(document).on("click",".search_btn",function () {
        var configType = $("select[name='configType'] option:selected").val();
        // alert(configType)
        if(configType == ''||configType == null){
            configType = 0;
        }
        table.reload("test",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                configType:configType,
                // goodsName: $("#goodsName").val(),  //查询内容，代码生成后手动修改

            }
        })

    });
    $(".add").click(function () {
        var configId = 0;
        layer.open({
            type:2,
            scrollbar: false,
            area:['50%','60%'],
            offset: 'auto',
            shadeClose:true,
            content:"/admin/indexconf/indexgoodsConfig/form?configId="+configId
        })
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
        // alert(data.configId)
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    url:"/admin/indexconf/indexgoodsConfig/del",
                    type:"GET",
                    data:{configId:data.configId},
                    dataType:"text",
                    success:function (json) {
                        // alert(json)
                        if(json=="success"){
                            obj.del();
                            layer.close(index);
                        }else {
                            alert("删除失败")
                        }
                    },
                    error:function (json) {
                        alert("删除失败")
                    }
                });

            });
        } else if(obj.event === 'edit'){
            layer.open({
                type:2,
                scrollbar: false,
                area:['50%','60%'],
                offset: 'auto',
                shadeClose:true,
                content:"/admin/indexconf/indexgoodsConfig/form?configId="+data.configId
            })
        }
    });
    //轮播图片上传
    var uploadLbt = upload.render({
        elem: '#lbtBtn'
        ,url: '/admin/uploadImg' //改成您自己的上传接口
        ,accept:'images'
        ,acceptMime:'image/*'
        ,field:'projectImg'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#lbt').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            // alert("hello")
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            // alert(res.data)
            //上传成功
            $("#carouselUrl").val("")
            $("#carouselUrl").val(res.data);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#lbtText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadLbt.upload();
            });
        }
    });
});