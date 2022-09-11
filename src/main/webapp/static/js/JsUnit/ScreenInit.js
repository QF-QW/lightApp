(function () {
    let docEl = document.documentElement;
    //body设置
    var dpr = window.devicePixelRatio || 1            //获取物理像素比

    function setBodyFontSize() {
        if (document.body) {
            document.body.style.fontSize = (12 * dpr) + "px"
        } else {
            document.addEventListener("DOMContentLoaded", setBodyFontSize)
        }
    }

    setBodyFontSize()

    //Rem设置
    function setRemUnit() {
        docEl.style.fontSize = docEl.offsetWidth / 10 + "px";
    }

    setRemUnit()
    window.addEventListener("resize", setRemUnit)
    window.addEventListener('pageshow', function (e) {
        if (e.persisted) {
            setRemUnit()
        }
    })
})()