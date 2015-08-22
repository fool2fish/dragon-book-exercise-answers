module.exports = failureFunction

function failureFunction(str) {
    var failure = [0]
    var j = 0
    for (var i = 1; i < str.length; i++) {
        while(j > 0 && str[j] != str[i]) {
            j = failure[j-1]
        }
        if(str[j] == str[i]){
            j++
        }
        failure[i] = j
    }
    return failure
}
