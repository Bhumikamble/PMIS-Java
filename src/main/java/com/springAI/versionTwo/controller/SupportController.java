package com.springAI.versionTwo.controller;

import com.springAI.versionTwo.dto.AiSupportResponse;
import com.springAI.versionTwo.dto.ErrorResponse;
import com.springAI.versionTwo.dto.SupportRequest;
import com.springAI.versionTwo.dto.SupportResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/support")
public class SupportController {

    private final ChatClient chatClient;

    public SupportController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping
    public ResponseEntity<?> support(@RequestBody SupportRequest request) {
        if (request.question() == null || request.question().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("The question must not be empty."));
        }

        String question = request.question().trim();
        AiSupportResponse aiResponse = chatClient.prompt()
                .system("""
                        You are a course-support assistant. Classify the user's intent and answer it.
                        Use category "Coursework" for questions about lessons, assignments, exams,
                        projects, programming, or learning materials. Use category "General" for
                        all other questions. Return only the requested structured response.
                        """)
                .user(question)
                .call()
                .entity(AiSupportResponse.class);

        if (aiResponse == null
                || aiResponse.category() == null
                || aiResponse.answer() == null
                || aiResponse.answer().isBlank()) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("The AI model did not return an answer."));
        }

        return ResponseEntity.ok(new SupportResponse(
                aiResponse.category(),
                question,
                aiResponse.answer()));
    }
}
