/**
 * 
 * @returns {undefined}
 */
function signUp() {
    // Let's get the user data from input fields
    var name = $("#inputName").val();
    var lastName = $("#inputLastName").val();
    var email = $("#inputEmail").val();
    var userName = $("#inputUser").val();
    var password1 = $("#inputPassword1").val();
    var password2 = $("#inputPassword2").val();

    // Gotta mae sure the fields contains something
    if (name.length === 0 || lastName.length === 0 || userName.length === 0
            || userName.length === 0 || password1.length === 0 || password2.length === 0
            || email.length === 0) {
        //each field is required
        myAlert(signinAlert, "You must fill everything", alertError);
        return;
    }

    // Let's verify first that the passwords match
    if (password1 !== password2) {
        //passwords don't match
        myAlert(signinAlert, "Passwords don't match, try again!", alertError);
        document.getElementById("inputPassword1").value = "";
        document.getElementById("inputPassword2").value = "";
        return;
    }

    // Ok we are ready to call the service!

    // Let's create the user object
    var user = {
        name: name,
        lastName: lastName,
        email: email,
        userName: userName,
        password: password1
    };

    // Yey! ready! Let call that rest service
    $.ajax({
        type: "POST",
        url: "/tasker/rest/SignUpService/",
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function(data) {
            console.log("Response text: " + data.responseText);
            console.log("Response status: " + data.status);
            window.location.replace("/tasker/index.html");
        },
        error: function(data) {
            if (data.status === 406) {
                if (data.responseText === "User name already taken") {
                    myAlert(signinAlert, data.responseText, alertError);
                }
            } else {
                if (data.status === 500) {
                    window.location = "/tasker/serverDown.html";
                }
            }
        }
    });
}

/**
 * 
 * @returns {undefined}
 */
function logIn() {
    // Let's get something to work with
    var username = $("#inputUser").val();
    var password = $("#inputPassword").val();

    //Let's check first if the username and password fields have anything
    if( username.length === 0 || password.length === 0 ){
        myAlert(loginAlert, "You have to provide a username and password, Duh!?",
        alertError);
        return;
    }

    //Let's call the login service
    $.ajax({
        type: "GET",
        url: "/tasker/rest/LogInService?username=" + username 
                + "&password=" + password,
        contentType: 'application/json',
        success: function(data) {
            var loginResponse = data;
            sessionStorage.setItem('token', loginResponse.token );
            sessionStorage.setItem('username', loginResponse.username );
            window.location = "/tasker/userHome.html";
        },
        error: function(data) {
            myAlert(loginAlert, data.responseText, alertError);
            $("#inputUser").val('');
            $("#inputPassword").val('');
        }
    });
}

/**
 * 
 * @returns {undefined}
 */
function loadUserSession() {
    // To load user session we have to get all the tasks
    // and place them correctly and offcourse, set the user name!
    
    // Let's set the username
    var username = sessionStorage.getItem('username');
    $("#userNameInfo").html( $("#userNameInfo").html() + username );
    
    // Let's call the service to get user's tasks
    var token = sessionStorage.getItem('token');
    $.ajax({
        type: "GET",
        url: "/tasker/rest/UserTasksService",
        contentType: 'application/json',
        headers: {
            token: token
        },
        success: function(data) {
            console.log(data);
            // TODO: Set the data all over the page
        },
        error: function(data) {
            // TODO: implement a modal type of alert
            console.log(data);
        }
    });
}