
function getCookie(c_name){    
    var cookieValue = null;
    var i = 0;
    var cookieArray = document.cookie.split("; ");
    
    while( i < cookieArray.length){
        var cookie = cookieArray[i].split("=");
        if( cookie[0] == c_name ){
            cookieValue = unescape(cookie[1]);
        }
        i += 1;
    }
    return cookieValue;
}    

function setCookie(c_name, value, exdays){
    var exdate = new Date();
    exdate.setDate( exdate.getDate() + exdays );
    var c_value = escape( value ) + ( (exdays == null) ? "" : "; expires=" + exdate.toUTCString() );
    document.cookie=c_name + "=" + c_value;
}

function del_cookie(name){
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}