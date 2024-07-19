# OpenApi Generated Code Example

Backend: A SpringBoot application configured to generate both Java code and TypeScript client code from an OpenApi specification.

Frontend: A Next.js using the generated client to call the backend API.

## Generation

The openapi spec is defined in [backend/src/main/resources/api/books.yaml](backend/src/main/resources/api/books.yaml).

[Maven OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin) is configured in pom.xml to generate the following:

### Java

- Controller Interface, which is implemented in BooksController.
- DTOs including validation annotations.
- This generated code can be found in [backend/target/generated-sources/openapi/java](backend/target/generated-sources/openapi/java)

Note: This can be configured to build and publish a separate jar rather than local files (e.g. if required by another Java client service).

### TypeScript

- A default client that can be used to call the API. Currently configured to use fetch, but other generators are available for axios etc.
- DTOs
- This generated code can be found in [frontend/lib/generated/openapi](frontend/lib/generated/openapi) and is consumed in [frontend/src/app/page.tsx](frontend/src/app/page.tsx)

Note: This should be configured to publish an npm library ideally, but working with local files for the purposes of this demo.

## Run

### Pre-requisites

- Maven
- Java 17
- node >= 18.17.0

### Backend

From the backend directory:

```bash
# Build and publish generated code
mvn clean install
```

```bash
# Run the app
mvn spring-boot:run
```

#### Testing

See [BooksControllerIT.java](backend/src/test/java/com/example/openapidemo/BooksControllerIT.java) for a demonstrative integration test.

### Frontend

From the frontend directory:

```bash
# Install dependencies
npm install
```

```bash
# Run the app
npm run dev
```
