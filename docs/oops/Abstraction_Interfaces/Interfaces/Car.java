package Lect5.Interfaces;

public class Car implements Engine,Brake,Media{
    @Override
    public void brake() {
        System.out.println("Breaking the vehicle");
    }

    @Override
    public void start() {
        System.out.println("Starting the vehicle");
    }

    @Override
    public void stop() {
        System.out.println("Stopping the vehicle");

    }

    @Override
    public void acc() {
        System.out.println("Vroom Vroom ");

    }
}
