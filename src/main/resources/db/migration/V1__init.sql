CREATE TABLE roles (
  id BIGINT NOT NULL AUTO_INCREMENT,
  code VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_roles_code (code)
) ENGINE=InnoDB;

CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  email VARCHAR(200) NOT NULL,
  display_name VARCHAR(200) NOT NULL,
  status VARCHAR(20) NOT NULL,
  version BIGINT NOT NULL,
  deleted BIT(1) NOT NULL,
  preferences JSON NOT NULL,
  created_at DATETIME(6) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_email (email),
  KEY idx_users_email (email),
  KEY idx_users_status (status)
) ENGINE=InnoDB;

CREATE TABLE user_profiles (
  user_id BIGINT NOT NULL,
  bio VARCHAR(1000) NOT NULL,
  avatar_url VARCHAR(500) NULL,
  created_at DATETIME(6) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT fk_user_profiles_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE user_addresses (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  label VARCHAR(100) NOT NULL,
  line1 VARCHAR(200) NOT NULL,
  city VARCHAR(100) NOT NULL,
  postal_code VARCHAR(20) NOT NULL,
  country VARCHAR(2) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  KEY idx_user_addresses_user_id (user_id),
  CONSTRAINT fk_user_addresses_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  assigned_at DATETIME(6) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES roles (id)
) ENGINE=InnoDB;

CREATE TABLE user_phone_numbers (
  user_id BIGINT NOT NULL,
  phone_number VARCHAR(30) NOT NULL,
  PRIMARY KEY (user_id, phone_number),
  CONSTRAINT fk_user_phone_numbers_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE payment_methods (
  id BIGINT NOT NULL AUTO_INCREMENT,
  pm_type VARCHAR(20) NOT NULL,
  provider VARCHAR(20) NOT NULL,
  card_last4 VARCHAR(4) NULL,
  card_network VARCHAR(20) NULL,
  vpa VARCHAR(200) NULL,
  created_at DATETIME(6) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE purchase_orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  status VARCHAR(20) NOT NULL,
  metadata JSON NOT NULL,
  payment_method_id BIGINT NULL,
  version BIGINT NOT NULL,
  created_at DATETIME(6) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  KEY idx_purchase_orders_user_id (user_id),
  KEY idx_purchase_orders_status (status),
  UNIQUE KEY uk_purchase_orders_payment_method_id (payment_method_id),
  CONSTRAINT fk_purchase_orders_user_id FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_purchase_orders_payment_method_id FOREIGN KEY (payment_method_id) REFERENCES payment_methods (id)
) ENGINE=InnoDB;

CREATE TABLE purchase_order_items (
  order_id BIGINT NOT NULL,
  line_no INT NOT NULL,
  sku VARCHAR(80) NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(19, 2) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  created_by VARCHAR(100) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  updated_by VARCHAR(100) NOT NULL,
  PRIMARY KEY (order_id, line_no),
  CONSTRAINT fk_purchase_order_items_order_id FOREIGN KEY (order_id) REFERENCES purchase_orders (id)
) ENGINE=InnoDB;
