
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
        error: function(data){
            if( data.status === 406 ){
                if( data.responseText === "User name already taken" ){
                    myAlert(signinAlert, data.responseText, alertError);
                }
            }else{
                if( data.status === 500 ){
                    window.location.replace("/tasker/serverDown.html");
                }
            }
        }
    });
}