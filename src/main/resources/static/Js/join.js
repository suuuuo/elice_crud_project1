
//비밀번호 입력

document.addEventListener('DOMContentLoaded', function() {
    let alertDisplayed = false;
    document.addEventListener('focusout', function () {
        let username = document.getElementById('join_pw').value;
        let password = document.getElementById('repeat_pw').value;

        if (!alertDisplayed) {
            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다. 비밀번호를 확인해주세요.")
            alertDisplayed = true;
            document.getElementById('repeat_pw').focus();

        }
    }
}