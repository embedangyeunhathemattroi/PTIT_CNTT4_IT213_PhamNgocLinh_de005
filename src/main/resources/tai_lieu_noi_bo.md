# Sổ tay nội bộ — Đặt chỗ đỗ xe

## Tiêu chuẩn chỗ đỗ xe
Nhóm STANDARD phục vụ tối đa 2 người. Nhóm PREMIUM phục vụ tối đa 4 người. Nhóm PREMIUM chỉ dành cho yêu cầu có từ 2 người trở lên.

## Chính sách đỗ xe
Một yêu cầu tối đa 14 ngày. Mục đích phải mô tả rõ từ 10 đến 200 ký tự. Chỉ yêu cầu PENDING được quản lý xử lý.

## Hủy và phê duyệt
Yêu cầu được phê duyệt khi còn khả dụng trong toàn bộ khoảng thời gian và tuân thủ sức chứa. Quản lý có thể APPROVE hoặc REJECT và ghi chú quyết định.
Chạy Docker trước.
Thứ tự đúng:
docker compose -f docker-compose-langfuse.yml up -d parking-db
Sau đó set env:
$env:DB_URL="jdbc:postgresql://localhost:5432/parking_reservation"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="postgres"
$env:PGVECTOR_TABLE_NAME="vector_store"
$env:SPRING_PROFILES_ACTIVE="local"

$env:OLLAMA_BASE_URL="http://localhost:11434"
$env:OLLAMA_CHAT_MODEL="qwen2.5:7b"
$env:OLLAMA_EMBEDDING_MODEL="nomic-embed-text"
$env:EMBEDDING_DIMENSIONS="768"
ồi chạy app:
.\gradlew.bat bootRun
Tóm lại:
1.
Bật Docker Desktop.
2.
Chạy docker compose ... parking-db.
3.
Set env.
4.
Chạy .\gradlew.bat bootRun.