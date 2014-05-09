
/**
 * 
 * @returns {undefined}
 */
function checkUserSession(){
    if( sessionStorage.getItem('token') === null ){
        window.location = "/tasker/loginError.html";
    }
}

/**
 * 
 * @returns {undefined}
 */
function logOut() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('username');
    window.location = '/tasker/index.html';
}