package com.example.calculator;

import java.util.Stack;

public class Calculations{
    public static String solve(String str){
        Stack<Integer> stk1 = new Stack<>();
        Stack<Character> stk2 = new Stack<>();
        int n=str.length();
        int i = 0;
        while(i < n){
            int num = 0;
            if(str.charAt(i) >= '0' && str.charAt(i) <='9'){
                while( i < n && str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                    num = (num*10)+(str.charAt(i)-'0');
                    i++;
                }
                stk1.push(num);
            }

            else{
                if (str.charAt(i) == '(') stk2.push(str.charAt(i++));

                else if(str.charAt(i) == '+' || str.charAt(i) == '-' ||
                        str.charAt(i) == 'x' || str.charAt(i) == '/'|| str.charAt(i) == '^'){
                    while( stk1.size() > 1 && stk2.size() > 0 && (priority(str.charAt(i)) < priority(stk2.peek()))){
                        solve(stk1, stk2);
                    }
                    stk2.push(str.charAt(i++));
                }

                else{
                    while(stk2 .size()>0 && stk2.peek() != '('){
                        solve(stk1, stk2);
                    }
                    if(stk2.size() > 0)stk2.pop();
                    i++;
                    //stk2.push(str.charAt(i++));
                }
            }
        }

        while(stk1.size() > 1 && stk2.size() > 0){
            solve(stk1, stk2);
        }

        return (Integer.toString(stk1.peek()));
    }
    private static int priority(char ch){
        if(ch == '^') return 4;
        else if(ch == 'x' || ch == '/') return 3;
        else if(ch == '-') return 2;
        else if(ch == '+') return 1;

        return -1;
    }

    private static void solve(Stack<Integer> stk1, Stack<Character> stk2){
        int num2 = stk1.pop();
        int num1 = stk1.pop();

        char operator = stk2.pop();
        if(operator == '^') stk1.push((int)(Math.pow(num1, num2)));
        else if(operator == '+') stk1.push(num1+num2);
        else if(operator == '-') stk1.push(num1-num2);
        else if(operator == 'x') stk1.push(num1*num2);
        else if(operator == '/') stk1.push(num1/num2);

    }
}
