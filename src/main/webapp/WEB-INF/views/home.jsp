<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>short url</title>
</head>
<body>
<form>
    <label>URL</label>
    <input type="text" name="url" size="100px">
    <input type="button" id="submit" value="convert">
</form>
<br>
<div id="result"></div>
</body>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
    $(document).ready(function(){
        $('#submit').click(function(){
            if(!$('input[name=url]').val())
                return false;
            let sendData = { "url" : $('input[name=url]').val()};
            $.ajax({
                type:'post',
                headers: {'Content-Type': 'application/json'},
                url:'/url/convert',
                data : JSON.stringify(sendData),
                dataType:'json',
                success : function(data){
                    $('#result').html(
                        "<p>converted URL : " + data.url + "</p>" +
                        "<p>request count : " + data.count + "</p>"
                    );
                },
                error: function(xhr, textStatus){
                    $('#result').text(xhr.status + ' ' + textStatus);
                }
            });
        });
    });
</script>
</html>