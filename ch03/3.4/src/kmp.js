var failureFunction = require('./failure-function')

module.exports = kmp

function kmp(str, search) {
    var failure = failureFunction(search)
    var s = 0
    for (var i = 0; i < str.length; i++) {
        while (s > 0 && str[i] != search[s]) {
            s = failure[s-1]
        }
        if(str[i] == search[s]){
            s = s + 1
        }
        if(s == search.length){
            return true
        }
    }
    return false
}