INSERT INTO users (id, email, name, password_hash, created_at, updated_at)
VALUES (
  '11111111-1111-1111-1111-111111111111',
  'poster@example.com',
  'Poster',
  '',
  now(),
  now()
);

INSERT INTO user_roles (user_id, roles)
VALUES ('11111111-1111-1111-1111-111111111111', 'USER');

INSERT INTO posts (id, user_id, title, content, published, created_at, updated_at)
VALUES (
  '22222222-2222-2222-2222-222222222222',
  '11111111-1111-1111-1111-111111111111',
  'Seeded Post',
  'This is a public seeded post created by Flyway. Use this to test GET /api/posts without authentication.',
  true,
  now(),
  now()
);

INSERT INTO post_tags (post_id, tags)
VALUES
  ('22222222-2222-2222-2222-222222222222', 'seed'),
  ('22222222-2222-2222-2222-222222222222', 'public');
