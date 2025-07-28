# File Upload API Guide

This document explains different approaches for handling file uploads in Spring Boot REST APIs, including the use of `@RequestParam`, `@RequestPart`, and stringified JSON.

## Table of Contents
1. [Overview](#overview)
2. [@RequestParam Approach](#requestparam-approach)
3. [@RequestPart Approach](#requestpart-approach)
4. [Stringified JSON Approach](#stringified-json-approach)
5. [Comparison Table](#comparison-table)
6. [Best Practices](#best-practices)
7. [Troubleshooting](#troubleshooting)

## Overview

When building REST APIs that handle file uploads, you have several options for structuring the request:

- **@RequestParam**: Simple form-data with individual fields
- **@RequestPart**: Multipart form-data with structured parts
- **Stringified JSON**: JSON data sent as part of multipart request

## @RequestParam Approach

### What is @RequestParam?
`@RequestParam` is used to extract query parameters, form data, and parts from multipart requests.

### Use Case
Best for simple file uploads with basic metadata fields.

### API Endpoint
```java
@PostMapping("/upload")
public ResponseEntity<Document> uploadDocument(
    @RequestParam("file") MultipartFile file,
    @RequestParam("description") String description,
    @RequestParam("category") String category,
    @RequestParam("uploadedBy") String uploadedBy,
    @RequestParam(value = "status", required = false) String status) {
    // Implementation
}
```

### Request Structure
```
POST /api/documents/upload
Content-Type: multipart/form-data

Form Data:
- file: [PDF File]
- description: "Sample document"
- category: "REPORTS"
- uploadedBy: "john.doe"
- status: "ACTIVE"
```

### Postman Configuration
1. **Method**: POST
2. **URL**: `http://localhost:8083/api/documents/upload`
3. **Body**: form-data
4. **Fields**:
   - `file` (Type: File) - Select PDF file
   - `description` (Type: Text) - "Sample document"
   - `category` (Type: Text) - "REPORTS"
   - `uploadedBy` (Type: Text) - "john.doe"
   - `status` (Type: Text) - "ACTIVE"

### cURL Example
```bash
curl -X POST "http://localhost:8083/api/documents/upload" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@document.pdf" \
  -F "description=Sample document" \
  -F "category=REPORTS" \
  -F "uploadedBy=john.doe" \
  -F "status=ACTIVE"
```

### Pros
- ✅ Simple and straightforward
- ✅ Easy to understand and implement
- ✅ Works well with form-based uploads
- ✅ Good for simple metadata

### Cons
- ❌ Limited to simple data types
- ❌ No complex object structures
- ❌ Less flexible for complex metadata

## @RequestPart Approach

### What is @RequestPart?
`@RequestPart` is used to extract parts from multipart requests, allowing you to handle complex objects alongside files.

### Use Case
Best for file uploads with complex metadata objects.

### API Endpoint
```java
@PostMapping("/upload")
public ResponseEntity<Document> uploadDocument(
    @RequestPart("file") MultipartFile file,
    @RequestPart("metadata") DocumentUploadRequest metadata) {
    // Implementation
}
```

### Request Structure
```
POST /api/documents/upload
Content-Type: multipart/form-data

Form Data:
- file: [PDF File]
- metadata: {"description":"Sample","category":"REPORTS","uploadedBy":"john.doe","status":"ACTIVE"}
```

### Postman Configuration
1. **Method**: POST
2. **URL**: `http://localhost:8083/api/documents/upload`
3. **Body**: form-data
4. **Fields**:
   - `file` (Type: File) - Select PDF file
   - `metadata` (Type: Text) - `{"description":"Sample","category":"REPORTS","uploadedBy":"john.doe","status":"ACTIVE"}`

### cURL Example
```bash
curl -X POST "http://localhost:8083/api/documents/upload" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@document.pdf" \
  -F 'metadata={"description":"Sample","category":"REPORTS","uploadedBy":"john.doe","status":"ACTIVE"}'
```

### Pros
- ✅ Supports complex object structures
- ✅ Type-safe with DTOs
- ✅ Better for structured data
- ✅ More flexible metadata handling

### Cons
- ❌ More complex to implement
- ❌ Requires proper JSON formatting
- ❌ Can cause parsing issues if JSON is malformed

## Stringified JSON Approach

### What is Stringified JSON?
Stringified JSON involves sending JSON data as a string in a multipart request, which is then parsed on the server side.

### Use Case
Best for complex metadata that needs to be sent as JSON but within a multipart request.

### API Endpoint
```java
@PostMapping("/upload-json")
public ResponseEntity<Document> uploadDocumentWithJson(
    @RequestPart("file") MultipartFile file,
    @RequestPart("metadata") String jsonMetadata) {
    
    try {
        ObjectMapper mapper = new ObjectMapper();
        DocumentUploadRequest metadata = mapper.readValue(jsonMetadata, DocumentUploadRequest.class);
        
        return documentService.uploadDocument(file, metadata.getDescription(), 
            metadata.getCategory(), metadata.getUploadedBy(), metadata.getStatus());
    } catch (Exception e) {
        throw new RuntimeException("Failed to parse JSON metadata: " + e.getMessage());
    }
}
```

### Request Structure
```
POST /api/documents/upload-json
Content-Type: multipart/form-data

Form Data:
- file: [PDF File]
- metadata: {"description":"Sample","category":"REPORTS","uploadedBy":"john.doe","status":"ACTIVE"}
```

### Postman Configuration
1. **Method**: POST
2. **URL**: `http://localhost:8083/api/documents/upload-json`
3. **Body**: form-data
4. **Fields**:
   - `file` (Type: File) - Select PDF file
   - `metadata` (Type: Text) - `{"description":"Sample","category":"REPORTS","uploadedBy":"john.doe","status":"ACTIVE"}`

### cURL Example
```bash
curl -X POST "http://localhost:8083/api/documents/upload-json" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@document.pdf" \
  -F 'metadata={"description":"Sample","category":"REPORTS","uploadedBy":"john.doe","status":"ACTIVE"}'
```

### Pros
- ✅ Full JSON flexibility
- ✅ Complex nested objects supported
- ✅ Easy to extend with new fields
- ✅ Better for API integrations

### Cons
- ❌ Requires JSON parsing
- ❌ Error handling for malformed JSON
- ❌ More complex implementation
- ❌ Potential security risks with JSON injection

## Comparison Table

| Aspect | @RequestParam | @RequestPart | Stringified JSON |
|--------|---------------|--------------|------------------|
| **Complexity** | Simple | Medium | High |
| **Data Types** | Basic types | Objects | Full JSON |
| **Flexibility** | Low | Medium | High |
| **Error Handling** | Simple | Medium | Complex |
| **Client Support** | Excellent | Good | Good |
| **Performance** | Fast | Medium | Medium |
| **Security** | Safe | Safe | Requires validation |
| **Maintenance** | Easy | Medium | Complex |

## Best Practices

### 1. Choose the Right Approach
- **Use @RequestParam** for simple file uploads with basic metadata
- **Use @RequestPart** for structured data with DTOs
- **Use Stringified JSON** for complex, nested metadata

### 2. Error Handling
```java
// Always validate file
if (file.isEmpty()) {
    throw new RuntimeException("File is empty");
}

// Validate file type
if (!file.getContentType().equals("application/pdf")) {
    throw new RuntimeException("Only PDF files allowed");
}

// Handle JSON parsing errors
try {
    DocumentUploadRequest metadata = objectMapper.readValue(jsonMetadata, DocumentUploadRequest.class);
} catch (Exception e) {
    throw new RuntimeException("Invalid JSON format: " + e.getMessage());
}
```

### 3. File Validation
```java
// Check file size
if (file.getSize() > maxFileSize) {
    throw new RuntimeException("File too large");
}

// Check file extension
String originalFilename = file.getOriginalFilename();
if (!originalFilename.toLowerCase().endsWith(".pdf")) {
    throw new RuntimeException("Only PDF files allowed");
}
```

### 4. Security Considerations
- Validate file types
- Check file sizes
- Sanitize JSON input
- Use proper content-type headers
- Implement rate limiting

## Troubleshooting

### Common Issues

#### 1. MultipartException
**Problem**: `Failed to parse multipart servlet request`
**Solution**: 
- Check Content-Type header
- Ensure form-data is used
- Verify file field type in Postman

#### 2. JSON Parsing Errors
**Problem**: `Failed to parse JSON metadata`
**Solution**:
- Validate JSON syntax
- Check field names match DTO
- Ensure proper escaping in cURL

#### 3. File Size Issues
**Problem**: File upload fails for large files
**Solution**:
- Increase `spring.servlet.multipart.max-file-size`
- Increase `spring.servlet.multipart.max-request-size`

#### 4. MethodArgumentTypeMismatchException
**Problem**: URL mapping conflicts
**Solution**:
- Order specific endpoints before generic ones
- Use proper URL patterns

### Configuration Properties
```properties
# File upload configuration
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
spring.servlet.multipart.file-size-threshold=2KB
spring.servlet.multipart.location=${java.io.tmpdir}
spring.servlet.multipart.resolve-lazily=false
```

### Testing Checklist
- [ ] File uploads successfully
- [ ] Metadata is saved correctly
- [ ] Error handling works
- [ ] File validation works
- [ ] JSON parsing works
- [ ] Response format is correct
- [ ] Database storage works
- [ ] File download works

## Conclusion

Choose the file upload approach based on your specific requirements:

- **Simple uploads**: Use `@RequestParam`
- **Structured data**: Use `@RequestPart` with DTOs
- **Complex metadata**: Use stringified JSON

All approaches can be implemented successfully with proper error handling and validation. 