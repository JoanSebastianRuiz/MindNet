package JoanRuiz.mindnet.controller;

import JoanRuiz.mindnet.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagService tagService;
}
