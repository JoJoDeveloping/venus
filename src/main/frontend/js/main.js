
window.load_error = function (msg) {
    var e = document.getElementById("loading_text");
    e.innerHTML = "An error has occurred while loading Venus!";
    if (typeof msg == "string") {
        e.innerHTML += "<br><br><div style=\"font-size:0.75em\">" + msg.replace(/\n/g, '<br>') + "</div>";
    }
    e.innerHTML += "<br><br><br><div style=\"font-size:0.6em\">Try to reload the page. If that does not fix the issue, make an issue post on <a href='https://github.com/ThaumicMekanism/venus/issues'>github</a>.<font></font>";
    document.getElementById("loader").style.opacity = 0;
}

window.driver_load_done = function () {
    /* Check if packages are all loaded */
    h  = function(){
        if (driver.driver_complete_loading) {
            load_done();
            return
        }
        setTimeout(h, 10);
    };
    setTimeout(h, 10);
};

window.load_done = function () {
    load_update_message("Done!");
    window.document.body.classList.add("loaded");
    window.onerror = null;
};

window.load_update_message = function (msg) {
    elm = document.getElementById("load-msg");
    if (elm === null) {
        msg = "Could not update the load message to: " + msg;
        load_error(msg);
        console.error(msg);
        return
    }
    elm.innerHTML = msg.replace(/\n/g, "<br>");
}


function load_error_fn(message, source, lineno, colno, error) {
    load_error(message + "\nMore info in the console.");
}

window.onerror = load_error_fn;
window.default_alert = window.alert;
window.alert = function(message) {
    alertify.alert(message.replace(/\n/g, "<br>"));
    // alertify.alert.apply(this, arguments);
};
// window.confirm = alertify.confirm;
// window.prompt = alertify.prompt;
alertify.alert()
    .setting({
        'title': 'Venus'
    });
alertify.confirm()
    .setting({
        'title': 'Venus'
    });
alertify.prompt()
    .setting({
        'title': 'Venus'
    });

function main_venus() {
    require('venus_wrapper').setup_venus();
}

main_venus();

