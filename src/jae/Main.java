package jae;

public class Main
{
    public static void main(String[] args)
    {
        CallHandler handler = new CallHandler();

        //Let's make a GET request
        System.out.println(handler.getName());

        //Let's make a POST request
        handler.setName(System.out::println, "Jae");
    }
}