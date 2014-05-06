/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var host = "http://localhost:8080/";
var getTasksUrl = "TaskerProject/resources/TaskServices/GetTasks?";
var createTaskUrl = "TaskerProject/resources/TaskServices/CreateTask?";
var deleteTaskUrl = "TaskerProject/resources/TaskServices/DeleteTask?";
var updateTaskUrl = "TaskerProject/resources/TaskServices/UpdateTask?";
var addActivityUrl = "TaskerProject/resources/ActivityServices/AddActivity?";
var getTaskActivitiesUrl = "TaskerProject/resources/ActivityServices/GetTaskActivities?";
var deleteActivityUrl = "TaskerProject/resources/ActivityServices/DeleteActivity?";

/**
 * This function loads a user after successful login process
 */
function loadUser() {
    var name = getUserDataItem("name");
    var lastName = getUserDataItem("last_name");
    var username = getUserDataItem("username");
    
    if( name == null || lastName == null || username == null ){
        window.location.replace('loginError.html');
        return;
    }
    
    document.getElementById("nameInfo").innerHTML += name + " " +  lastName;
    document.getElementById("userNameInfo").innerHTML += username;
}

/**
 * Logout user
 */
function logoutUser() {
    deleteUserData();
    window.location.replace('./');
}

/**
 * This function loads the created tasks from the logged user
 */
function loadTasks() {
    var url = host + getTasksUrl + "id_user=" + getUserDataItem("id_user");    
    sendRequest(url, getTasksMethod);
}

/**
 * Manage response getTasks
 */
function getTasksResponse(response) {
    if( response == "ZERO_TASKS" ){
    // no tiene tareas
    }else{
        // tiene tareas
        var jsonObj = eval('(' + response + ')');
        var tasksCombo = document.getElementById("tasksCombo");
        var lista = jsonObj.tasks;
        
        tasksCombo.options.remove( tasksCombo.options.selectedIndex );
        
        var index = 0;
        while ( index < lista.length ){
            tasksCombo.options[ tasksCombo.options.length ] = new Option(lista[index], lista[index]);
            index += 1;
        }
        tasksCombo.selectedIndex = index - 1;
    }
    return;
}

/**
 * Create Task
 */
function createTask() {
    var taskDesc = document.getElementById("taskDesc").value;
    if( taskDesc != "" ){
        var url = host + createTaskUrl + "taskDesc=" + taskDesc + "&id_user=" + getUserDataItem("id_user");
        sendRequest(url, createTaskMethod);
        updateTask();
    }else{
        myAlert("modalAlert", "You mush type something!", 1);
    }
}

/**
 * Comment
 */
function createTaskResponse(response) {
    if( response == "false" ){
        //Tarea no creada
        myAlert("modalAlert", "A task with that description already exists", 1);
    }else if( response == "true" ){
        //Tarea creada
        var newTaskDesc = document.getElementById('taskDesc').value;
        var combo = document.getElementById("tasksCombo");
        combo.options[ combo.options.length ] = new Option(newTaskDesc, newTaskDesc);
        tasksCombo.selectedIndex = combo.options.length - 1;
        //Hides the modal
        document.getElementById("myModal").setAttribute("class", "modal hide fade");
        $('#myModal').modal('hide');
    }
}

/**
 * Comment
 */
function deleteTask() {
    var taskDesc = document.getElementById("tasksCombo").value;
    if( taskDesc == '' ){
        return;
    }
    var url = host + deleteTaskUrl + "taskDesc=" + taskDesc + "&id_user=" + getUserDataItem("id_user");
    sendRequest(url, deleteTaskMethod);
}

/**
 * Comment
 */
function deleteTaskResponse(response) {
    if( response == "false" ){
        //Tarea no eliminada
        myAlert("editorAlert", "The task still has some activities, finish them all or delete them.", 1);
    }else if( response == "true" ){
        //Tarea eliminada
        var combo = document.getElementById("tasksCombo");
        combo.options.remove(combo.options.selectedIndex);
        combo.selectedIndex = combo.options.length - 1;
        
        //checking whats left on the combo box
        if( combo.options.length > 0 ){
            updateTask();
        }else{
            hideEditor();
        }
    }
}

/**
 * Comment
 */
function updateTaskDesc() {    
    var newDesc = document.getElementById("taskName").value;
    if( newDesc == "" ){
        myAlert("editorAlert", "You must type something!", 1);
        return;
    }
    var url = host + updateTaskUrl +
    "taskDesc=" + document.getElementById("tasksCombo").value +
    "&newDesc=" + newDesc + 
    "&id_user=" + getUserDataItem("id_user");
    sendRequest(url, updateTaskMethod);
}

/**
 * Comment
 */
