package kniznica;

public class KniznicaMain {
    public static void main(String[] args) {
        KniznicaService kniznicaService = new KniznicaService();

        kniznicaService.fillKniznica();
        kniznicaService.startMenu();
    }
}
