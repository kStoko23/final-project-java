import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Gwiazda implements Serializable
{
    //region Pola
    private final String nazwa;  //zawiera 3 wielkie litery i 4 cyfry
    private  String nazwaKatalogowa;  //jest ona połączeniem litery z alfabetu greckiego i nazwy gwiazdozbioru
    private  final String deklinacja;  //if polkula == "Północna" then 0 <= deklinacja <= 90 || if polkula == "Południowa" then -90 <= deklinacja <= 0
    private  final String rektascensja;  //0 <= rektascensja <= 24
    private  final double obserwowanaWielkoscGwiazdowa;  //-26.74 <= obserwowanaWielkoscGwiazdowa <= 15
    private  final double odlegloscWLatachSwietlnych;
    private  final double absolutnaWielkoscGwiazdowa;  //obserwowanaWielkoscGwiazdowa - 5 * (Math.log10((odlegloscWLatachSwietlnych/3.26)) + 5);
    private  final String gwiazdozbior;
    private  final String polkula;
    private  final double temperatura;  //temperatura w stopniach Celsjusza, temperatura >= 2000
    private  final double masa;  //0.1 <= masa <= 50

    private  int indexWGwiazdozbiorze;  //zawiera index gwiazdozbioru w HashMapie z gwiazdozbiorami - pozwala to na odpowiednie przypisanie litery z alfabetu greckiego


    private static final char[] alfabetGrecki= new char[]  //tablica pomocznica - zawiera symbole potrzebne do utworzenia nazwy katalogowej
            {
                    'α','β','γ','δ','ε','ζ','η','θ','ι','κ',
                    'λ','μ','ν','ξ','ο','π','ρ','ς','σ','τ',
                    'υ','φ','χ','ψ','ω','ϊ','ϋ','ό','ύ','ώ',
                    'Ϗ','ϐ','ϑ','ϒ','ϓ','ϔ','ϕ','ϖ','ϗ','Ϙ',
                    'ϙ','Ϛ','ϛ','Ϝ','ϝ','Ϟ','ϟ','Ϡ','ϡ','Ϣ',
                    'ϣ','Ϥ','ϥ','Ϧ','ϧ','Ϩ','ϩ','Ϫ','ϫ','Ϭ',
                    'ϭ','Ϯ','ϯ','ϰ','ϱ','ϲ','ϳ','ϴ','ϵ','϶',
                    'Ϸ','ϸ','Ϲ','Ϻ','ϻ','ϼ','Ͻ','Ͼ','Ͽ'
            };

    public static ArrayList<Gwiazda> listaGwiazd = new ArrayList<>();  //zawiera wszystkie obiekty klasy Gwiazda
    public static HashMap<String, Integer> listaGwiazdozbiorow = new HashMap<>();  //zawiera wszystkie gwiazdozbiory i indeksuje je
                                                                                   //key - nazwa gwiazdozbioru, value - ilosc gwiazd w gwiazdozbiorze



    //endregion

    //region Konstruktor
    public Gwiazda(String nazwa, String deklinacja, String rektascensja, double obserwowanaWielkoscGwiazdowa, double odlegloscWLatachSwietlnych, String gwiazdozbior, String polkula, double temperatura, double masa)
    {
        this.nazwa = nazwa;
        this.deklinacja = deklinacja;
        this.rektascensja = rektascensja;
        this.obserwowanaWielkoscGwiazdowa = obserwowanaWielkoscGwiazdowa;
        this.odlegloscWLatachSwietlnych = odlegloscWLatachSwietlnych;
        this.gwiazdozbior = gwiazdozbior;
        this.polkula = polkula.toLowerCase();
        this.temperatura = temperatura;
        this.masa = masa;
        this.UtworzNazweKatalogowa(gwiazdozbior);  //przypisuje danej gwiazdzie jej nazwe katalogowa
        this.absolutnaWielkoscGwiazdowa = ObliczanieAbsolutnaWielkoscGwiazdowa();
    }
    //endregion

    //region Metody pomocnicze

    private void  UtworzNazweKatalogowa(String gwiazdozbior)  //metoda tworzaca nazwe katalogowa
    {
        listaGwiazdozbiorow.putIfAbsent(gwiazdozbior, 0);  //jeśli gwiazdozbior nie istnieje w HashMapie to go dodaje
        this.nazwaKatalogowa=alfabetGrecki[listaGwiazdozbiorow.get(gwiazdozbior)]+" "+gwiazdozbior;  //tworzy nazwe katalogowa łaczac litere z alfabetu greckiego z nazwa gwiazdozbioru
        this.indexWGwiazdozbiorze=listaGwiazdozbiorow.get(gwiazdozbior);  //sprawdza index gwiazdozbioru w HashMapie
        listaGwiazdozbiorow.computeIfPresent(gwiazdozbior,(k,v) -> v+1); //przygotowuje nowy index dla następnego gwiazdozbioru
    }

    public static void wypisywanie()  //wyswietla wszystkie gwiazdy w liscie wraz z ich parametrami
    {
        for (Gwiazda gwiazda : listaGwiazd) {
            System.out.println("Nazwa: " + gwiazda.nazwa + "\nNazwa katalogowa: " + gwiazda.nazwaKatalogowa + "\nDeklinacja: " + gwiazda.deklinacja + "\nRektascensja: " + gwiazda.rektascensja + "\nObserwowana wielkość gwiazdowa: " + gwiazda.obserwowanaWielkoscGwiazdowa + "\nOdległość w latach świetlnych: " + gwiazda.odlegloscWLatachSwietlnych + "\nAbsolutna wielkość gwiazdowa: " + gwiazda.absolutnaWielkoscGwiazdowa + "\nGwiazdozbiór: " + gwiazda.gwiazdozbior + "\nPółkula: " + gwiazda.polkula + "\nTemperatura: " + gwiazda.temperatura + "\nMasa: " + gwiazda.masa);
            System.out.println();
            System.out.println();
        }

    }

    public double ObliczanieAbsolutnaWielkoscGwiazdowa()  //oblicza absolutna wielkosc gwiazdowa
    {
        return this.obserwowanaWielkoscGwiazdowa - 5 * (Math.log10(this.odlegloscWLatachSwietlnych/3.26) + 5);
    }

    //endregion

    //region Sprawdzanie zaleźności - metody sprawdzajace czy uzytkowanik wprowadzil wlasciwe dane
    public static boolean sprawdzNazwe(String nazwa)
    {
        if(nazwa.length() != 7)
            return false;
        for(int i = 0; i < 3; i++)
        {
            if(nazwa.charAt(i) < 'A' || nazwa.charAt(i) > 'Z')
                return false;
        }
        for(int i = 3; i < 7; i++)
        {
            if(nazwa.charAt(i) < '0' || nazwa.charAt(i) > '9')
                return false;
        }
        return true;
    }
    public static boolean sprawdzDeklinacje(String deklinacja)  //sprawdzenie czy wprowadzona deklinacja jest w formacie +/-00'00'00.00'
    {


           if(deklinacja.length() != 13)
                return false;
            if(deklinacja.charAt(0) != '+' && deklinacja.charAt(0) != '-')
                return false;
            for(int i = 1; i < 3; i++)
            {
                if(deklinacja.charAt(i) < '0' || deklinacja.charAt(i) > '9')
                    return false;
            }
            if(deklinacja.charAt(3) != '\'')
                return false;
            for(int i = 4; i < 6; i++)
            {
                if(deklinacja.charAt(i) < '0' || deklinacja.charAt(i) > '9')
                    return false;
            }
            if(deklinacja.charAt(6) != '\'')
                return false;
            for(int i = 7; i < 9; i++)
            {
                if(deklinacja.charAt(i) < '0' || deklinacja.charAt(i) > '9')
                    return false;
            }
            if(deklinacja.charAt(9) != '.')
                return false;
            for(int i = 10; i < 12; i++)
            {
                if(deklinacja.charAt(i) < '0' || deklinacja.charAt(i) > '9')
                    return false;
            }
            if(deklinacja.charAt(12) != '\'')
                return false;

            return true;

    }

    public static boolean sprawdzRektascensje(String rektascensja)  //sprawdzenie czy wprowadzona deklinacja jest w formacie 00h00m00s
    {
        if(rektascensja.length() != 9)
            return false;
        for(int i = 0; i < 2; i++)
        {
            if(rektascensja.charAt(i) < '0' || rektascensja.charAt(i) > '9')
                return false;
        }
        if(rektascensja.charAt(2) != 'h')
            return false;
        for(int i = 3; i < 5; i++)
        {
            if(rektascensja.charAt(i) < '0' || rektascensja.charAt(i) > '9')
                return false;
        }
        if(rektascensja.charAt(5) != 'm')
            return false;
        for(int i = 6; i < 8; i++)
        {
            if(rektascensja.charAt(i) < '0' || rektascensja.charAt(i) > '9')
                return false;
        }
        if(rektascensja.charAt(8) != 's')
            return false;

        return true;
    }


    public static boolean sprawdzObserwowanaWielkoscGwiazdowa(double obserwowanaWielkoscGwiazdowa)
    {
        return !(obserwowanaWielkoscGwiazdowa < -26.74) && !(obserwowanaWielkoscGwiazdowa > 15);
    }

    public static boolean sprawdzOdlegloscWLatachSwietlnych(double odlegloscWLatachSwietlnych)
    {
        return !(odlegloscWLatachSwietlnych < 0);
    }

    public static boolean sprawdzPolkule(String polkula)
    {
        return polkula.equals("polnocna") || polkula.equals("poludniowa");
    }

    public static boolean sprawdzTemperature(double temperatura)
    {
        return !(temperatura < 2000);
    }

    public static boolean sprawdzMase(double masa)
    {
        return !(masa < 0.1) && !(masa > 50);
    }

    //endregion

    //region Dodawanie, usuwanie i wyświetlanie gwiazd

    public static void dodajGwiazde(Scanner skaner)
    {
        System.out.println();
        System.out.println("DODAWANIE GWIAZDY");
        System.out.println("Wpisując dane, które są ułamkami używaj przecinka, a nie kropki");  //gdy uzywa sie kropki, to program wyrzuca blad
        System.out.println();

        //uzytkownik wprowadza dane

        System.out.println("Podaj nazwę gwiazdy (nazwa gwiazdy składa się z 3 dużych liter oraz 4 cyfr): ");
        String nazwa = skaner.next();

        System.out.println("Podaj półkulę gwiazdy (Polnocna/Poludniowa): ");
        String polkula = skaner.next().toLowerCase();

        System.out.println("Podaj deklinację gwiazdy (0 do 90 stopni dla gwiazd znajdujących się na półkuli północnej oraz 0 do -90 stopni dla gwiazd na półkuli południowej, wartość podajemy jako +/-XX'YY'ZZ.ZZ'): ");
        String deklinacja = skaner.next();

        System.out.println("Podaj rektascensję gwiazdy (przyjmuje wartości od 00h do 24h, wartość podajemy jako XXhYYmZZs): ");
        String rektascensja = skaner.next();

        System.out.println("Podaj gwiazdozbiór gwiazdy: ");
        String gwiazdozbior = skaner.next();

        System.out.println("Podaj obserwowaną wielkość gwiazdową gwiazdy ( minimalna dopuszczalna wielkość gwiazdowa wynosi -26.74, a maksymalna wartość wynosi 15.00): ");
        double obserwowanaWielkoscGwiazdowa = skaner.nextDouble();

        System.out.println("Podaj odległość w latach świetlnych gwiazdy: ");
        double odlegloscWLatachSwietlnych = skaner.nextDouble();

        System.out.println("Podaj temperaturę gwiazdy (wartosc podawana w stopniach celsjusza, minimalna temperatura gwiazdy to 2000 stopni celsjusza: ");
        double temperatura = skaner.nextDouble();

        System.out.println("Podaj masę gwiazdy (minimalna masa gwiazdy wynosi 0.1 masy Słońca, natomiast maksymalna dopuszczalna masa wynosi 50): ");
        double masa = skaner.nextDouble();

        //program sprawdza czy dane wprowadzone przez uzytkownika sa poprawne

        if(sprawdzNazwe(nazwa))
        {
            if (sprawdzDeklinacje(deklinacja))
            {
                if (sprawdzRektascensje(rektascensja))
                {
                    if (sprawdzObserwowanaWielkoscGwiazdowa(obserwowanaWielkoscGwiazdowa))
                    {
                        if (sprawdzOdlegloscWLatachSwietlnych(odlegloscWLatachSwietlnych))
                        {
                            if (sprawdzPolkule(polkula))
                            {
                                if (sprawdzTemperature(temperatura))
                                {
                                    if (sprawdzMase(masa))
                                    {
                                        listaGwiazd.add(new Gwiazda(nazwa, deklinacja, rektascensja, obserwowanaWielkoscGwiazdowa, odlegloscWLatachSwietlnych, gwiazdozbior, polkula, temperatura, masa));
                                        System.out.println("Dodano gwiazdę");  //jesli dane sa poprawne, gwiazda zostaje dodana do listy
                                    } else
                                        System.out.println("Niepoprawna masa");
                                } else
                                        System.out.println("Niepoprawna temperatura");
                            } else
                                System.out.println("Niepoprawna półkula");
                        } else
                            System.out.println("Niepoprawna odległość w latach świetlnych");
                    } else
                        System.out.println("Niepoprawna obserwowana wielkość gwiazdowa");
                } else
                    System.out.println("Niepoprawna rektascensja");
            } else
                System.out.println("Niepoprawna deklinacja");
        } else
            System.out.println("Niepoprawna nazwa");
    }

    public static void usunGwiazde(Scanner skaner)
    {
        String gwiazdozbiorUsunietejGwiazdy;
        int indekxUsunietejGwiazdy;
        String nazwaGwiazdyDoUsuniecia;

        //uzytkownik podaje nazwe gwiazdy, ktora ma zostac usunieta

        System.out.println("Podaj nazwę gwiazdy, którą chcesz usunąć: ");
        nazwaGwiazdyDoUsuniecia = skaner.next();

        for (int i = 0; i < listaGwiazd.size(); i++)
        {
            if (listaGwiazd.get(i).nazwa.equals(nazwaGwiazdyDoUsuniecia))  //program sprawdza czy gwiazda znajduje sie na liscie
            {
                gwiazdozbiorUsunietejGwiazdy = listaGwiazd.get(i).gwiazdozbior;  //program sprawdza jaki gwiazdozbior ma gwiazda, ktora ma zostac usunieta
                indekxUsunietejGwiazdy = listaGwiazd.get(i).indexWGwiazdozbiorze;  //program sprawdza jaki indeks ma gwiazda, ktora ma zostac usunieta i zapisuje go w zminnej indekxUsunietejGwiazdy
                listaGwiazd.remove(i); //program usuwa gwiazde z listy
                System.out.println("Usunięto gwiazdę " + nazwaGwiazdyDoUsuniecia + " z gwiazdozbioru " + gwiazdozbiorUsunietejGwiazdy);

                listaGwiazdozbiorow.computeIfPresent(gwiazdozbiorUsunietejGwiazdy, (k, v) -> indekxUsunietejGwiazdy);  //jesli gwiazdozbior usunietej gwiazdy istnieje w hashmapie, zmienia value na indeks usunietej gwiazdy
                for(Gwiazda gwiazda : listaGwiazd)
                {
                    if ((gwiazda.gwiazdozbior.equals(gwiazdozbiorUsunietejGwiazdy) && gwiazda.indexWGwiazdozbiorze> indekxUsunietejGwiazdy))  //dla każdej gwiazdy w liscie gwiazd o indexie wiekszym niz index usunietej gwiazdy na nowo generuje nazwe katalogowa
                    {
                        gwiazda.UtworzNazweKatalogowa(gwiazda.gwiazdozbior);
                    }
                }
                break;
            }else System.out.println("Nie ma takiej gwiazdy w bazie!");
        }
    }

    public static void wyswietlGwiazdy()
    {
        if (listaGwiazd.size() == 0)
        {
            System.out.println("Brak gwiazd w bazie");
            System.out.println();
            System.out.println();
        } else
        {
            wypisywanie();
        }
    }
    //endregion

    //region Szukanie obiektów
    public static void wyszukiwanieObiektow(Scanner skaner)
    {
        do
        {
            System.out.println();

            System.out.println("Wybierz co chcesz wyszukać \n1)Wyszukaj wszystkie gwiazdy w gwiazdozbiorze \n2)Wyszukaj gwiazdy znajdujące się w odległości x parseków od Ziemii \n3)Wyszukaj gwiazdy o temperaturze w zadanym przedziale \n4)Wyszukaj gwiazdy o wielkości gwiazdowej w zadanym przedziale \n5)Wyszukaj gwiazdy z półkuli północnej lub południowej \n6)Wyszukaj potencjalne supernowe (Supernowe to gwiazdy, których masa przekracza tzw. granicę Chandrasekhara, która wynosi 1.44 masy Słońca) \n7)Wróć do menu głównego \nPodaj cyfrę wybranej opcji:");
            int wybor = skaner.nextInt();

            if (wybor == 1)
            {
                wyszukajGwiazdyWGwiazdozbiorze(skaner);
            }
            else if (wybor == 2)
            {
                wyszukajGwiazdyWZadanymPromieniu(skaner);
            }
            else if (wybor == 3)
            {
                wyszukajGwiazdyWTemperaturze(skaner);
            }
            else if (wybor == 4)
            {
                wyszukajGwiazdyWObserwowanejWielkosciGwiazdowej(skaner);
            }
            else if (wybor == 5)
            {
                wyszukajGwiazdyZPolkuli(skaner);
            }
            else if (wybor == 6)
            {
                wyszukajSupernowe();
            }
            else if (wybor == 7)
            {
                break;
            }
            else
            {
                System.out.println("Nie ma takiej opcji");
            }
        }while (true);

    }
    //endregion

    //region Szukanie obiektów - metody pomocnicze

    public static void wyszukajGwiazdyWGwiazdozbiorze(Scanner skaner)
    {
        System.out.println("Podaj nazwę gwiazdozbioru, w którym chcesz wyszukać gwiazdy: ");
        String nazwaGwiazdozbioru = skaner.next();
        int licznik = 0;

        for (Gwiazda gwiazda : listaGwiazd)
        {
            if (gwiazda.gwiazdozbior.equals(nazwaGwiazdozbioru))
            {
                licznik++;
                System.out.println(gwiazda.nazwa + " - " + gwiazda.gwiazdozbior);
            }
        }
        if (licznik == 0)
        {
            System.out.println("Nie ma takiego gwiazdozbioru w bazie");
            System.out.println();
        }
    }

    public static void wyszukajGwiazdyWZadanymPromieniu(Scanner skaner)
    {
        System.out.println("Podaj promień w parsekach, w którym chcesz wyszukać gwiazdy: ");
        double promienWParsekach = skaner.nextDouble();
        double promienWLatachSwietlnych = promienWParsekach * 3.26;
        int licznik = 0;

        for (Gwiazda gwiazda : listaGwiazd)
        {
            if (gwiazda.odlegloscWLatachSwietlnych <= promienWLatachSwietlnych)
            {
                licznik++;
                System.out.println(gwiazda.nazwa + " - " + (gwiazda.odlegloscWLatachSwietlnych/3.26) + " parseków");
            }
        }
        if (licznik == 0)
        {
            System.out.println("Nie ma takiej gwiazdy w bazie");
            System.out.println();
        }
    }

    public static void wyszukajGwiazdyWTemperaturze(Scanner skaner)
    {
        System.out.println("Podaj temperaturę minimalną: ");
        double temperaturaMinimalna = skaner.nextDouble();
        System.out.println("Podaj temperaturę maksymalną: ");
        double temperaturaMaksymalna = skaner.nextDouble();
        int licznik = 0;

        for (Gwiazda gwiazda : listaGwiazd)
        {
            if (gwiazda.temperatura >= temperaturaMinimalna && gwiazda.temperatura <= temperaturaMaksymalna)
            {
                licznik++;
                System.out.println(gwiazda.nazwa + " - " + gwiazda.temperatura);
            }
        }
        if (licznik == 0)
        {
            System.out.println("Nie ma takiej gwiazdy w bazie");
            System.out.println();
        }
    }

    public static void wyszukajGwiazdyWObserwowanejWielkosciGwiazdowej(Scanner skaner)
    {
        System.out.println("Podaj wielkość gwiazdową minimalną: ");
        double wielkoscGwiazdowaMinimalna = skaner.nextDouble();
        System.out.println("Podaj wielkość gwiazdową maksymalną: ");
        double wielkoscGwiazdowaMaksymalna = skaner.nextDouble();
        int licznik = 0;

        for (Gwiazda gwiazda : listaGwiazd)
        {
            if (gwiazda.obserwowanaWielkoscGwiazdowa >= wielkoscGwiazdowaMinimalna && gwiazda.obserwowanaWielkoscGwiazdowa <= wielkoscGwiazdowaMaksymalna)
            {
                licznik++;
                System.out.println(gwiazda.nazwa + " - " + gwiazda.obserwowanaWielkoscGwiazdowa);
            }
        }
        if (licznik == 0)
        {
            System.out.println("Nie ma takiej gwiazdy w bazie");
            System.out.println();
        }
    }

    public static void wyszukajGwiazdyZPolkuli(Scanner skaner)
    {
        System.out.println("Podaj półkulę (Polnocna/Poludniowa): ");
        String polkula = skaner.next().toLowerCase();
        int licznik = 0;

        for (Gwiazda gwiazda : listaGwiazd)
        {
            if (gwiazda.polkula.equals(polkula))
            {
                licznik++;
                System.out.println(gwiazda.nazwa + " - " + gwiazda.polkula);
            }
        }
        if (licznik == 0)
        {
            System.out.println("Nie ma takiej gwiazdy w bazie");
            System.out.println();
        }
    }

    public static void wyszukajSupernowe()
    {
        int licznik = 0;

        for (Gwiazda gwiazda : listaGwiazd)
        {
            if (gwiazda.masa > 1.44)
            {
                licznik++;
                System.out.println(gwiazda.nazwa + " - " + gwiazda.masa);
            }
        }
        if (licznik == 0)
        {
            System.out.println("Nie ma takiej gwiazdy w bazie");
            System.out.println();
        }
    }



    //endregion
}
