use wavetechTest;

INSERT INTO bt_user (name, email, password, user_group, status, created_by, updated_by, created_at, updated_at)
VALUES (
    'Admin',
    'btadmin@gmail.com',
    'QXFfDqcsImWMIBNUvDQyg9W3ngO9H0QMiX9NYi0h8Uk=', -- password: Admin@WT123
    'admin',
    'active',
    'system',
    'system',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);