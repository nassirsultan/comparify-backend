# Comparify - Backend

Spring Boot REST API that powers the Comparify document comparison app using Cosine Similarity algorithm.

## 🧠 Algorithm

Documents are compared using **Cosine Similarity**:
1. Extract text from uploaded files
2. Build word frequency vectors (stop words filtered)
3. Calculate dot product of both vectors
4. Calculate magnitude of each vector
5. Return `dot product / (magnitude1 × magnitude2)` as similarity score

## 🛠️ Tech Stack

- Java 11
- Spring Boot
- Maven
- REST API

## 📦 Getting Started

### Prerequisites
- Java 11+
- Maven

### Run Locally
```bash
mvn spring-boot:run
```

App runs on `http://localhost:8080`

## 🔗 API Endpoints

### Compare Documents
`POST /api/compare`

| Parameter | Type | Description |
|-----------|------|-------------|
| file1 | MultipartFile | First .txt document |
| file2 | MultipartFile | Second .txt document |

**Response:**
```json
{
  "similarityScore": 89.44
}
```

**Error Responses:**
| Status | Reason |
|--------|--------|
| 400 | Missing or empty file |
| 415 | Unsupported file type (non .txt) |
| 500 | Internal server error |

## 🔒 Validation
- Only `.txt` files accepted
- Both files must be provided
- File type validated via MIME type check

## 👨‍💻 Author
Nassir Sultan
