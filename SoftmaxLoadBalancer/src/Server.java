import java.util.Random;

public class Server {
    private double meanLatency; // Gerçek ortalama gecikme süresi
    private final Random random = new Random();

    public Server(double initialMean) {
        this.meanLatency = initialMean;
    }

    // Sunucunun performansını zamanla değiştiren metod (Non-stationary)
    public void drift() {
        // Her adımda ortalama gecikme %1-2 oranında rastgele değişsin
        meanLatency += random.nextGaussian() * 0.5;
        if (meanLatency < 1) meanLatency = 1; // 1ms'den az olmasın
    }

    // Sunucudan bir yanıt süresi al (Gürültülü/Normal Dağılım)
    public double getResponse() {
        return meanLatency + (random.nextGaussian() * 2.0); // Ortalama + gürültü
    }

    public double getTrueMean() { return meanLatency; }
}