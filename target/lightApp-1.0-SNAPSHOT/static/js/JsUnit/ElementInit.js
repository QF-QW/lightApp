/*初始化加载用户的信息*/
window.copywriting = [
    ["The first time when I saw her being meek that she might attain height.", "第一次，当它本可进取时，却故作谦卑"],
    ["The second time when I saw her limping before the crippled.", "第二次，当它在空虚时，用爱欲来填充"],
    ["The third time when she was given to choose between the hard and the easy, and she chose the easy.", "第三次，在困难和容易之间，它选择了容易"],
    ["The fourth time when she committed a wrong, and comforted herself that others also commit wrong.", "第四次，它犯了错，却借由别人也会犯错来宽慰自己"],
    ["The fifth time when she forbore for weakness, and attributed her patience to strength.", "第五次，它自由软弱，却把它认为是生命的坚韧"],
    ["The sixth time when she despised the ugliness of a face, and knew not that it was one of her own masks.", "第六次，当它鄙夷一张丑恶的嘴脸时，却不知那正是自己面具中的一副"],
    ["And the seventh time when she sang a song of praise, and deemed it a virtue.", "第七次，它侧身于生活的污泥中，虽不甘心，却又畏首畏尾"],
]
window.color = [["#84cff6", "#ffffff"], ["#fda17c", "#ffffff"], ["#ac91fc", "#ffffff"], ["#f6dd5c", "#ffffff"], ["#e6eef1", "#c1c5d1"], ["#76eae7", "#ffffff"], ["#fb9392", "#ffffff"], ["#fba27a", "#ffffff"]];


$(function () {

    /*初始化 pjax*/
    $(document).pjax('a', '#pjax-container', {
        replace: true,
        timeout: 6000
    })

    /*过场动画*/
    function change() {
        let flag = false;

        function active(e) {
            let pageMessageIndex = parseInt(Math.random() * 7)
            $(".transitions-container").find(".text .message").eq(0).html(window.copywriting[pageMessageIndex][0])
            $(".transitions-container").find(".text .message").eq(1).html(window.copywriting[pageMessageIndex][1])


            if (flag) {
                e.preventDefault();
            } else {
                /*如果是未启动情况进行替换*/
                $(this).addClass("current")
                $(this).siblings(".current").removeClass("current")

                flag = true;

                $("#init-dom .transitions-container").css("display", "flex");
                clearTimeout(window.timeout)
                $("a").attr("disabled", true)

                window.timeout = setTimeout(function () {
                    $("#init-dom .transitions-container").css("display", "none");
                    $("a").attr("disabled", false);

                    flag = false;
                }, 5000)
            }
        }

        $("a").on("click", active)
    }

    change()

    /*易班接口初始化*/
    function scan(type) {
        try {
            if (type) {
                yiban.scan({
                    success: function (result) {
                        /*成功获取文本*/
                    },
                    fail: function (error) {
                        /*获取文本失败*/
                    }
                });
            } else {
                /*直接跳转*/
                yiban.scan();
            }
        } catch (err) {
            console.log("不支持")
        }
    }
    $(".scanning").click(function (){
        scan(0)
    })


        function getLocation() {
            try {
                yiban.getLocation({success:function(result) {

                    return [result.longitude,result.latitude]

                    }, fail:function(error) {
                        var resultHTML = document.getElementById("result");
                        resultHTML.textContent = JSON.stringify(error);
                    }});
            }catch(err) {
                var resultHTML = document.getElementById("result");
                resultHTML.textContent = '{"code":"12000"}';
            }
        }



    /*webSocket*/
    // if ("WebSocket" in window) {
    //
    //     // 打开一个 web socket
    //     var ws = new WebSocket("ws://localhost:10087");
    //
    //     ws.onopen = function () {
    //         // Web Socket 已连接上，使用 send() 方法发送数据
    //         ws.send("发送数据");
    //     };
    //
    //     ws.onmessage = function (evt) {
    //         var received_msg = evt.data;
    //     };
    //
    //     ws.onclose = function () {
    //         // 关闭 websocket
    //     };
    // } else {
    //     // 浏览器不支持 WebSocket
    // }

})
