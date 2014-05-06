/**
 *  Method ids
 */

var loginMethod = "login";
var signinMethod = "signin";
var getTasksMethod = "getTasks";
var createTaskMethod = "createTask";
var deleteTaskMethod = "deleteTask";
var updateTaskMethod = "updateTask";
var addActivityMethod = "addActivity";
var getTaskActivitiesMethod = "getTaskAct";
var deleteActivityMethod = "deleteActivity";

/**
 * This function sends the request to the server
 */
function sendRequest(url, method){
    var xmlhttp = new XMLHttpRequest();
    //false cuz it's not async
    xmlhttp.open('GET', url, false);
    xmlhttp.setRequestHeader("Content-Type", "text/plain");
    xmlhttp.send(null);
    xmlhttp.onreadystatechange = httpHandle(method, xmlhttp);
    return;
}

/**
 * This function handles the http request
 */
function httpHandle(method, xmlhttp) {
    if (xmlhttp.readyState == 4) {
        if ( xmlhttp.status == 200) {
            if( method == loginMethod ){
                loginResponse( xmlhttp.responseText );
            }else if( method == signinMethod ){
                signinResponse( xmlhttp.responseText );
            }else if( method == getTasksMethod ){
                getTasksResponse( xmlhttp.responseText );
            }else if( method == createTaskMethod ){
                createTaskResponse( xmlhttp.responseText );
            }else if( method == deleteTaskMethod ){
                deleteTaskResponse( xmlhttp.responseText );
            }else if( method == updateTaskMethod ){
                updateTaskResponse( xmlhttp.responseText );
            }else if( method == addActivityMethod ){
                addActivityResponse( xmlhttp.responseText );
            }else if( method == getTaskActivitiesMethod ){
                getTaskActivitiesResponse( xmlhttp.responseText );
            }else if( method == deleteActivityMethod ){
                deleteActivityResponse( xmlhttp.responseText );
            }
        }else{
            window.location.replace('serverDown.html');
            document.getElementById("errorCode").innerHTML = xmlhttp.status;
            alert( "Error code: " + xmlhttp.status );
        }
    }
}