# Burger Shop

A Java Swing desktop application for managing a burger shop. Staff can maintain a burger menu, build customer orders with customizations, apply discount codes, and see a live price breakdown — all through a two-panel GUI.

## Features

- Pre-loaded menu with four default burgers
- Add new burgers to the menu with name, price, and description
- Build orders by selecting burgers and checking customization options (extra cheese, bacon, no pickles)
- Remove individual items or clear the entire order
- Apply discount codes (e.g. `SAVE10` for 10% off)
- Live subtotal, discount, and total labels that update on every change

## Tech Stack

| Layer    | Technology        |
|----------|-------------------|
| Language | Java              |
| GUI      | Java Swing        |
| Build    | `javac` (no build tool required) |

## Project Structure

```
burgershop/
├── Main.java           # Entry point — launches the Swing frame
├── BurgerShopFE.java   # GUI layout and all user interaction
├── BurgerManager.java  # Menu management and default burger seed
├── Order.java          # Order state, customizations, and price calculations
├── Burger.java         # Burger model (name, price, description)
├── Customization.java  # Customization option model (name, extra cost)
└── Discount.java       # Discount model and application logic
```

## Getting Started

### Prerequisites

- Java JDK 11+

### Compile & Run

```bash
javac *.java
java Main
```

## Author

**Pratik Lamichhane** · [GitHub](https://github.com/lcpratik) · [LinkedIn](https://linkedin.com/in/lcpratik)
