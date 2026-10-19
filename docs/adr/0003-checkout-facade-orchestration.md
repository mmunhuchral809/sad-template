# ADR-0003: CheckoutFacade for Order Orchestration

## Status
Accepted

## Date
2026-10-19

## Context
Several controllers coordinated the inventory, payment and order services directly.
Each controller repeated the same steps (check stock, reserve, charge, create order)
and its own rollback logic. When the payment failed, a controller could forget to
release the reserved stock, which left the stock count wrong.

## Decision
We introduce `CheckoutFacade` as the only entry point for checkout. It checks the stock,
reserves it, charges the payment and creates the order in a fixed sequence. If the
payment fails, it releases the reserved stock. Controllers depend only on the facade.
Business rules stay inside the domain services, not in the facade.

## Consequences
Benefits:
- Controllers become simple and the checkout steps exist in one place only.
- The rollback logic can be tested once with unit tests.

Costs:
1. An extra layer of indirection and extra request/response classes (`CheckoutRequest`, `Receipt`).
2. Risk of a "god object": if business rules move into the facade, it grows and becomes hard to maintain.
