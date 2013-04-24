module.exports = function(str) {
    var failures = [0]
    var j = 0
    for(var i = 1; i < str.length; i++) {
        while(j > 0 && str[j] != str[i]) {
            j = failures[j-1]
        }
        if(str[j] == str[i]){
            j++
        }
        failures[i] = j
    }
    return failures
}
