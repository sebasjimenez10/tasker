
/**
 * This function stores user session data
 */
function setUserData(name, last_name, username, user_id){
    sessionStorage.setItem( "name", name );
    sessionStorage.setItem( "last_name", last_name );
    sessionStorage.setItem( "username", username );
    sessionStorage.setItem( "id_user", user_id );
}

/**
 * This function deletes user session data
 */
function deleteUserData() {
    sessionStorage.removeItem( "name" );
    sessionStorage.removeItem( "last_name" );
    sessionStorage.removeItem( "username" );
    sessionStorage.removeItem( "id_user" );
}

/**
 * This function returns specified session item
 */
function getUserDataItem(item) {
    return sessionStorage.getItem(item);
}

/**
 * This function returns true if user is logged false otherwise
 */
function isUserLogged() {
    var isUserIn = new Boolean(false);
    
    var name = getUserDataItem("name");
    var lastName = getUserDataItem("last_name");
    var username = getUserDataItem("username");
    var id_user = getUserDataItem("id_user");
    
    if( name != null && lastName != null && username != null && id_user != null ){
        isUserIn = true;
    }
    return isUserIn;
}