package com.cuizhanming.oop.behavioral;

/**
 * Observer Pattern - Notifies multiple objects about state changes
 * 观察者模式 - 通知多个对象状态变化
 */
public class ObserverPattern {

    // Subject interface
    public interface Subject {
        void registerObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers();
    }

    // Observer interface
    public interface Observer {
        void update(float temperature, float humidity, float pressure);
    }

    // Concrete subject
    public static class WeatherStation implements Subject {
        private final java.util.List<Observer> observers = new java.util.ArrayList<>();
        private float temperature;
        private float humidity;
        private float pressure;

        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
            System.out.println("Observer registered: " + observer.getClass().getSimpleName());
        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
            System.out.println("Observer removed: " + observer.getClass().getSimpleName());
        }

        @Override
        public void notifyObservers() {
            System.out.println("Notifying " + observers.size() + " observers...");
            for (Observer observer : observers) {
                observer.update(temperature, humidity, pressure);
            }
        }

        public void setMeasurements(float temperature, float humidity, float pressure) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
            measurementsChanged();
        }

        private void measurementsChanged() {
            notifyObservers();
        }

        // Getters
        public float getTemperature() { return temperature; }
        public float getHumidity() { return humidity; }
        public float getPressure() { return pressure; }
    }

    // Concrete observers
    public static class CurrentConditionsDisplay implements Observer {
        private float temperature;
        private float humidity;

        @Override
        public void update(float temperature, float humidity, float pressure) {
            this.temperature = temperature;
            this.humidity = humidity;
            display();
        }

        public void display() {
            System.out.println("Current conditions: " + temperature + "°C, " + humidity + "% humidity");
        }
    }

    public static class StatisticsDisplay implements Observer {
        private float maxTemp = Float.MIN_VALUE;
        private float minTemp = Float.MAX_VALUE;
        private float tempSum = 0.0f;
        private int numReadings = 0;

        @Override
        public void update(float temperature, float humidity, float pressure) {
            tempSum += temperature;
            numReadings++;

            if (temperature > maxTemp) {
                maxTemp = temperature;
            }

            if (temperature < minTemp) {
                minTemp = temperature;
            }

            display();
        }

        public void display() {
            System.out.printf("Avg/Max/Min temperature: %.1f/%.1f/%.1f°C%n",
                (tempSum / numReadings), maxTemp, minTemp);
        }
    }

    public static class ForecastDisplay implements Observer {
        private float currentPressure = 29.92f;
        private float lastPressure;

        @Override
        public void update(float temperature, float humidity, float pressure) {
            lastPressure = currentPressure;
            currentPressure = pressure;
            display();
        }

        public void display() {
            System.out.print("Forecast: ");
            if (currentPressure > lastPressure) {
                System.out.println("Improving weather on the way!");
            } else if (currentPressure == lastPressure) {
                System.out.println("More of the same");
            } else {
                System.out.println("Watch out for cooler, rainy weather");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Observer Pattern Demo ===");

        WeatherStation weatherStation = new WeatherStation();

        // Create observers
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();

        // Register observers
        weatherStation.registerObserver(currentDisplay);
        weatherStation.registerObserver(statisticsDisplay);
        weatherStation.registerObserver(forecastDisplay);

        // Update weather data
        System.out.println("\nWeather update 1:");
        weatherStation.setMeasurements(25.5f, 65.0f, 30.4f);

        System.out.println("\nWeather update 2:");
        weatherStation.setMeasurements(27.2f, 70.0f, 29.2f);

        System.out.println("\nWeather update 3:");
        weatherStation.setMeasurements(23.8f, 90.0f, 29.8f);

        // Remove an observer
        System.out.println("\nRemoving forecast display...");
        weatherStation.removeObserver(forecastDisplay);

        System.out.println("\nWeather update 4:");
        weatherStation.setMeasurements(26.1f, 55.0f, 30.1f);
    }
}
