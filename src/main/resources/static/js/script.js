console.log("Script loaded");

let currentTheme = getTheme();

document.addEventListener('DOMContentLoaded', () => {
    changeTheme();
});

function changeTheme(){
    //     set to web page
    changePageTheme(currentTheme, currentTheme);
    //     set the listener to change theme button
    const changeThemeButton = document.querySelector('#theme_change_button')
    const oldTheme = currentTheme;
    changeThemeButton.addEventListener("click", (event) => {
        console.log("change theme button clicked");
        if (currentTheme === "dark"){
            currentTheme = "light";
        }else{
            currentTheme = "dark"
        }

        changePageTheme(currentTheme, oldTheme);
    });
}

//set theme to localstorage
function setTheme(theme){
    localStorage.setItem("theme", theme);
}
//get theme from localstorage
function getTheme(){
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

function changePageTheme(theme, oldTheme){
    //  localstorage theme update
    setTheme(currentTheme);
    document.querySelector('html').classList.remove(oldTheme);
    document.querySelector('html').classList.add(theme);

    document.querySelector('#theme_change_button').querySelector("span").textContent =
        theme === 'light' ? 'dark' : 'light'
}