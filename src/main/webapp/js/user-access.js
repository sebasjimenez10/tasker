/* 
 * JavaScript for webservices
 */

//REST urls
var host = "http://localhost:8080/";
var loginUrl = "TaskerProject/resources/UserServices/Login?";
var signinUrl = "TaskerProject/resources/UserServices/Signin?";

//Alert container id
var loginAlert = "loginAlert";
var signinAlert = "signinAlert";

//Alert styles
var alertError = "error";
var alertSuccess = "success";


/**
 * This function call the login ws
 */
function loginUser() {
    var user = document.getElementById("inputUser");
    var password = document.getElementById("inputPassword");
    
    if(user.value == 0 || password.value == 0){
        myAlert(loginAlert, "You must fill everything!", alertError);
        return;
    }    
    var url = host + loginUrl + "userName=" + user.value + "&password=" + password.value;
    
    sendRequest(url, loginMethod);
}

/**
 * This function signs in a new user
 */
function signinUser() {
    var name = document.getElementById("inputName");
    var lastName = document.getElementById("inputLastName");
    var userName = document.getElementById("inputUser");
    var password1 = document.getElementById("inputPassword1");
    var password2 = document.getElementById("inputPassword2");
    var email = document.getElementById("inputEmail");
    
    if( name.value == 0 || lastName.value == 0 || userName.value == 0 
        || userName.value == 0 ||  password1.value == 0 || password2.value == 0
        || email.value == 0 ){
        //each field is required
        myAlert(signinAlert, "You must fill everything", alertError);
        return;
    }
    
    if( password1.value != password2.value ){
        //passwords don't match
        myAlert(signinAlert, "Passwords does not match, type 'em again!", alertError);
        document.getElementById("inputPassword1").value = "";
        document.getElementById("inputPassword2").value = "";
        return;
    }
    
    var url = host + signinUrl + "name=" + name.value
    + "&lastName=" + lastName.value + "&userName=" + userName.value
    + "&password=" + password2.value + "&email=" + email.value;

    sendRequest(url, signinMethod);
}

/**
 * Comment
 */
function loginResponse(response) {
    if( response == "USER_NOT_FOUND" || response == "INVALID_PASSWORD" ){
        myAlert(loginAlert, "User might not exist or password was wrong", alertError);
    }else{
        var jsonObj = eval('(' + response + ')');
        //Set user session data
        setUserData(jsonObj.name, jsonObj.last_name, document.getElementById("inputUser").value, jsonObj.id_user);                    
        window.location.replace("./userHome.html");
    }
    return;
}

/**
 * Comment
 */
function signinResponse(response) {
    if( response == "true" ){
        myAlert( signinAlert, "You made it! Now go and Login!", alertSuccess );
        document.getElementById("inputName").value = "";
        document.getElementById("inputLastName").value = "";
        document.getElementById("inputUser").value = "";
        document.getElementById("inputPassword1").value = "";
        document.getElementById("inputPassword2").value = "";
        document.getElementById("inputEmail").value = "";
    }else if (response == "false"){
        myAlert(signinAlert, "Username already exists pick another one!", alertError);
        document.getElementById("inputUser").value = "";
    }
    return;
}

/**
 * This function show an alert
 */
function myAlert(elementId, message, type) {
    var header;
    if( type == "error" ){
        header = "Got an error!"
    }else if( type == "success" ){
        header = "Got it!"
    }
    var alertHtml = 
    "<div class=\"alert alert-" + type + " fade in\">" +
    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" +
    "<strong>" + header + "</strong> " + message + "</div>";    

    document.getElementById(elementId).innerHTML = alertHtml;
}