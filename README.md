# Softmax-Load-Balancer
# Probabilistic Client-Side Load Balancer using Softmax Action Selection

Projenin temel amacı, yanıt süreleri zamanla değişen ve gürültülü (non-stationary & noisy) sunucu kümelerinde toplam gecikme süresini (latency) minimize eden olasılıksal bir yük dengeleyici simüle etmektir.

## 📌 Problem Tanımı
Dağıtık sistemlerde sunucu performansları statik değildir; ağ yoğunluğu veya donanımsal faktörler nedeniyle performans dalgalanmaları yaşanır. Klasik **Round-Robin** veya **Random** algoritmaları bu dinamik değişimlere uyum sağlayamaz. Bu çalışmada, takviyeli öğrenme (reinforcement learning) prensiplerinden biri olan **Softmax Action Selection** kullanılarak, en verimli kaynağın sürekli olarak yeniden keşfedilmesi hedeflenmiştir.

## 🛠️ Teknik Özellikler ve Implementasyon
* **Dil:** Java
* **IDE:** IntelliJ IDEA
* **Algoritma:** Softmax Action Selection
* **Simülasyon Yapısı:** Sunucuların performansları `drift()` metodu ile her adımda rastgele (Gaussian gürültü ile) değiştirilmektedir.

### Softmax ve Nümerik Stabilite
Algoritma, sunucuların geçmiş ödül (reward) ortalamalarını üstel bir düzleme taşır. 
$$P_t(i) = \frac{e^{Q_t(i)/\tau}}{\sum_{j=1}^{K} e^{Q_t(j)/\tau}}$$

Projede, $e^x$ hesaplamalarında oluşabilecek **Floating Point Overflow** (taşma) hatalarını önlemek adına sıcaklık parametresi ($\tau$) ve ödül normalizasyonu üzerinde nümerik stabilite önlemleri alınmıştır. Latency değerleri $100 / Latency$ formülü ile maksimizasyon problemine uygun "ödül" değerlerine dönüştürülmüştür.

## 📊 Algoritma Analizi

### Zaman Karmaşıklığı (Time Complexity)
* **Seçim İşlemi:** $K$ adet sunucu için olasılık hesabı yapıldığından her adımda **$O(K)$** zaman alır.
* **Güncelleme İşlemi:** Tek bir sunucunun tahmini ödül değeri güncellendiği için **$O(1)$** zaman alır.
* **Toplam:** $N$ adet istek için toplam çalışma zamanı **$O(N \times K)$**'dır.

### Alan Karmaşıklığı (Space Complexity)
* Sunucu performans verileri ve tahminleri dizilerde tutulduğu için bellek kullanımı sunucu sayısıyla doğru orantılıdır: **$O(K)$**.

## 🚀 Çalıştırma
1. Depoyu yerel makinenize clone'layın.
2. `src` klasöründeki dosyaları herhangi bir Java IDE'sinde (tercihen IntelliJ IDEA) açın.
3. `Main.java` sınıfını çalıştırarak simülasyonu başlatın.


