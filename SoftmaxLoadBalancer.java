public class SoftmaxLoadBalancer {
    private final double[] Q; // Tahmini ödül değerleri (Reward estimation)
    private final double tau; // Sıcaklık parametresi (Temperature)
    private final int k;      // Sunucu sayısı

    public SoftmaxLoadBalancer(int k, double tau) {
        this.k = k;
        this.tau = tau;
        this.Q = new double[k];
        for (int i = 0; i < k; i++) Q[i] = 100.0; // İyimser başlangıç
    }

    public int selectServer() {
        double[] expValues = new double[k];
        double sumExp = 0;

        // Gecikme süresi düşük olan daha iyidir, bu yüzden Q değerini ters orantılı kullanıyoruz
        // Reward = 1 / Latency mantığıyla Softmax uyguluyoruz
        for (int i = 0; i < k; i++) {
            expValues[i] = Math.exp(Q[i] / tau);
            sumExp += expValues[i];
        }

        double rand = Math.random();
        double cumulative = 0;
        for (int i = 0; i < k; i++) {
            cumulative += expValues[i] / sumExp;
            if (rand <= cumulative) return i;
        }
        return k - 1;
    }

    public void update(int index, double latency) {
        // Ödül: Düşük latency yüksek ödül demektir
        double reward = 100.0 / latency;
        double alpha = 0.1; // Öğrenme oranı (Step size)
        Q[index] += alpha * (reward - Q[index]);
    }
}
