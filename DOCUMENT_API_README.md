# Document Upload API

This API allows you to upload PDF documents along with metadata using multipart form data.

## API Endpoints

### 1. Upload PDF Document
**POST** `/api/documents/upload`

Uploads a PDF document with additional metadata.

**Parameters:**
- `file` (required): PDF file to upload
- `description` (optional): Document description
- `category` (optional): Document category
- `uploadedBy` (required): Username of the person uploading the document
- `status` (optional): Document status (defaults to "ACTIVE")

**Example using cURL:**
```bash
curl -X POST "http://localhost:8083/api/documents/upload" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/document.pdf" \
  -F "description=Sample PDF document" \
  -F "category=REPORTS" \
  -F "uploadedBy=john.doe" \
  -F "status=ACTIVE"
```

**Example using Postman:**
1. Set method to POST
2. Set URL to `http://localhost:8083/api/documents/upload`
3. In Body tab, select "form-data"
4. Add the following key-value pairs:
   - `file` (Type: File) - Select your PDF file
   - `description` (Type: Text) - "Sample PDF document"
   - `category` (Type: Text) - "REPORTS"
   - `uploadedBy` (Type: Text) - "john.doe"
   - `status` (Type: Text) - "ACTIVE"

### 2. Upload PDF with JSON Metadata
**POST** `/api/documents/upload-json`

Uploads a PDF document with stringified JSON metadata.

**Parameters:**
- `file` (required): PDF file to upload
- `metadata` (required): Stringified JSON containing document metadata

**JSON Metadata Structure:**
```json
{
  "description": "Sample PDF document",
  "category": "REPORTS",
  "uploadedBy": "john.doe",
  "status": "ACTIVE"
}
```

**Example using cURL:**
```bash
curl -X POST "http://localhost:8083/api/documents/upload-json" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/document.pdf" \
  -F 'metadata={"description":"Sample PDF document","category":"REPORTS","uploadedBy":"john.doe","status":"ACTIVE"}'
```

**Example using Postman:**
1. Set method to POST
2. Set URL to `http://localhost:8083/api/documents/upload-json`
3. In Body tab, select "form-data"
4. Add the following key-value pairs:
   - `file` (Type: File) - Select your PDF file
   - `metadata` (Type: Text) - `{"description":"Sample PDF document","category":"REPORTS","uploadedBy":"john.doe","status":"ACTIVE"}`

### 3. Get All Documents
**GET** `/api/documents`

Retrieves all uploaded documents.

### 4. Get Document by ID
**GET** `/api/documents/{id}`

Retrieves a specific document by its ID.

### 5. Download Document File
**GET** `/api/documents/{id}/download`

Downloads the PDF file from the database.

**Example using cURL:**
```bash
curl -X GET "http://localhost:8083/api/documents/1/download" \
  -H "Accept: application/pdf" \
  --output downloaded_document.pdf
```

**Example using Postman:**
1. Set method to GET
2. Set URL to `http://localhost:8083/api/documents/1/download`
3. Send request - the file will be downloaded automatically

### 6. Get Documents by Category
**GET** `/api/documents/category/{category}`

Retrieves documents filtered by category.

### 7. Get Documents by Uploader
**GET** `/api/documents/uploader/{uploadedBy}`

Retrieves documents uploaded by a specific user.

### 8. Update Document Metadata
**PUT** `/api/documents/{id}`

Updates document description, category, and status.

**Request Body:**
```json
{
  "description": "Updated description",
  "category": "UPDATED_CATEGORY",
  "status": "INACTIVE"
}
```

**Example using cURL:**
```bash
curl -X PUT "http://localhost:8083/api/documents/1" \
  -H "Content-Type: application/json" \
  -d '{"description":"Updated description","category":"UPDATED_CATEGORY","status":"INACTIVE"}'
```

### 9. Delete Document
**DELETE** `/api/documents/{id}`

Deletes a document and its associated file.

## File Storage

- **PDF files are stored directly in the database** as BLOB (Binary Large Object)
- Each file gets a unique UUID-based filename for reference
- Original filename is preserved in the database
- Only PDF files are accepted
- File data is stored in the `fileData` column as `LONGBLOB`

## Database Schema

The `documents` table contains the following fields:
- `id`: Primary key
- `fileName`: Unique filename for reference
- `originalFileName`: Original uploaded filename
- `contentType`: MIME type (application/pdf)
- `fileSize`: File size in bytes
- `fileData`: PDF file content stored as LONGBLOB
- `description`: Document description
- `uploadedBy`: Username of uploader
- `uploadedAt`: Timestamp of upload
- `category`: Document category
- `status`: Document status

## Error Handling

The API includes proper error handling for:
- File not found (404)
- Invalid file type (400)
- Empty files (400)
- File storage errors (500)

## Configuration

File upload limits are configured in `application.properties`:
- Maximum file size: 10MB
- Maximum request size: 10MB
- Multipart uploads enabled

## Swagger Documentation

Access the interactive API documentation at:
`http://localhost:8083/swagger-ui.html` 