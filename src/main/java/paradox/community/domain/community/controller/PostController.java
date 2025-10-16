package paradox.community.domain.community.controller;


import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostController {

    @GetMapping("/communities/posts/{post-id}/comments")
    public String getPostComments(@PathVariable("post-id") long postId, Model model) {

    }
}
