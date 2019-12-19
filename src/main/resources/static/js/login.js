const username = document.querySelector('.username');
const password = document.querySelector('.password');
const verificationCode = document.querySelector('.verification-code');
const btnLoading = document.querySelector('.btn-loading');
const submitBtn = document.querySelector('.submit-btn');
const usernameError = document.querySelector('.username-error');
const passwordError = document.querySelector('.password-error');
const verificationCodeError = document.querySelector('.verification-code-error');
window.onload = function () {
    if (usernameError.innerHTML !== '') {
        username.className = 'username username-input-error';
        usernameError.className = 'username-error opacity1';
    }
};

function handleSubmit(e) {
    if (username.value === '') {
        username.className = 'username username-input-error';
        usernameError.className = 'username-error opacity1';
        usernameError.innerHTML = '请输入用户名';
        return false;
    }
    if (password.value === '') {
        password.className = 'password password-input-error';
        passwordError.className = 'password-error opacity1';
        passwordError.innerHTML = '请输入密码';
        return false
    }

    if (verificationCode !== null && verificationCode.value === '') {
        verificationCode.className = 'verification-code verification-code-input-error';
        verificationCodeError.className = 'verification-code-error opacity1';
        verificationCodeError.innerHTML = '请输入验证码';
        return false
    }

    btnLoading.style.display = 'block';
    submitBtn.style.backgroundColor = '#b3b3b3';
    submitBtn.style.cursor = 'default';
    return true;
}

function usernameInputFocus() {
    if (username.className === 'username username-input-error') {
        username.className = 'username username-input-default';
        usernameError.className = 'username-error opacity0';
    }
}

function passwordInputFocus() {
    if (password.className === 'password password-input-error') {
        password.className = 'password password-input-default';
        passwordError.className = 'password-error opacity0';
    }
}

function verificationCodeFocus() {
    if (verificationCode !== null &&  verificationCode.className === 'verification-code verification-code-input-error') {
        verificationCode.className = 'verification-code verification-code-input-default';
        verificationCodeError.className = 'verification-code-error opacity0';
    }
}

function refreshImageValidateCode() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            //6.接收数据
            var res = xmlHttp.responseText;
            if (res !== undefined) {
                var result = JSON.parse(res);
                if (result.deviceId !== undefined && result.deviceId !== ''){
                    document.getElementById("imageValidateCode").setAttribute("src", "http://sculptor.cdk8s.com/sculptor-boot-backend/validate/code/imageCode?deviceId=" + result.deviceId)
                }
            }
        }
    };
    xmlHttp.open("GET", "/sculptor-boot-backend/oauth/refreshImageValidateCode", false);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send();
}
