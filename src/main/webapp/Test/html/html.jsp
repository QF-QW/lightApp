<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: QW
  Date: 2022/2/25
  Time: 1:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/Test/js/jquery-3.6.0.js"></script>
</head>
<!--本地：113.400721   23.054315-->
<!--key:b3b757ea4a67ec259bf9ea961e469c60    密钥：212ae3522640392272bd27f9416a2c95 -->
<body>
<div id="container" style="width: 800px;height: 800px"></div>

<img src="<c:url value="/Test/img/84868473_p0.jpg"/>" alt="">

</body>
<!--密钥-->
<script type="text/javascript">
    window._AMapSecurityConfig = {
        securityJsCode:'212ae3522640392272bd27f9416a2c95',
    }
</script>
<script src="https://webapi.amap.com/loader.js"></script>
<script src = 'https://webapi.amap.com/maps?v=2.0&key=b3b757ea4a67ec259bf9ea961e469c60'></script>
<script>
    var map = new AMap.Map('container', {
        zoom:15,
        center:[113.400721,23.054315]
    })

    var marker = new AMap.Marker({
        // position:new AMap.LngLat(113.400721,23.054315),
        position:[113.400721,23.054315],
        title:"靓仔",
        // icon:'../img/84868473_p0.jpg'
        draggable: true,
        // offset:new AMap.Pixel(-13,-30)
    })

    map.add(marker)
</script>

</html>
