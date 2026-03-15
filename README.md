# For Grade 6

## Odpowiedzi na pytania

1. **Jaka jest podstawowa funkcja wątku EDT w aplikacjach Swing?**  
   Wątek EDT (Event Dispatch Thread) jest głównym wątkiem odpowiedzialnym za obsługę zdarzeń interfejsu użytkownika i renderowanie komponentów Swing. Wszystkie operacje na GUI muszą być wykonywane w tym wątku, aby zapewnić stabilność i responsywność interfejsu.

2. **Dlaczego próba bezpośredniego wykonania zapytania SQL w metodzie actionPerformed przycisku jest zła?**  
   Ponieważ zapytanie SQL jest długotrwałą operacją (I/O), która blokuje wątek EDT. Gdy EDT jest zablokowany, interfejs przestaje reagować na interakcje użytkownika, co prowadzi do „zawieszenia” aplikacji (brak odpowiedzi na kliknięcia, przeciąganie okna itp.).

3. **Która metoda klasy SwingWorker jest bezpieczna do aktualizacji komponentów GUI (np. JLabel) po zakończeniu długiej operacji?**  
   Metoda `done()` jest wykonywana w wątku EDT po zakończeniu `doInBackground()`, więc bezpiecznie można w niej aktualizować komponenty Swing.

4. **W którym wątku (EDT czy roboczym) jest wykonywana metoda doInBackground()?**  
   Metoda `doInBackground()` jest wykonywana w wątku roboczym (poza EDT), co pozwala na przeprowadzenie długotrwałych operacji bez blokowania interfejsu.

---

## Zastosowane technologie

- **Kotlin** – język programowania
- **Java Swing** – biblioteka GUI
- **FlatLaf** – nowoczesny wygląd
- **Kapper** – lekki ORM (Object-Relational Mapping) dla Kotlinu, użyty do mapowania obiektów na bazę danych
- **HikariCP** – szybkie i niezawodne połączenie z bazą danych (connection pooling)
- **PostgreSQL** – relacyjna baza danych (uruchamiana w Dockerze)
- **Docker Compose** – do zarządzania kontenerem bazy danych

## Dodatkowy feature

- **Usuwanie zadań** – możliwość wyboru zadania z tabeli i usunięcia go po potwierdzeniu, z użyciem asynchronicznego `DeleteTaskWorker`.