function updateTaskResponse(response) {
    if( response == "true" ){
        //actualizada cambiarla en el combo
        var combo = document.getElementById("tasksCombo");
        var newDesc = document.getElementById("taskName");
        var selected = combo.selectedIndex;
        combo.options[ combo.selectedIndex ] = new Option(newDesc.value, newDesc.value);
        combo.selectedIndex = selected;
    }else{
        //hubo algun error en el update
        myAlert("editorAlert", "A task with that description already exists", 1);
    }
}

/**
 * Comment
 */
function updateTask() {
    var selectedTask = document.getElementById("tasksCombo").value;
    document.getElementById("taskName").value = selectedTask;
    document.getElementById("activitiesList").innerHTML = "";
    //tambien tiene que update las actividades de esa task
    var url = host + getTaskActivitiesUrl + "taskDesc=" + selectedTask;
    sendRequest(url, getTaskActivitiesMethod);
}

function getTaskActivitiesResponse( response ){
    if( response == "SQL_EX" || response == "JSON_EX" || response == "NO_ACTIVITIES_FOUND" ){
        return;
    }
    var objResponse = eval( '(' + response + ')' );
    var index = 0;
    
    while( index < objResponse.act_ids.length ){
        var desc = objResponse.act_descs[index];
        var status = objResponse.status_ids[index];
        if( status == "1" ){
            status = 'INCOMPLETE';
        }else{
            status = 'COMPLETE';
        }
        addItemToTable(desc, status);
        index++;
    }
}

/**
 * Comment
 */

function addActivity() {
    var actDesc = document.getElementById("actDesc").value;
    if( actDesc == "" ){
        myAlert("editorAlert", "You mush type something!", 1);
        return;
    }
    var url = host + addActivityUrl + "taskDesc=" + document.getElementById("tasksCombo").value +
    "&actDesc=" + actDesc + "&id_user=" + getUserDataItem("id_user");
    sendRequest(url, addActivityMethod);
}

/**
 * Comment
 */
function addActivityResponse(response) {
    if( response == "true" ){
        addItemToTable( document.getElementById("actDesc").value, "INCOMPLETE" );
        document.getElementById("actDesc").value = "";
    }else{
        myAlert("editorAlert", "An activity with that description already exists", 1);
    }
}

/**
 * Comment
 */
var actCount = 0;
function addItemToTable( desc, status ) {
    var table = document.getElementById("activitiesList");    
    var newRow = document.createElement('tr');
    newRow.setAttribute("id", "activity" + actCount );    
    var tdDesc = document.createElement('td');
    var tdStatus = document.createElement('td');
    var tdDelButton = document.createElement('td');
    
    var delButton = document.createElement('button');
    delButton.setAttribute('onclick', 'deleteActivity(\'activity' + actCount + '\')' );
    delButton.setAttribute('class', 'btn btn-danger');
    delButton.innerHTML = 'Delete';
        
    tdDesc.innerHTML = desc;
    tdStatus.innerHTML = status;
        
    var divCenter = document.createElement('div');
    divCenter.setAttribute('align', 'center');
    divCenter.appendChild(delButton);    
    tdDelButton.appendChild( divCenter );
    
    newRow.appendChild(tdDesc);
    newRow.appendChild(tdStatus);    
    newRow.appendChild(tdDelButton);
    
    table.appendChild(newRow);
    actCount++;
}

/**
 * Comment
 */
var trId = null;
function deleteActivity(trIdTarget) {
    trId = trIdTarget;
    var taskDesc = document.getElementById( "tasksCombo" ).value;
    var actDesc = document.getElementById( trIdTarget ).firstChild.innerHTML;
    var url = host + deleteActivityUrl + "taskDesc=" + taskDesc + "&actDesc=" + actDesc;
    sendRequest(url, deleteActivityMethod);
    trId = null;
    return;
}

/**
 * Comment
 */
function deleteActivityResponse(response) {
    if( response == "true" ){
        var table = document.getElementById("activitiesList");
        var tableRow = document.getElementById(trId);
        table.removeChild(tableRow);
    }
}

/**
 * This function shows things
 */
function showEditor() {    
    if(document.getElementById("tasksCombo").options.length > 0){
        updateTask();
        document.getElementById("taskEditor").setAttribute("class", "");
    }
}

/**
* This function hides things
*/
function hideEditor() {
    document.getElementById("taskEditor").setAttribute("class", "hide");
}

function myAlert(elementId, message, type) {
    var header;
    var alertType;
    if( type == 1 ){
        header = "Got an error!"
        alertType = "error";
    }else if( type == 2 ){
        header = "Got it!"
        alertType = "success";
    }
    var alertHtml = 
    "<div id=\"myAlert\" class=\"alert alert-" + alertType + " fade in\">" +
    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" +
    "<strong>" + header + "</strong> " + message + "</div>";
    document.getElementById(elementId).innerHTML = alertHtml;
}