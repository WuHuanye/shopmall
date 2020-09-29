//Demo
layui.use(['form','layer','upload'], function(){
    var form = layui.form;
    var upload = layui.upload;
    var layer = layui.layer;
    var $ = layui.jquery; //重点处
    $('.site-demo-layedit').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    //普通图片上传
    var uploadInst = upload.render({
        elem: '#test1'
        ,url: '/admin/uploadImg2' //改成您自己的上传接口
        ,accept:'images'
        ,acceptMime:'image/*'
        // ,field:'projectImg'
        ,size: 1024*6   //不得超过6M
        ,before: function(obj){
            $("#zImg").val('');//重新上传时清空隐藏的主图input里面的地址
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                // alert(result)
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            //上传成功
            // $('#demo1').attr('src', res.data); //图片链接（base64）
            $("#zImg").val("")
            $("#zImg").val(res.data);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
    //轮播图片上传
    var uploadLbt = upload.render({
        elem: '#lbtBtn'
        ,url: '/admin/uploadImg2' //改成您自己的上传接口
        ,accept:'images'
        ,acceptMime:'image/*'
        // ,field:'projectImg'
        ,size: 1024*6   //不得超过6M
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
            $("#lbtInput").val("")
            $("#lbtInput").val(res.data);
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
    //多图片上传
    var str = "";
    upload.render({
        elem: '#test2',
        url: '/admin/uploadImg2', //改成您自己的上传接口
        multiple: true,   //是否允许多文件上传，默认未false
        acceptMine:'/image/*', //只选择图片
        auto: false,  //是否自动上传 ，默认为true
        number : 5,  //最大上传数量
        size: 1024*6, //最大文件大小，单位k
        bindAction: '#uploadActionForAdd', //绑定的按钮
        field: 'file', //传到后台的字段名,默认file
        choose: function (obj) {
            files = obj.pushFile();
            obj.preview(function (index, file, result) {
                console.log(index); //得到文件索引
                console.log(file); //得到文件对象
                // console.log(result); //得到文件base64编码，比如图片
                $('#demo2').append('<img src="' + result + '" id="'+index+'" alt="' + file.name + '" style="height: 100px;width: 100px;margin-right: 10px" class="layui-upload-img ">')
                $('#'+index).bind('dblclick', function () {//双击删除指定预上传图片
                    delete files[index];//删除指定图片
                    $(this).remove();
                });
            })
        },
        before: function(obj){
            if($("[name='brand']").val() == ''){
                return false;
            }else{
                console.log(files);
                var names='';
                layui.each(files, function (index,file) {
                    console.log(file.name)
                    names += file.name+',';

                });
                $("[name='image']").val(names)
                console.log(names);
            }
            return true;
        },
        done: function (res) {
            str += res.data+",";
            $("#duotu").val(str)
            layer.msg("图片上传成功！", {icon: 1, time: 1000});
            //上传完毕回调
        },
        error: function (e) {
            //请求异常回调
            console.log(e)
        }
    });


    //下拉菜单
    form.on('select(test1)',function () {
        var dictId = ($("#levelOne").val());
        // alert(dictId)
        if(dictId == ""||dictId == null){
            dictId = 0;
            $('#levelTwo').html("");
            $('#levelTwo').html('                <option value="">请选择子类</option>\n')
            return
        }
        $.ajax({
            url: '/admin/goods/listForSelect?dictId=' + dictId,
            type: 'GET',
            success: function (result) {

                var levelTwoSelect = '';
                var length2 = result.length;
                // levelTwoSelect += '<option value="">请选择子类</option>';

                for (var i = 0; i < length2; i++) {
                    levelTwoSelect += '<option value=\"' + result[i].id + '\">' + result[i].value + '</option>';
                }
                // alert(levelTwoSelect)
                $('#levelTwo').html(levelTwoSelect);
                form.render(); //更新 lay-filter="test2" 所在容器内的全部 select 状态,重新渲染表单

            },
            error: function () {
                alert("操作失败")
            }
        });
    })

});

