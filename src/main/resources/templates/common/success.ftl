<html>
<#include "../common/header.ftl">
<body>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="alert alert-dismissable alert-success">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h4>
                        成功!
                    </h4> <strong>${msg!""}</strong><a href="${url}" class="alert-link"><span id="seconds">4</span>s后自动跳转</a>
                </div>
            </div>
        </div>
    </div>
</body>

<script>
    var sec = 3;
    var intervalId = setInterval(func, 1000);

    function func() {
        if(sec == 0) {
            clearInterval(intervalId);
            location.href = "${url}";
        }
        document.getElementById("seconds").innerHTML = sec;
        sec--;
    }
</script>

</html>