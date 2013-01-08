# 第2章要点

### 1. 文法、语法制导翻译方案、语法制导的翻译器

以一个仅支持个位数加减法的表达式为例

1. 文法

    list -> list + digit | list - digit | digit
    
    digit -> 0 | 1 | … | 9

2. （消除了左递归的）语法制导翻译方案

    expr -> term test
    
    rest -> + term { print('+') } rest | - term { print('+') } rest | ε
    
    term -> 0 { print('0') } | 1 { print('1') } | … | 9 { print('9') }
                                
4. 语法制导的翻译器
    
    java代码见 p46

### 2. 语法树、语法分析树

以 2 + 5 - 9 为例

![语法树和语法分析树](https://raw.github.com/fool2fish/dragon-book-practice-answer/master/ch02/key-point/assets/dragonbook-keypoint-2.2-2.png)

### 3. 上下文无关文法、上下文相关文法?

### 4. 如何处理运算运算符（运算符（包括括号）有结合性和优先级）？

### 5. 避免二义性文法的有效原则？

- 避免镜像的语法对或对称的语法

### 6. 避免预测分析器因左递归文法造成的无限循环

产生式：

A -> A x | y

语法制导翻译伪代码片段：

    void A(){
        switch(lookahead){
            case x:
                A();match(x);break;
            case y:
                match(y):break;
            default:
                report("syntax error")
        }
    }

当语句符合 A x 形式时， A() 运算会陷入死循环，可以通过将产生式改为等价的非左递归形式来避免: 

B -> y C

C -> x C | ε

### 7. 为什么在右递归的文法中，包含了左结合运算符的表达式翻译会比较困难？