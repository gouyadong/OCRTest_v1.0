<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>百度AI-火车票识别</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/layui-v2.2.5/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>

</head>
<body>
<div class="content">
    <h3 class="title">百度AI-火车票识别</h3>
    <div class="img-box">
        <div class="layui-upload">
            <div class="layui-upload-list">
                <img class="layui-upload-img" id="demo1">
                <p id="demoText"></p>
            </div>
        </div>
    </div>

    <button type="button" class="layui-btn" id="test1">上传火车票图片</button>
    <div class="layui-form-item">
        <label class="layui-form-label">车票号：</label>
        <div class="layui-input-inline">
            <input type="text" id="ticket_num" required lay-verify="required" placeholder="车票号" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">始发站：</label>
        <div class="layui-input-inline">
            <input type="text" id="starting_station" required lay-verify="required" placeholder="始发站" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">车次号：</label>
        <div class="layui-input-inline">
            <input type="text" id="train_num" required lay-verify="required" placeholder="车次号" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">到达站：</label>
        <div class="layui-input-inline">
            <input type="text" id="destination_station" required lay-verify="required" placeholder="到达站"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">出发日期：</label>
        <div class="layui-input-inline">
            <input type="text" id="date" required lay-verify="required" placeholder="出发日期" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">车票金额：</label>
        <div class="layui-input-inline">
            <input type="text" id="ticket_rates" required lay-verify="required" placeholder="车票金额" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">席别：</label>
        <div class="layui-input-inline">
            <input type="text" id="seat_category" required lay-verify="required" placeholder="席别" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">乘客姓名：</label>
        <div class="layui-input-inline">
            <input type="text" id="name" required lay-verify="required" placeholder="乘客姓名" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
</div>
<script src="/layui/layui-v2.2.5/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            , url: '/trainTicket/train_ticket'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg('上传失败');
                }
                //上传成功
                $('#ticket_num').val(res.ticket_num);
                $('#starting_station').val(res.starting_station);
                $('#train_num').val(res.train_num);
                $('#destination_station').val(res.destination_station);
                $('#date').val(res.date);
                $('#ticket_rates').val(res.ticket_rates);
                $('#seat_category').val(res.seat_category);
                $('#name').val(res.name);

            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
    });
</script>
</body>
</html>
