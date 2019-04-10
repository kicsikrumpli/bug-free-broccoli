package io.github.kicsikrumpli.sandbox;

import java.util.Objects;

public class Fortune {
    private String fortune;

    public Fortune(String fortune) {
        this.fortune = fortune;
    }

    public String getFortune() {
        return fortune;
    }

    public void setFortune(String fortune) {
        this.fortune = fortune;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fortune fortune1 = (Fortune) o;
        return Objects.equals(fortune, fortune1.fortune);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fortune);
    }

    @Override
    public String toString() {
        return "Fortune{" +
                "fortune='" + fortune + '\'' +
                '}';
    }
}
