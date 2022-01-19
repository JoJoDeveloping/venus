var kotlin = require('kotlin_raw');

if (typeof kotlin !== "undefined" && typeof kotlin.kotlin !== "undefined" && typeof kotlin.kotlin.Number === "undefined") {
    kotlin.kotlin.Number = function (){};
    kotlin.kotlin.Number.prototype.call = function(a){};
}

module.exports = kotlin;
     