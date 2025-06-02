class PaymentProcessorAntiPattern {
    public void processPayment(String type, int amount) {
        if ("credit_card".equals(type)) {
            System.out.println("Processando pagamento de R$" + amount + " com cartão de crédito.");
        } else if ("pix".equals(type)) {
            System.out.println("Processando pagamento de R$" + amount + " com PIX.");
        } else if ("boleto".equals(type)) {
            System.out.println("Processando pagamento de R$" + amount + " com Boleto.");
        } else {
            System.out.println("Tipo de pagamento inválido.");
        }
    }
}

public class AntiPatternDemo {
    public static void main(String[] args) {
        PaymentProcessorAntiPattern processor = new PaymentProcessorAntiPattern();
        processor.processPayment("credit_card", 200);
        processor.processPayment("pix", 75);
    }
}