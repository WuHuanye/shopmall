layui.use(['table','layer','form'], function(){
    var table = layui.table,
        form = layui.form,
        layer = layui.layer;
        var $ = layui.jquery; //重点处
    table.render({
        elem: '#test'
        ,url:'/admin/goods/data'
        ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            ,layEvent: 'LAYTABLE_TIPS'
            ,icon: 'layui-icon-tips'
        }]
        // ,id:"tables"
        ,title: '商品数据表'
        ,cellMinWidth : 95
        ,height : "full-104"
        ,limit : 20
        ,limits : [10,15,20,25],
        // ,autoSort: false,    //禁用前端自动排序
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {type : "numbers", title : '序号',align : 'center',sort : true, fixed: 'left',width : 50},
            /*{field:'goodsId', title:'ID', width:80, fixed: 'left', unresize: true, sort: true,hide:true}
            ,*/{field:'goodsName', title:'产品名称', width:120, edit: 'text'}
            ,{field:'goodsDictId', title:'分类', width:70,hide:true}
            ,{field:'goodsCoverImg', title:'主图', width:150,
                templet:function (obj) {
                    return "<div><img style='width:90px;height:60px' src='" + obj.goodsCoverImg + "' alt='主图'/></div>";
                }
            }
            /*,{field:'goodsCarousel', title:'轮播图', width:150,
                templet:function (obj) {
                    return "<img src='" + obj.goodsCarousel + "' alt='无法显示' style='width:120px;height:100px;'/>";
                }
            }*/
            ,{field:'goodsPurPrice', title:'进价', width:80, sort: true}
            ,{field:'originalPrice', title:'原价', width:80, sort: true}
            ,{field:'sellingPrice', title:'售价', width:120, sort: true}
            ,{field:'stockNum', title:'库存', width:100, sort: true}
            ,{field:'tag', title:'标签', width:120}
            ,{field:'goodsSellStatus', title:'状态', width:120,
                // templet:'#switchTpl'
                templet:function (obj) {
                    var strCheck = obj.goodsSellStatus == "1" ?"checked":"";
                    return "<input  type='checkbox'  mid="+obj.goodsId+" "+strCheck+" name='goodsSellStatus' lay-skin='switch' lay-text='上架|下架' lay-filter='switchTest'>";
                }
            }
            ,{field:'createUserName', title:'创建人', width:120}
            ,{field:'createTime', title:'创建时间', width:120}
            ,{field:'updateUserName', title:'修改人', width:120}
            ,{field:'updateTime', title:'修改时间', width:120}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
        ]]
        ,page: true
    });


    $(document).on("click",".search_btn",function () {
        // alert($("#levelOne").val()!='')
        if($("#levelOne").val()!=''){
            if($("#levelTwo").val()==null||''==$("#levelTwo").val()){
                alert("请选择子类")
                return
            }
        }
        var goodsDictId = $("select[name='goodsDictId'] option:selected").val();
        if(goodsDictId == ''||goodsDictId == null){
            goodsDictId = 0;
        }
        table.reload("test",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                goodsDictId:goodsDictId,
                goodsName: $("#goodsName").val(),  //查询内容，代码生成后手动修改

            }
        })

    })
    //监听提交
    form.on('submit(save)', function(data){
        var goodsName = $("#goodsNameM").val();
        var sellingPriceM = $("#sellingPriceM").val();
        var stockNumM = $("#stockNumM").val();
        var goodsPurPriceM = $("#goodsPurPriceM").val();
        var originalPriceM = $("#originalPriceM").val();

        var pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{1,9}$/;   //商品名称不得超过9个字
        var reg = new RegExp("^[0-9]*$");  //数字校验
        if (!pattern.test(goodsName.trim())) {
            alert("商品名称不得超过9个字")
            return
        }
        if (!reg.test(sellingPriceM)||!reg.test(stockNumM)||!reg.test(originalPriceM)||!reg.test(goodsPurPriceM)){
            alert("库存|进价|原价|现价 需要为数字")
            return
        }
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //将表单数据序列化
        var goods = $("#form").serialize();
        // alert(goods.goodsId)
        $.ajax({
            url:"/admin/goods/save",
            async: false,
            type:"POST",
            data:goods,
            dataType: "text",
            success:function (json) {
                if(json == "success"){
                    alert("添加成功")
                    parent.layer.close(index); //再执行关闭                        //刷新父页面
                    parent.location.reload();
                    return
                }else{
                    alert("添加失败。。。")
                    return
                }
            },
            error: function (json) {
                alert("请检查网络...")
                return
            },
        });
        return false;
    });
    //监听指定开关
    form.on('switch(switchTest)', function(data){
        // layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
        /*layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
            offset: '6px'
        });*/
        var state = data.elem.checked;
        var goodsId= data.elem.attributes['mid'].nodeValue;
        // console.log(data)
        // alert(goodsId)
        $.ajax({
            url:"/admin/goods/updateStatus",
            type:"POST",
            data:{goodsId:goodsId},
            dataType:"text",
            success:function (json) {
                if (json!="success"){
                    layer.msg("改变失败！", {icon: 1, time: 1000});
                    return
                }
            }
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
        if(obj.event === 'del'){
            layer.confirm('真的要删除吗?', function(index){
                $.ajax({
                    url:"/admin/goods/delete",
                    data:{goodsId:data.goodsId},
                    type:'post',
                    dataType: "text", //return dataType: text or json
                    success:function (res) {
                        if (res=="success"){
                            obj.del();
                            table.render();
                        } else {
                            alert("删除失败")
                        }
                    }

                });
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
           editLink(data);
        }
    });

    function editLink(data) {
        // alert(data.goodsId)
        var index = layer.open({
            title : "修改商品",
            type : 2,
            area: ['100%', '100%'],
            // content : path + "/admin/goods/form?id="+data.id
            content : "/admin/goods/edit?goodsId="+data.goodsId
        })
        layui.layer.full(index);
    };


    $('#levelOne').on('change', function () {
        // alert("哈哈")
        var dictId = ($("#levelOne").val());
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
                levelTwoSelect += '<option value="">请选择子类</option>';

                for (var i = 0; i < length2; i++) {
                    levelTwoSelect += '<option value=\"' + result[i].id + '\">' + result[i].value + '</option>';
                }
                $('#levelTwo').html(levelTwoSelect);

            },
            error: function () {
                alert("操作失败")
            }
        });
    });


});