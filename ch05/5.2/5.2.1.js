var preArr = [1, 2, 3, 4, 5]
var postArr = [6, 7, 8, 9]

function arrange(arr, pre, rt) {
    pre = pre || []
    rt = rt || []

    arr.forEach(function(item) {
        var pre2 = pre.filter(function(){
            return true
        })
        pre2.push(item)

        if(arr.length > 1) {
            var arr2 = arr.filter(function(item2){
                if (item !== item2) {
                    return true
                }
            })
            arrange(arr2, pre2, rt)

        } else {
            rt.push(pre2)
        }
    })

    return rt
}


function filter(arr) {
    return arr.filter(function(item) {
        var idx1 = item.indexOf(1)
        var idx3 = item.indexOf(3)
        var idx5 = item.indexOf(5)
        var idx2 = item.indexOf(2)
        var idx4 = item.indexOf(4)

        if (idx1 < idx3 && idx3 < idx5 && idx2 < idx4) {
            return true
        }
    })
}


console.log(filter(arrange(preArr)).map(function(item) {
    return item.concat(postArr)
}))
