package PR;

public abstract class PriceData {

    private static double priceElectricity = 10;
    private static final double priceFuel = 15;

    public static double getPriceFuel() {
        return priceFuel;
    }

    public static double getPriceElectricity() {
        return priceElectricity;
    }

    public static void setPriceElectricity(double priceElectricity) {
        PriceData.priceElectricity = priceElectricity;
    }
}
