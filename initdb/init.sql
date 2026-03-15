CREATE TABLE IF NOT EXISTS tasks (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title TEXT NOT NULL,
    description TEXT,
    is_done BOOLEAN NOT NULL DEFAULT FALSE
);

-- Przykładowe dane
INSERT INTO tasks (title, description, is_done) VALUES
    ('Zadanie 1', 'Opis zadania 1', false),
    ('Zadanie 2', 'Opis zadania 2', true),
    ('Zadanie 3', 'Opis zadania 3', false);