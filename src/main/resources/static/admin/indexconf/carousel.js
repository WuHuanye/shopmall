layui.use(['table','layer','upload','form'], function(){
    var table = layui.table;
    var $ = layui.jquery,
        form = layui.form,
        upload = layui.upload,
        layer = layui.layer; //独立版的layer无需执行这一句

    table.render({
        elem: '#test'
        ,url:'/admin/indexconf/carousel/data'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }]
        ,title: '数据表'
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field:'carouselId', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
            ,{field:'carouselUrl', title:'轮播图', width:160, edit: 'text',
                templet:function (obj) {
                    return "<div><img style='width:90px;height:60px' src='" + obj.carouselUrl + "' alt='轮播图'/></div>";
                }
            }
            ,{field:'redirectUrl', title:'跳转地址', width:150, edit: 'text',hide:true}
            ,{field:'carouselRank', title:'排序', width:80, edit: 'text', sort: true}
            ,{field:'createTime', title:'创建时间', width:160}
            ,{field:'createUser', title:'创建人',width:150}
            ,{field:'updateTime', title:'更新时间', width:160, sort: true}
            ,{field:'updateUser', title:'更新人', width:150}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}

        ]]
        ,page: false
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
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    url:"/admin/indexconf/carousel/del",
                    type:"GET",
                    data:{carouselId:data.carouselId},
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
            var carouselId = data.carouselId;
            // alert(carouselId)
            layer.open({
                type:2,
                scrollbar: false,
                area:['50%','60%'],
                offset: 'auto',
                shadeClose:true,
                content:"/admin/indexconf/carousel/form?carouselId="+carouselId
            })
        }
    });
    //轮播图片上传
    var uploadLbt = upload.render({
        elem: '#lbtBtn'
        ,url: '/admin/uploadImg1' //改成您自己的上传接口
        ,accept:'images'
        ,acceptMime:'image/*'
        // ,field:'projectImg'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#lbt').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            // alert(res.data)
            //上传成功
            $("#carouselUrl").val("")
            $("#carouselUrl").val(res.data);
            parent.location.reload();
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
    //监听提交
    form.on('submit(save)', function(data){
        var carousel = $("#form").serialize();
        $.ajax({
            url:"/admin/indexconf/carousel/save",
            async: false,
            type:"POST",
            data:carousel,
            dataType: "text",
            success:function (json) {
                if(json == "success"){
                    alert("添加成功")
                    //刷新页面
                    parent.location.reload();
                    return
                }else{
                    alert("添加失败。。。")
                }

            },
            error: function (json) {
                alert("请检查网络...")
                return
            },

        });
        return false;
    });
    $(".add").click(function () {
        // alert("添加轮播图")
        var carouselId = 0;
        layer.open({
            type:2,
            scrollbar: false,
            area:['50%','60%'],
            offset: 'auto',
            shadeClose:true,
            content:"/admin/indexconf/carousel/form?carouselId="+carouselId
        })
    });

});