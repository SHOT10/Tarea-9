import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        System.out.print("Ingrese el número de usuarios: ");
        int numUsuarios = scanner.nextInt();


        AtomicInteger contrasenasValidas = new AtomicInteger(0);


        for (int i = 0; i < numUsuarios; i++) {
            Thread thread = new PasswordValidatorThread(contrasenasValidas);
            thread.start();
        }


        while (Thread.activeCount() > 1) {
            Thread.yield();
        }


        System.out.println("Total de contraseñas válidas: " + contrasenasValidas.get());
    }
}

class PasswordValidator {
    public static boolean validate(String password) {

        String regex = "^(?=.*[a-z].*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,}$";
        return password.matches(regex);
    }
}

class PasswordValidatorThread extends Thread {
    private AtomicInteger contrasenasValidas;

    public PasswordValidatorThread(AtomicInteger contrasenasValidas) {
        this.contrasenasValidas = contrasenasValidas;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();


        if (PasswordValidator.validate(password)) {
            System.out.println("Contraseña válida.");
            contrasenasValidas.incrementAndGet();
        } else {
            System.out.println("La contraseña no cumple con los requisitos.");
        }
    }
}
