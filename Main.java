public class Main {
    public static void main(String[] args) {
        int K = 5; // 5 farklı sunucu
        int totalRequests = 1000;
        double tau = 0.5; // Sıcaklık parametresi

        Server[] servers = new Server[K];
        for (int i = 0; i < K; i++) {
            servers[i] = new Server(10 + (i * 10)); // 10ms, 20ms... başlangıçlı sunucular
        }

        SoftmaxLoadBalancer lb = new SoftmaxLoadBalancer(K, tau);
        double totalLatency = 0;

        System.out.println("Simülasyon Başlıyor...");
        System.out.println("Adım\tSecilen\tGecikme\tToplam Ortalama");

        for (int t = 1; t <= totalRequests; t++) {
            int selected = lb.selectServer();
            double latency = servers[selected].getResponse();

            lb.update(selected, latency);
            totalLatency += latency;

            // Her adımda sunucu performanslarını hafifçe kaydır (Non-stationary)
            for (Server s : servers) s.drift();

            if (t % 100 == 0) {
                System.out.printf("%d\tServer %d\t%.2fms\t%.2fms%n",
                        t, selected, latency, (totalLatency / t));
            }
        }

        System.out.println("\nSimülasyon Tamamlandı!");
        System.out.printf("Genel Ortalama Gecikme: %.2f ms%n", (totalLatency / totalRequests));
    }
}