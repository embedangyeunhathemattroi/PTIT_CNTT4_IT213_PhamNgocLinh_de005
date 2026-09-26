# Base project — Đề 005: Đặt chỗ đỗ xe

Starter gồm Gradle, Spring Boot/Spring AI dependencies, model/repository, corpus và seed data. Các package config, controller, dto, exception, service (rag/chat/langfuse/mcp) và tool chỉ là khung rỗng.

Sinh viên dùng credential cá nhân qua environment variables; không commit secret. Khởi động ứng dụng để JPA tạo bảng, sau đó chạy src/main/resources/seed_data.sql.
## Langfuse

Docker Langfuse được cấu hình sẵn trong `docker-compose-langfuse.yml` và dùng host mặc định `http://localhost:3000`. Sinh viên chỉ cần khởi động Docker, tạo project Langfuse cá nhân rồi điền `LANGFUSE_PUBLIC_KEY` và `LANGFUSE_SECRET_KEY` qua environment.

## Cấu hình AI linh hoạt

Project có hai profile AI. Chỉ chọn profile qua environment, không cần sửa code hoặc `application.yml`.

- **Cloud (mặc định):** đặt `SPRING_PROFILES_ACTIVE=cloud`; khai báo `OPENAI_BASE_URL`, `OPENAI_API_KEY`, `OPENAI_CHAT_MODEL`, `OPENAI_EMBEDDING_MODEL`. Dimension Pgvector mặc định là `3072`; đổi `EMBEDDING_DIMENSIONS` nếu embedding model dùng dimension khác.
- **Local (Ollama):** đặt `SPRING_PROFILES_ACTIVE=local`; khai báo tùy chọn `OLLAMA_BASE_URL` (mặc định `http://localhost:11434`), `OLLAMA_CHAT_MODEL` (mặc định `qwen2.5:7b`) và `OLLAMA_EMBEDDING_MODEL` (mặc định `nomic-embed-text`). Dimension Pgvector mặc định là `768`.

Trước khi chạy local, cài Ollama và tải model tương ứng, ví dụ `ollama pull qwen2.5:7b` và `ollama pull nomic-embed-text`. Khi đổi embedding model hoặc đổi giữa cloud/local, dùng `PGVECTOR_TABLE_NAME` khác hoặc đảm bảo `EMBEDDING_DIMENSIONS` khớp dimension của model để tránh lỗi vector store.

## Chạy local và test bằng Postman

Project mặc định dùng PostgreSQL/pgvector Docker ở `localhost:5434` để tránh đụng PostgreSQL cài sẵn ở port `5432`.

```powershell
docker compose -f docker-compose-langfuse.yml up -d parking-db
.\gradlew.bat bootRun
```

Endpoint xử lý duyệt request:

```http
POST http://localhost:8080/api/operations/approve-request
Content-Type: application/json

{
  "requestId": "REQ-...",
  "decision": "APPROVE",
  "note": "Duyet yeu cau"
}
```

`decision` chỉ nhận `APPROVE` hoặc `REJECT`. Nếu API trả `Không tìm thấy request`, cần tạo request đặt chỗ trước qua assistant/tool rồi dùng đúng `requestId` được trả về.
