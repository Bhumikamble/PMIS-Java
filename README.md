# Spring AI Course Support Assistant

This is a practice variation of the FAQ Assistant in `versionOne`. It uses
the same Maven dependencies and Gemini configuration, but answers course and
learning-related questions.

## Practice problem

Build an AI-powered course-support assistant. A user sends a question, and the
application asks Gemini to answer it and classify the user's intent:

- `Coursework`: lessons, assignments, exams, projects, programming, or study
  materials
- `General`: all other questions

Unlike the first project, the endpoint is `/support` and the application runs
on port `8082`.

## Run the application

Configure the same IntelliJ environment variable used by `versionOne`:

```text
Gemini_API=your-gemini-api-key
```

Then run `VersionTwoApplication` from IntelliJ, or run Maven from the
repository root:

```powershell
.\versionOne\mvnw.cmd -f versionTwo\pom.xml spring-boot:run
```

The application starts at:

```text
http://localhost:8082
```

## Test with Postman

Use:

```text
POST http://localhost:8082/support
```

Select **Body -> raw -> JSON** and send:

```json
{
  "question": "How do I prepare for my Java exam?"
}
```

Example response:

```json
{
  "category": "Coursework",
  "question": "How do I prepare for my Java exam?",
  "answer": "AI-generated study advice appears here."
}
```

Try a general question too:

```json
{
  "question": "What is the weather today?"
}
```

It should normally return the `General` category.

## How the code works

`SupportRequest` receives the JSON request. `SupportController` validates the
question, sends it to Gemini with a system instruction, and asks Gemini to
return a structured `AiSupportResponse`.

The controller then combines that AI result with the original question in a
`SupportResponse`, which Spring converts to JSON. `ErrorResponse` is used when
the question is empty or the model does not return a usable response.

This project practices the same flow as `versionOne`:

```text
Postman JSON -> DTO -> Controller -> ChatClient -> Gemini -> DTO -> JSON
```

Run its tests from the repository root with:

```powershell
.\versionOne\mvnw.cmd -f versionTwo\pom.xml test
```
