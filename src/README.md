## Verbose++

**Verbose++** is a fun, **Java-parody programming language** designed to **embrace verbosity**, explicit typing, and ultra-clear code.  
It’s perfect for developers who want to **type more than they actually code**.

---

## Features

- Explicit **variable declarations**: `variable(int) a := 10;`
- Strong typing for clarity.
- Nested `if` and `for` structures with readable syntax.
- **Terminal output** through `terminal.print()`.
- **Verbose design**: everything is spelled out, no shortcuts.
---
## Hello World

```verbose++  
variable(string) message := "Hello, Verbose++!";
terminal.print(message);
```
---
## Greatest of three numbers

```verbose++
variable(int) a := 10;
variable(int) b := 20;
variable(int) c := 15;

if a > b {
    if a > c {
        terminal.print("a is greatest");
    } else {
        terminal.print("c is greatest");
    }
} else {
    if b > c {
        terminal.print("b is greatest");
    } else {
        terminal.print("c is greatest");
    }
}
```
---
## For Loop
```verbose++
for(variable(int) i := 1; i < 5; i := i + 1) {
    terminal.print(i / 2);
}
```
---
## Functions( Methods ) -> Future Idea
```verbose++
method greet(variable(string) name): void {
    terminal.print("Hello, " + name);
}
```