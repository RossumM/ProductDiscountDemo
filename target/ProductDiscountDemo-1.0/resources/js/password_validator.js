//var supports_input_validity = function()
//    {
//      var i = document.createElement("input");
//      return "setCustomValidity" in i;
//    }
//
//    if(supports_input_validity()) {

//      var usernameInput = document.getElementById("field_username");
//      usernameInput.setCustomValidity(usernameInput.title);
//
//      var pwd1Input = document.getElementById("field_pwd1");
//      pwd1Input.setCustomValidity(pwd1Input.title);
//
//      var pwd2Input = document.getElementById("field_pwd2");
      
    //}

var password1 = document.getElementById('password');
var password2 = document.getElementById('password2');

var checkPasswordValidity = function() {
    if (password1.value != password2.value) {
        password1.setCustomValidity('De wachtwoorden moeten gelijk zijn.');
    } else {
        password1.setCustomValidity('');
    }        
};

password1.addEventListener('change', checkPasswordValidity, false);
password2.addEventListener('change', checkPasswordValidity, false);