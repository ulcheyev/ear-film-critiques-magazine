package cvut.controllers;

import cvut.config.utils.EarUtils;
import cvut.model.dto.creation.CommentCreationDTO;
import cvut.security.SecurityUtils;
import cvut.security.validators.NotStringValidator;
import cvut.services.CommentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/critiques")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/{critiqueId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addComment(
            @NonNull @Valid @NotStringValidator @PathVariable String critiqueId,
            @NonNull @Valid @RequestBody CommentCreationDTO comment) {
        String username = SecurityUtils.getAuthentication().getName();
        final HttpHeaders headers = EarUtils
                .createLocationHeaderFromCurrentUri("/{critiqueId}", critiqueId);
        commentService.save(comment.getContent(), username, Long.parseLong(critiqueId));
        return ResponseEntity.ok().headers(headers).body("Comment successfully created");
    }


    @DeleteMapping(value = "/{critiqueId}", produces = MediaType.APPLICATION_JSON_VALUE, params = "co_flagId")
    @PreAuthorize("@commentServiceImpl.checkOwner(#commentId, principal.username) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteComment(@NonNull @Valid @NotStringValidator @PathVariable String critiqueId,
                                                @RequestParam(value = "co_flagId") String commentId) {
        commentService.deleteComment(Long.parseLong(commentId));
        final HttpHeaders headers = EarUtils
                .createLocationHeaderFromCurrentUri("/{critiqueId}", critiqueId);
        return ResponseEntity.ok().headers(headers).body("Comment successfully deleted");
    }

    @PutMapping(value = "/{critiqueId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@commentServiceImpl.checkOwner(#commentId, principal.username) or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateComment(@NonNull @Valid @NotStringValidator @PathVariable String critiqueId,
                                                @NonNull @Valid @RequestBody CommentCreationDTO comment,
                                                @RequestParam(value = "co_flagId") String commentId) {
        commentService.update(Long.parseLong(commentId), comment.getContent());
        final HttpHeaders headers = EarUtils
                .createLocationHeaderFromCurrentUri("/{critiqueId}", critiqueId);
        return ResponseEntity.ok().headers(headers).body("Comment successfully updated");
    }
}
