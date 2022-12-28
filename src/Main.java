import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner skaner = new Scanner(System.in);

        //uzytkownik wybiera co chce zrobic w programie

        do {
            System.out.println("Wybierz co chcesz zrobic \n1)Dodaj gwiazdę \n2)Usuń gwiazdę \n3)Wyświetl listę gwiazd \n4)Wyszukiwanie zaawansowane \n5)Zapisz dane o gwiazdach do pliku \nPodaj cyfrę wybranej opcji:");
            int wybor = skaner.nextInt();

            if (wybor == 1)
            {
                if (Gwiazda.listaGwiazd.size() < 79)
                {
                    Gwiazda.dodajGwiazde(skaner);
                }
                else
                {
                    System.out.println("Lista jest pełna :(");
                }

            } else if (wybor == 2)
            {
                Gwiazda.usunGwiazde(skaner);

            } else if (wybor == 3)
            {
                System.out.println();
                System.out.println("Lista gwiazd:");
                System.out.println();
                Gwiazda.wyswietlGwiazdy();

            } else if (wybor == 4)
            {
                Gwiazda.wyszukiwanieObiektow(skaner);

            } else if (wybor == 5)
            {
                try
                {
                    String nazwaPliku = "gwiazdy.txt";
                    FileOutputStream zapis = new FileOutputStream(nazwaPliku);
                    ObjectOutputStream zapisObiektow = new ObjectOutputStream(zapis);
                    zapisObiektow.writeObject(Gwiazda.listaGwiazd);
                    zapisObiektow.close();
                    zapis.close();
                    System.out.println(("Dane zapisane do pliku: " + nazwaPliku));
                } catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }

            } else
            {
                System.out.println("Wpisz poprawną cyfrę!");
            }
        } while (true);
    }
}
