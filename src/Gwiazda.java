public class Gwiazda {

    private final String nazwa;
    private final String nazwakatalogowa;
    private final String deklinacja;
    private final String rektascensja;
    private final double obserwowanaWielkoscGwiazdowa;
    private final double absolutnaWielkoscGwiazdowa;
    private final double odleglosc;
    private  final String gwiazdozbior;
    private final String polkula;
    private final int temperatura;
    private final  float masa;


    public Gwiazda(String nazwa, String nazwakatalogowa, String deklinacja,
                   String rektascensja, double obserwowanaWielkoscGwiazdowa, double absolutnaWielkoscGwiazdowa,
                   double odleglosc, String gwiazdozbior, String polkula, int temperatura, float masa){
        this.nazwa=nazwa;
        this.nazwakatalogowa=nazwakatalogowa;
        this.deklinacja=deklinacja;
        this.rektascensja=rektascensja;
        this.obserwowanaWielkoscGwiazdowa=obserwowanaWielkoscGwiazdowa;
        this.absolutnaWielkoscGwiazdowa=absolutnaWielkoscGwiazdowa;
        this.odleglosc=odleglosc;
        this.gwiazdozbior=gwiazdozbior;
        this.polkula=polkula;
        this.temperatura=temperatura;
        this.masa=masa;
    }

}
