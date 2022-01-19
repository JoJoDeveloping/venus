function setup_venus() {
    console.log("----------THIS IS THE END OF THE EXPECTED GET ERRORS!----------");
    try {
        load_update_message("Initializing codeMirror");
        window.venus = require('venus');
        window.venus_main = window.venus;
        window.driver = venus_main.venus.Driver;
        window.venus.api = venus_main.venus.api.API;
        window.simulatorAPI = venus_main.venus.api.venusbackend.simulator.Simulator;
        window.editor = document.getElementById("asm-editor");
        window.codeMirror = CodeMirror.fromTextArea(editor,
            {
                lineNumbers: true,
                mode: "riscv",
                indentUnit: 4,
                autofocus: true,
                lint: true,
                autoRefresh:true,
            }
        );
        if (window.CodeMirror.mac) { // This uses a custom codemirror which exposes this check.
            codeMirror.addKeyMap({"Cmd-/": function(cm){cm.execCommand('toggleComment')}})
        } else {
            codeMirror.addKeyMap({"Ctrl-/": function(cm){cm.execCommand('toggleComment')}})
        }
        window.codeMirror.setSize("100%", "88vh");
    } catch (e) {
        console.error(e);
        load_error(e.toString())
    }
}

module.exports.setup_venus = setup_venus;