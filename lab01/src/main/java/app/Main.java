package app;

import com.fastcgi.FCGIInterface;

public class Main {
    public static void main(String[] args)  {
        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0){
            RequestHandler requestHandler = new RequestHandler();
            requestHandler.handle();
        }
    }

}