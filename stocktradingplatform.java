import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings;
    private double balance;

    public Portfolio(double initialBalance) {
        this.holdings = new HashMap<>();
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.getPrice() * quantity;
        if (balance >= totalCost) {
            balance -= totalCost;
            holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Insufficient balance to buy " + stock.getSymbol());
        }
    }

    public void sellStock(Stock stock, int quantity) {
        if (holdings.containsKey(stock.getSymbol()) && holdings.get(stock.getSymbol()) >= quantity) {
            double totalSale = stock.getPrice() * quantity;
            balance += totalSale;
            holdings.put(stock.getSymbol(), holdings.get(stock.getSymbol()) - quantity);
            if (holdings.get(stock.getSymbol()) == 0) {
                holdings.remove(stock.getSymbol());
            }
            System.out.println("Sold " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Insufficient holdings to sell " + quantity + " shares of " + stock.getSymbol());
        }
    }

    public void showPortfolio() {
        System.out.println("Portfolio balance: $" + balance);
        System.out.println("Holdings:");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            System.out.println("Stock: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }
}

public class StockTradingPlatform {

    private static Map<String, Stock> marketData = new HashMap<>();

    public static void main(String[] args) {
        // Initialize market data
        marketData.put("AAPL", new Stock("AAPL", 150.00));
        marketData.put("GOOGL", new Stock("GOOGL", 2800.00));
        marketData.put("AMZN", new Stock("AMZN", 3400.00));

        // Create a portfolio with an initial balance
        Portfolio portfolio = new Portfolio(10000.00);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. Show Portfolio");
            System.out.println("4. Update Market Data");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQuantity = scanner.nextInt();
                    if (marketData.containsKey(buySymbol)) {
                        portfolio.buyStock(marketData.get(buySymbol), buyQuantity);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQuantity = scanner.nextInt();
                    if (marketData.containsKey(sellSymbol)) {
                        portfolio.sellStock(marketData.get(sellSymbol), sellQuantity);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;
                case 3:
                    portfolio.showPortfolio();
                    break;
                case 4:
                    updateMarketData();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void updateMarketData() {
        Random random = new Random();
        for (Stock stock : marketData.values()) {
            double change = (random.nextDouble() * 10) - 5; // Random price change between -5 and +5
            stock.setPrice(stock.getPrice() + change);
            System.out.println("Updated price of " + stock.getSymbol() + " to $" + stock.getPrice());
        }
    }
}
