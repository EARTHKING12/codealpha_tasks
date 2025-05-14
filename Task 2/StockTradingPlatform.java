import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    void updatePrice() {
        // Simulate price changes
        double change = (Math.random() - 0.5) * 10;
        price = Math.max(1, price + change);
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double cash = 10000;

    void buy(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (cost > cash) {
            System.out.println("Insufficient funds.");
            return;
        }
        holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + quantity);
        cash -= cost;
        System.out.println("Bought " + quantity + " shares of " + stock.symbol);
    }

    void sell(Stock stock, int quantity) {
        int owned = holdings.getOrDefault(stock.symbol, 0);
        if (quantity > owned) {
            System.out.println("Not enough shares to sell.");
            return;
        }
        holdings.put(stock.symbol, owned - quantity);
        cash += stock.price * quantity;
        System.out.println("Sold " + quantity + " shares of " + stock.symbol);
    }

    void view(StockMarket market) {
        System.out.println("\n--- Portfolio ---");
        System.out.printf("Cash: $%.2f\n", cash);
        double totalValue = cash;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            Stock s = market.getStock(entry.getKey());
            double value = s.price * entry.getValue();
            totalValue += value;
            System.out.printf("%s: %d shares @ $%.2f each = $%.2f\n",
                    s.symbol, entry.getValue(), s.price, value);
        }
        System.out.printf("Total Value: $%.2f\n", totalValue);
    }
}

class StockMarket {
    Map<String, Stock> stocks = new HashMap<>();

    StockMarket() {
        stocks.put("AAPL", new Stock("AAPL", 150));
        stocks.put("GOOG", new Stock("GOOG", 2800));
        stocks.put("TSLA", new Stock("TSLA", 700));
    }

    void updatePrices() {
        for (Stock stock : stocks.values()) {
            stock.updatePrice();
        }
    }

    void showMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock stock : stocks.values()) {
            System.out.printf("%s: $%.2f\n", stock.symbol, stock.price);
        }
    }

    Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    boolean isValidSymbol(String symbol) {
        return stocks.containsKey(symbol);
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StockMarket market = new StockMarket();
        Portfolio portfolio = new Portfolio();

        while (true) {
            market.updatePrices();
            market.showMarket();

            System.out.println("\nChoose an option: ");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = sc.next().toUpperCase();
                    if (!market.isValidSymbol(buySymbol)) {
                        System.out.println("Invalid symbol.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();
                    portfolio.buy(market.getStock(buySymbol), buyQty);
                    break;

                case 2:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = sc.next().toUpperCase();
                    if (!market.isValidSymbol(sellSymbol)) {
                        System.out.println("Invalid symbol.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();
                    portfolio.sell(market.getStock(sellSymbol), sellQty);
                    break;

                case 3:
                    portfolio.view(market);
                    break;

                case 4:
                    System.out.println("Exiting.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
