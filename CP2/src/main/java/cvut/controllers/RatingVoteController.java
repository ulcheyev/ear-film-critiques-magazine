package cvut.controllers;

import cvut.security.SecurityUtils;
import cvut.services.RatingVoteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/critiques")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class RatingVoteController {

    private final RatingVoteService ratingVoteService;

    @DeleteMapping(value = "/{critiqueId}", produces = MediaType.APPLICATION_JSON_VALUE, params = "unvote")
    public ResponseEntity<String> deleteVote(@RequestParam(value = "unvote") @PathVariable Long critiqueId) {
        ratingVoteService.deleteAndUpdate(
                SecurityUtils.getAuthentication().getName(),
                critiqueId
        );
        return ResponseEntity.ok().body("Vote successfully deleted");
    }


    /* Method in service check, if user already made vote.
     * If true -> updates vote, false -> creates new vote.
     * Update method is not necessary.
     * */
    @PostMapping(value = "/{critiqueId}", produces = MediaType.APPLICATION_JSON_VALUE, params = "stars")
    public ResponseEntity<String> addVote(@PathVariable @NonNull Long critiqueId,
                                          @RequestParam(value = "stars") Double stars) {
        ratingVoteService.makeVoteAndUpdateCritiqueAndCriticRatings(
                SecurityUtils.getAuthentication().getName(),
                critiqueId,
                stars
        );
        return ResponseEntity.ok().body("Vote successfully added");
    }

}
