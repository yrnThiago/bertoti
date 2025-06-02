package observer;

import java.util.ArrayList;
import java.util.List;

interface OrderObserver {
    void update(Order order);
}

class EmailNotifier implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println("Email: Pedido #" + order.getOrderId() + " mudou o status para: " + order.getStatus());
    }
}

class SMSNotifier implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println("SMS: Pedido #" + order.getOrderId() + " agora está: " + order.getStatus());
    }
}

interface OrderObservable {
    void addObserver(OrderObserver observer);
    void removeObserver(OrderObserver observer);
    void notifyObservers();
}

class Order implements OrderObservable {
    private String orderId;
    private String status;
    private List<OrderObserver> observers = new ArrayList<>();

    public Order(String orderId) {
        this.orderId = orderId;
        this.status = "Criado";
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers();
    }

    @Override
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }
}

public class ObserverDemo {
    public static void main(String[] args) {
        Order order = new Order("123A");

        EmailNotifier emailNotifier = new EmailNotifier();
        SMSNotifier smsNotifier = new SMSNotifier();

        order.addObserver(emailNotifier);
        order.addObserver(smsNotifier);

        order.setStatus("Pagamento Aprovado");
        System.out.println("---");
        order.setStatus("Enviado");

        System.out.println("--- Removendo notificador de email ---");
        order.removeObserver(emailNotifier);
        order.setStatus("Entregue");
    }
}