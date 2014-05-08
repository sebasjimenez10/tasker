

/**
 * 
 * @returns {undefined}
 */
function deleteUserData() {
    sessionStorage.removeItem( "token" );
    sessionStorage.removeItem( "username" );
}

/**
 * 
 * @param {type} item
 * @returns {DOMString}
 */
function getUserDataItem(item) {
    return sessionStorage.getItem(item);
}

/**
 * This function returns true if user is logged false otherwise
 */
function isUserLogged() {
    var isUserIn = new Boolean(false);
    
    var username = getUserDataItem('username');
    var token = getUserDataItem('token');
    
    console.log( username, token );
    
    if( username !== null && token !== null ){
        isUserIn = true;
    }
    return isUserIn;
}