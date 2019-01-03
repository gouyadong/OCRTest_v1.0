<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>百度AI-文字识别</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/layui-v2.2.5/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>

</head>
<body>
<div class="content">
    <h3 class="title">百度AI-文字识别</h3>
    <div class="img-box">
        <div class="layui-upload">
            <div class="layui-upload-list">
                <img class="layui-upload-img" id="demo1">
                <p id="demoText"></p>
            </div>
        </div>
    </div>

    <button type="button" class="layui-btn" id="test1">上传文字图片</button>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">文字识别结果：</label>
        <div class="layui-input-inline" style="width:430px;">
            <textarea id="words" style="height: 350px;" placeholder="文字识别结果" class="layui-textarea"></textarea>
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
            , url: '/wordsDistinguish/accurate_basic'
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
                $('#words').val(res.words);
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
