INSERT INTO
    users (id, name)
VALUES
    (1, 'Alice'),
    (2, 'Bob');

INSERT INTO
    orders (id, user_id, created_at)
VALUES
    (1, 1, NOW ()),
    (2, 1, NOW ()),
    (3, 1, NOW ()),
    (4, 2, NOW ()),
    (5, 2, NOW ());

INSERT INTO
    review (id, user_id, comment)
VALUES
    (1, 1, 'Great product!'),
    (2, 1, 'Loved it!');