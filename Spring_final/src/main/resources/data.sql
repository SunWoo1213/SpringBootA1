-- 기존 상품 데이터가 있다면 삭제
DELETE FROM purchase;
DELETE FROM product;

-- PC 카테고리 상품
INSERT INTO product (name, description, price, image_url, category, created_at) 
VALUES ('게이밍 데스크탑 PC', '최신 RTX 4070 그래픽카드, i7 프로세서, 32GB RAM 탑재', 2499000, 
'https://images.unsplash.com/photo-1587831990711-23ca6441447b?ixlib=rb-4.0.3', 
'PC', CURRENT_TIMESTAMP);

-- Smartphone 카테고리 상품
INSERT INTO product (name, description, price, image_url, category, created_at) 
VALUES ('최신형 스마트폰', '6.7인치 AMOLED 디스플레이, 108MP 카메라, 5G 지원', 1299000, 
'https://images.unsplash.com/photo-1592899677977-9c10ca588bbd?ixlib=rb-4.0.3', 
'SMARTPHONE', CURRENT_TIMESTAMP);

-- Laptop 카테고리 상품
INSERT INTO product (name, description, price, image_url, category, created_at) 
VALUES ('울트라 노트북', '13.3인치 4K 디스플레이, 초경량 1.2kg, 최대 20시간 배터리', 1899000, 
'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?ixlib=rb-4.0.3', 
'LAPTOP', CURRENT_TIMESTAMP); 