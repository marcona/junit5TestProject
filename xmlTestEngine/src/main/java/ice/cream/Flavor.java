package ice.cream;

enum Flavor {

    Chocolate,

    Stracciatella,

    Vanilla;

    static Flavor random() {
        Flavor[] flavors = values();
        return flavors[(int) (Math.random() * flavors.length)];
    }
}