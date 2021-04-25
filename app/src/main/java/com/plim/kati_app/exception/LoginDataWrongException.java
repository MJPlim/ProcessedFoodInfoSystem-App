package com.plim.kati_app.exception;

import com.plim.kati_app.constants.Constant_yun;

/**
 * 로그인 시 아이디나 비밀번호가 틀렸을 때 발생하는 exception
 */
public class LoginDataWrongException extends Exception{


    public LoginDataWrongException(){
        super(Constant_yun.LOGIN_DATA_WRONG_EXCEPTION_MESSAGE);
    }



}
