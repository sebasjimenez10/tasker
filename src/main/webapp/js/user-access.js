
//Alert styles
var alertError = "error";
var alertSuccess = "success";
/**
 * 
 * @param {type} elementId
 * @param {type} message
 * @param {type} type
 * @returns {undefined}
 */
function myAlert(elementId, message, type) {
    var header;
    if( type === "error" ){
        header = "Got an error!";
    }else if( type === "success" ){
        header = "Got it!";
    }
    var alertHtml = 
    "<div class=\"alert alert-" + type + " fade in\">" +
    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" +
    "<strong>" + header + "</strong> " + message + "</div>";    

    document.getElementById(elementId).innerHTML = alertHtml;
}